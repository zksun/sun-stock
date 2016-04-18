package com.sun.stock.domain.information;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zksun on 16-2-14.
 */
public class KlineItem implements Comparable<KlineItem>, Serializable {
	//开盘价
	private Long openPrice;
	//收盘价
	private Long closePrice;
	//最高价
	private Long highPrice;
	//最低价
	private Long lowPrice;
	//成交手数
	private Long tradedVolume;
	//成交额
	private Long tradedAmount;
	//换手率
	private Double handRate;
	//涨跌
	private Long upDown;
	//涨跌幅
	private Double upDownRatio;
	//时间日期
	private Date date;
	//股票代码
	private String stockCode;

	public KlineItem(String stockCode) {
		this.stockCode = stockCode;
	}

	public Long getOpenPrice() {
		return openPrice;
	}

	public void setOpenPrice(Long openPrice) {
		this.openPrice = openPrice;
	}

	public Long getClosePrice() {
		return closePrice;
	}

	public void setClosePrice(Long closePrice) {
		this.closePrice = closePrice;
	}

	public Long getHighPrice() {
		return highPrice;
	}

	public void setHighPrice(Long highPrice) {
		this.highPrice = highPrice;
	}

	public Long getLowPrice() {
		return lowPrice;
	}

	public void setLowPrice(Long lowPrice) {
		this.lowPrice = lowPrice;
	}

	public Long getTradedVolume() {
		return tradedVolume;
	}

	public void setTradedVolume(Long tradedVolume) {
		this.tradedVolume = tradedVolume;
	}

	public Long getTradedAmount() {
		return tradedAmount;
	}

	public void setTradedAmount(Long tradedAmount) {
		this.tradedAmount = tradedAmount;
	}

	public Double getHandRate() {
		return handRate;
	}

	public void setHandRate(Double handRate) {
		this.handRate = handRate;
	}

	public Long getUpDown() {
		return upDown;
	}

	public void setUpDown(Long upDown) {
		this.upDown = upDown;
	}

	public Double getUpDownRatio() {
		return upDownRatio;
	}

	public void setUpDownRatio(Double upDownRatio) {
		this.upDownRatio = upDownRatio;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getStockCode() {
		return stockCode;
	}

	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

	@Override
	public int compareTo(KlineItem o) {
		if (this.getDate().after(o.date)) {
			return 1;
		} else if (this.getDate().before(o.date)) {
			return -1;
		}
		return 0;
	}
}
