package com.sun.stock.domain.enums;

/**
 * Created by zksun on 16-2-10.
 */
public enum Timer {
	NULL(-1, "null", "无"), SECOND(0, "s", "秒"), MINUTE(1, "m", "分钟"), MINUTE_5(2, "5m", "五分钟"),
	QUARTER_HOUR(3, "15m", "一刻钟"), HALF_HOUR(4, "30m", "三十分钟"),
	HOUR(5, "h", "小时"), DAY(6, "day", "日"), DAY_5(7, "5day", "5日"), DAY_10(8, "10day", "10日"),
	DAY_30(9, "30day", "30日"), MONTH(10, "month", "月");

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
