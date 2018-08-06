package org.bossky.jpa;

/**
 * 业务实体类
 * 
 * @author daibo
 *
 * @param <Entity>
 */
public interface Persistent {
	/**
	 * 唯一id
	 * 
	 * @return
	 */
	public UniteId getId();
}
