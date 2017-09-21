package com.sun.stock.core.domain.information;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static java.util.Collections.sort;

/**
 * Created by zksun on 16-2-15.
 */
public class ExcludeRightsWrapper {

	private String stockCode;
	private List<ExcludeRights> stockExRights;

	public ExcludeRightsWrapper(List<ExcludeRights> excludeRightses) {
		if (null == excludeRightses || excludeRightses.size() < 1) {
			throw new NullPointerException("no excludeRightses");
		}
		this.stockExRights = excludeRightses;
		this.stockCode = excludeRightses.get(0).getStockCode();
		sortExcludeRights();
	}

	public static ExcludeRightsWrapper wrapper(List<ExcludeRights> excludeRightses) {
		if (null == excludeRightses || excludeRightses.isEmpty()) {
			throw new NullPointerException("empty excludeRights");
		}
		return new ExcludeRightsWrapper(excludeRightses);
	}


	public void setStockExRights(List<ExcludeRights> stockExRights) {
		this.stockExRights = stockExRights;
	}

	public String getStockCode() {
		return stockCode;
	}

	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

	private void sortExcludeRights() {
		sort(this.stockExRights);
	}

	public long calculateAdjustStockPrice(Date tradeDate, long tradePrice) {
		if (null == tradeDate) {
			throw new NullPointerException();
		}

		if (tradePrice < 1) {
			throw new IllegalArgumentException("error price");
		}

		List<ExcludeRights> rights = getExcludeRightsByDate(tradeDate);

		if (rights.isEmpty()) {
			return tradePrice;
		}

		long adjustPrice = tradePrice;

		for (ExcludeRights r : rights) {
			adjustPrice = exchangeAdjustStockPrice(adjustPrice, r);
		}
		return adjustPrice;
	}


	public long exchangeAdjustStockPrice(long tradedPrice, ExcludeRights rights) {
		if (tradedPrice < 1) {
			throw new IllegalArgumentException("error price");
		}
		Long numerator = tradedPrice * 10L
				- rights.getDistribute()//每十股分红
				+ rights.getAllotmentPrice()//每十股配股价
				* rights.getAllotmentStock();//每十股配股

		if (numerator < 0) {
			throw new IllegalArgumentException("error price");
		}

		Long denominator = 10L + rights.getExchangeStock() + rights.getAllotmentStock();

		return new Double(new BigDecimal(numerator)
				.divide(new BigDecimal(denominator), 2, BigDecimal.ROUND_HALF_UP)
				.doubleValue()).longValue();
	}

	private List<ExcludeRights> getExcludeRightsByDate(Date date) {
		int index = 0;
		for (ExcludeRights rights : this.stockExRights) {
			if (date.before(rights.getAdjustDay())) {
				index = this.stockExRights.size() - index;
				break;
			}
			index++;
		}
		if (index == this.stockExRights.size()) {
			return Collections.EMPTY_LIST;
		}
		return copySubListByIndex(index);
	}

	private List<ExcludeRights> copySubListByIndex(int index) {
		List<ExcludeRights> list = new ArrayList<>(index);
		for (int i = index; i > 0; i--) {
			list.add(this.stockExRights.get(this.stockExRights.size() - i));
		}
		return list;
	}

}
