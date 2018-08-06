package org.bossky.jpa;

public interface Flusher {
	/**
	 * 保存
	 * 
	 * @param p
	 */
	public void save(Entity p);

	/**
	 * 移除
	 * 
	 * @param p
	 */
	public void remove(Entity p);

}
