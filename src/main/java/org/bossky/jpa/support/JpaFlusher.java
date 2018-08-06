package org.bossky.jpa.support;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import org.bossky.common.Shutdown;
import org.bossky.jpa.Entity;
import org.bossky.jpa.Flusher;
import org.slf4j.Logger;

/**
 * jpa刷写器
 * 
 * @author daibo
 *
 */
public class JpaFlusher implements Flusher, Runnable, Closeable {
	/** 日志 */
	static final Logger _Logger = org.slf4j.LoggerFactory.getLogger(JpaFlusher.class);
	/** 实体管理工厂 */
	protected EntityManagerFactory factory;
	/** 保存队列 */
	protected ConcurrentLinkedQueue<Entity> saves;
	/** 移除队列 */
	protected ConcurrentLinkedQueue<Entity> removes;
	/** 名称 */
	protected String name;
	/** 间隔 */
	protected long interval = 3000;
	/** 最大保存项数 */
	protected long maxSaveNum = 100;
	/** 最大移除项数 */
	protected long maxRemoveNum = 100;
	/** 是否运行中 */
	protected volatile boolean running;

	public JpaFlusher(EntityManagerFactory factory, String name) {
		saves = new ConcurrentLinkedQueue<>();
		removes = new ConcurrentLinkedQueue<>();
		start();
	}

	public void start() {
		Thread t = new Thread(this, "Flusher-" + name);
		running = true;
		t.start();
		Shutdown.register((Closeable) this);
	}

	@Override
	public void save(Entity p) {
		saves.offer(p);
		if (saves.size() >= maxSaveNum) {
			synchronized (this) {
				this.notify();
			}
		}
	}

	@Override
	public void remove(Entity p) {
		removes.offer(p);
		if (removes.size() >= maxRemoveNum) {
			synchronized (this) {
				this.notify();
			}
		}
	}

	@Override
	public void run() {
		while (running) {
			synchronized (this) {
				try {
					this.wait(interval);
				} catch (InterruptedException e) {
					// 正常的
				}
			}
			flush();
		}

	}

	@Override
	public void close() throws IOException {
		running = false;
		synchronized (this) {
			this.notify();
		}
	}

	private void flush() {
		EntityManager m = factory.createEntityManager();
		EntityTransaction tran = m.getTransaction();
		List<Entity> fairs = new ArrayList<Entity>();
		Entity p;
		while (null != (p = saves.poll())) {
			boolean isSuccess = false;
			try {
				tran.begin();
				m.persist(p);
				tran.commit();
				isSuccess = true;
			} catch (Throwable e) {
				_Logger.error("flusher fair " + p, e);
			} finally {
				if (!isSuccess) {
					fairs.add(p);
					tran.rollback();
				}
			}
		}
		if (running) {
			for (Entity f : fairs) {
				saves.offer(f);
			}
		}
		while (null != (p = removes.poll())) {
			boolean isSuccess = false;
			try {
				tran.begin();
				m.remove(p);
				tran.commit();
				isSuccess = true;
			} catch (Throwable e) {
				_Logger.error("flusher fair " + p, e);
			} finally {
				if (!isSuccess) {
					fairs.add(p);
					tran.rollback();
				}
			}
		}
		if (running) {
			for (Entity f : fairs) {
				removes.offer(f);
			}
		}
		m.close();
	}

}
