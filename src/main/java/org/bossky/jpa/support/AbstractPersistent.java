package org.bossky.jpa.support;

import org.bossky.jpa.Assistant;
import org.bossky.jpa.Entity;
import org.bossky.jpa.Persistent;
import org.bossky.jpa.UniteId;

/**
 * 抽象持久类实现
 * 
 * @author daibo
 *
 * @param <E>
 * @param <A>
 */
public class AbstractPersistent<E extends Entity, A extends Assistant> implements Persistent {
	/** 助手 */
	protected final A assistant;
	/** 实体类 */
	protected final E entity;
	/** id */
	private UniteId id;

	public AbstractPersistent(A assistant, E entity) {
		this.assistant = assistant;
		this.entity = entity;
		id = UniteId.valueOf(getClass(), entity.getId());
	}

	@Override
	public UniteId getId() {
		return id;
	}

	protected E getEntity() {
		return entity;
	}

	protected Assistant getAssistant() {
		return assistant;
	}

	protected void makeNew() {
		// TODO
	}

	protected void makeUpdate() {
		// TODO
	}

}
