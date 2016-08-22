package com.sun.stock.core.domain.enums;

/**
 * Created by zksun on 16-2-10.
 */
public enum Timer {
	NULL(-1, "null", "��"), SECOND(0, "s", "��"), MINUTE(1, "m", "����"), MINUTE_5(2, "5m", "�����"),
	QUARTER_HOUR(3, "15m", "һ����"), HALF_HOUR(4, "30m", "��ʮ����"),
	HOUR(5, "h", "Сʱ"), DAY(6, "day", "��"), DAY_5(7, "5day", "5��"), DAY_10(8, "10day", "10��"),
	DAY_30(9, "30day", "30��"), MONTH(10, "month", "��");

	Timer(int code, String abbreviation, String desc) {
		this.code = code;
		this.abbreviation = abbreviation;
		this.desc = desc;
	}

	private int code;
	private String abbreviation;
	private String desc;

	public int getCode() {
		return code;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public String getDesc() {
		return desc;
	}

	@Override
	public String toString() {
		return "TimeEnum{" +
				"code=" + code +
				", abbreviation='" + abbreviation + '\'' +
				", desc='" + desc + '\'' +
				'}';
	}
}
