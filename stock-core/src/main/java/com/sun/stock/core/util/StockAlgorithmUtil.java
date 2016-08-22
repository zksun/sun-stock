package com.sun.stock.core.util;

import com.sun.stock.core.domain.information.ExcludeRights;
import com.sun.stock.core.domain.information.ExcludeRightsWrapper;
import org.apache.commons.collections4.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by zksun on 16-2-10.
 */
public final class StockAlgorithmUtil {

	public static int getRealSequence(int source) {
		if (source <= 0) {
			throw new IllegalArgumentException();
		}
		if (source < 780) {
			return source - 570;
		} else {
			return source - 780 + 120;
		}
	}

	public static Long exchangeAdjustStockPrice(List<ExcludeRights> rights, Long price, String tradeDate) {
		if (CollectionUtils.isEmpty(rights) || null == price || null == tradeDate) {
			throw new NullPointerException("param error");
		}
		try {
			return ExcludeRightsWrapper.wrapper(rights)
					.calculateAdjustStockPrice(new SimpleDateFormat(StockUtil.COMMON_DATE_FORMATTER).parse(tradeDate), price);
		} catch (Exception e) {
			return price;
		}
	}

	public static Long exchangeAdjustStockPrice(List<ExcludeRights> rights, Long price, Date tradeDate) {
		if (CollectionUtils.isEmpty(rights) || null == price) {
			throw new NullPointerException("no rights");
		}
		return ExcludeRightsWrapper.wrapper(rights).calculateAdjustStockPrice(tradeDate, price);
	}


	private StockAlgorithmUtil() {
	}
}
