package com.sun.stock.informations;

import com.sun.stock.domain.enums.Timer;
import com.sun.stock.domain.information.ExcludeRights;
import com.sun.stock.domain.information.KlineItem;

import java.util.Date;
import java.util.List;

/**
 * Created by zksun on 16-4-18.
 */
public interface Explorer {
	int INFO_DEFAULT_COUNT = 100;

	Long calculateAllotmentPrice(String stockCode, Date date);

	Long calculateAllotmentPrice(String stockCode, String date);

	String getTradeDetailInfo(String stockCode);

	List<KlineItem> getKlineInfo(String stockCode, Timer timer, boolean adjust, String startDay, int count);

	List<KlineItem> getKlineInfo(String stockCode, Timer timer, boolean adjust, Date startDay, int count);

	List<ExcludeRights> getExcludeRightsInfo(String stockCode, Timer timer);
}
