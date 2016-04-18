package com.sun.stock.domain.enums;

/**
 * Created by zksun on 16-2-10.
 */
public enum StockType {
	SHANGHAI(1, "sh"), SHENZHEN(2, "sz");

	StockType(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	private int code;
	private String desc;

	public static StockType getTypeByDesc(String desc) {
		for (StockType type : StockType.values()) {
			if (type.desc.equals(desc)) {
				return type;
			}
		}
		return null;
	}
}
