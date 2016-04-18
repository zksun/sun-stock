package com.sun.stock.domain.information;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zksun on 16-2-14.
 */
public class ExcludeRights implements Comparable<ExcludeRights>, Serializable {
	//每十股分红金额
	private Long distribute;
	//每十股转股
	private Integer exchangeStock;
	//每十股配股
	private Integer allotmentStock;
	//每十股配股价格
	private Long allotmentPrice;
	//除权日
	private Date adjustDay;
	//股票代码
	private String stockCode;

	public ExcludeRights(String stockCode) {
		this.stockCode = stockCode;
	}

	public Long getDistribute() {
		return distribute;
	}

	public void setDistribute(Long distribute) {
		this.distribute = distribute;
	}

	public Integer getExchangeStock() {
		return exchangeStock;
	}

	public void setExchangeStock(Integer exchangeStock) {
		this.exchangeStock = exchangeStock;
	}

	public Integer getAllotmentStock() {
		return allotmentStock;
	}

	public void setAllotmentStock(Integer allotmentStock) {
		this.allotmentStock = allotmentStock;
	}

	public Date getAdjustDay() {
		return adjustDay;
	}

	public void setAdjustDay(Date adjustDay) {
		this.adjustDay = adjustDay;
	}

	public String getStockCode() {
		return stockCode;
	}

	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

	public Long getAllotmentPrice() {
		return allotmentPrice;
	}

	public void setAllotmentPrice(Long allotmentPrice) {
		this.allotmentPrice = allotmentPrice;
	}

	@Override
	public String toString() {
		return "ExcludeRights{" +
				"distribute=" + distribute +
				", exchangeStock=" + exchangeStock +
				", allotmentStock=" + allotmentStock +
				", adjustDay=" + adjustDay +
				", stockCode='" + stockCode + '\'' +
				", allotmentPrice=" + allotmentPrice +
				'}';
	}

	@Override
	public int compareTo(ExcludeRights source) {
		if (this.adjustDay.after(source.adjustDay)) {
			return 1;
		} else if (this.adjustDay.before(source.adjustDay)) {
			return -1;
		}
		return 0;
	}
}
