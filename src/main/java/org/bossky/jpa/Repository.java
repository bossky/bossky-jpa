package org.bossky.jpa;

/**
 * 存储库
 * 
 * @author daibo
 *
 * @param <P>
 */
public interface Repository<P extends Persistent> {
	/**
	 * 获取对象
	 * 
	 * @param id
	 * @return
	 */
	P get(UniteId id);

	/**
	 * 获取对象
	 * 
	 * @param id
	 * @return
	 */
	P get(String id);

	/**
	 * 新增对象
	 * 
	 * @param v
	 */
	void add(P v);

	/**
	 * 更新对象
	 * 
	 * @param v
	 */
	void update(P v);

	/**
	 * 删除对象
	 * 
	 * @param v
	 */
	void delete(P v);

}
