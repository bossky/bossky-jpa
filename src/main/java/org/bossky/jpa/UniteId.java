package org.bossky.jpa;

/**
 * 唯一id
 * 
 * @author daibo
 *
 */
public class UniteId {
	/** 类型 */
	protected String type;
	/** 序号 */
	protected String ordinal;

	public static final char TYPE_SEPARATE = '$';

	protected UniteId(Class<?> type, String ordinal) {
		this(getType(type), ordinal);
	}

	protected UniteId(String type, String ordinal) {
		this.type = type;
		this.ordinal = ordinal;
	}

	public String getId() {
		return type + TYPE_SEPARATE + ordinal;
	}

	public String getType() {
		return type;
	}

	public String getOrdinal() {
		return ordinal;
	}

	public static String getType(Class<?> type) {
		return type.getSimpleName();
	}

	public static UniteId valueOf(Class<?> type, String ordinal) {
		return new UniteId(type, ordinal);
	}

}
