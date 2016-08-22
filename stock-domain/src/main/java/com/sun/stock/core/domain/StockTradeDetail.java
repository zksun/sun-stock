package com.sun.stock.core.domain;

import com.sun.stock.core.domain.enums.StockType;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zksun on 16-2-15.
 */
public class StockTradeDetail implements Comparable<StockTradeDetail>, Serializable {
	/**
	 * 股票代码
	 */
	private String stockCode;
	/**
	 * 股票市场类型(沪市,深市...)
	 */
	private StockType stockType;
	/**
	 * 交易价格
	 */
	private Long tradePrice;
	/**
	 * 后复位价格
	 */
	private Long exRightsPrice;
	/**
	 * 交易日期
	 */
	private Date tradeDate;
	/**
	 * 交易手数
	 */
	private Long tradedVolume;
	/**
	 * 交易量
	 */
	private Long tradedAmount;
	/**
	 * 是否主动买
	 */
	private boolean buy;

	public StockTradeDetail(String stockCode, long tradePrice, long tradedVolume, Date tradeDate) {
		if (null == stockCode || stockCode.equals("") || null == tradeDate) {
			throw new NullPointerException();
		}

		if (tradePrice < 1) {
			throw new IllegalArgumentException("error price");
		}
		this.tradePrice = tradePrice;
		this.tradeDate = tradeDate;
		this.tradedVolume = tradedVolume;
		this.tradedAmount = tradePrice * tradedVolume * 100;
		this.stockCode = stockCode;
		this.stockType = getStockType(stockCode);
	}

	private StockType getStockType(String stockCode) {
		String[] split = stockCode.split("_");
		StockType stockType;
		if (split.length < 2 || null == (stockType = StockType.getTypeByDesc(split[0]))) {
			return null;
		}
		return stockType;
	}

	public String getStockCode() {
		return stockCode;
	}

	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

	public StockType getStockType() {
		return stockType;
	}

	public void setStockType(StockType stockType) {
		this.stockType = stockType;
	}

	public Long getTradePrice() {
		return tradePrice;
	}

	public void setTradePrice(Long tradePrice) {
		this.tradePrice = tradePrice;
	}

	public Long getExRightsPrice() {
		return exRightsPrice;
	}

	public void setExRightsPrice(Long exRightsPrice) {
		this.exRightsPrice = exRightsPrice;
	}

	public Date getTradeDate() {
		return tradeDate;
	}

	public void setTradeDate(Date tradeDate) {
		this.tradeDate = tradeDate;
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

	public boolean isBuy() {
		return buy;
	}

	public void setBuy(boolean buy) {
		this.buy = buy;
	}

	@Override
	public int compareTo(StockTradeDetail o) {
		if (this.exRightsPrice > o.exRightsPrice) {
			return 1;
		} else if (this.exRightsPrice < o.exRightsPrice) {
			return -1;
		}
		return 0;
	}

	@Override
	public String toString() {
		return "StockTradeDetail{" +
				"stockCode='" + stockCode + '\'' +
				", stockType=" + stockType +
				", tradePrice=" + tradePrice +
				", exRightsPrice=" + exRightsPrice +
				", tradeDate=" + tradeDate +
				", tradedVolume=" + tradedVolume +
				", tradedAmount=" + tradedAmount +
				", buy=" + buy +
				'}';
	}
}
