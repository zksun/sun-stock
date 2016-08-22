package com.sun.stock.core.informations.http.sohu;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.stock.core.domain.information.ExcludeRights;
import com.sun.stock.core.informations.InformationHandler;
import com.sun.stock.core.util.StockUtil;
import com.sun.stock.core.domain.information.KlineItem;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zksun on 16-2-13.
 */
public interface SohuGetInformationHandler<T, V> extends InformationHandler<T, V> {

	class TradeDetailInfoHandler implements SohuGetInformationHandler<String, byte[]> {
		@Override
		public String getInformation(byte[] value) {
			return null;
		}
	}

	class KlineInfoHandler implements SohuGetInformationHandler<List<KlineItem>, String> {
		@Override
		public List<KlineItem> getInformation(String value) {
			if (StringUtils.isBlank(value)) {
				throw new NullPointerException("no data");
			}

			Gson gson = new Gson();
			Map<String, Object> jsonObj = gson.fromJson(value, new TypeToken<Map<String, Object>>() {
			}.getType());
			String stockCode = (String) jsonObj.get("code");
			List<List<String>> dataList = (List<List<String>>) jsonObj.get("kline");
			if (CollectionUtils.isNotEmpty(dataList) && StringUtils.isNoneBlank(stockCode)) {
				return getData(dataList, stockCode);
			}
			return null;
		}

		private List<KlineItem> getData(List<List<String>> dataList, String stockCode) {
			List<KlineItem> klineItems = new ArrayList<>(dataList.size());
			for (List<String> list : dataList) {
				KlineItem klineItem = new KlineItem(stockCode);
				String[] strings = list.toArray(new String[list.size()]);
				try {
					klineItem.setDate(new SimpleDateFormat(StockUtil.COMMON_DATE_FORMATTER).parse(strings[0].trim()));
				} catch (ParseException e) {
					//igore
				}
				klineItem.setOpenPrice(new Double(NumberUtils.toDouble(strings[1].trim()) * 100D).longValue());
				klineItem.setClosePrice(new Double(NumberUtils.toDouble(strings[2].trim()) * 100D).longValue());
				klineItem.setHighPrice(new Double(NumberUtils.toDouble(strings[3].trim()) * 100D).longValue());
				klineItem.setLowPrice(new Double(NumberUtils.toDouble(strings[4].trim()) * 100D).longValue());
				klineItem.setTradedVolume(new Double(NumberUtils.toDouble(strings[5].trim())).longValue());
				klineItem.setTradedAmount(new Double(NumberUtils.toDouble(strings[6].trim()) * 100D).longValue());
				klineItem.setHandRate(new Double(NumberUtils.toDouble(strings[7].trim())));
				klineItem.setUpDown(new Double(NumberUtils.toDouble(strings[8].trim()) * 100D).longValue());
				klineItem.setUpDownRatio(new Double(NumberUtils.toDouble(strings[9].trim())));
				klineItems.add(klineItem);
			}
			return klineItems;
		}
	}

	class AdjustInfoHandler implements SohuGetInformationHandler<List<ExcludeRights>, String> {

		@Override
		public List<ExcludeRights> getInformation(String value) {
			if (StringUtils.isBlank(value)) {
				throw new NullPointerException("no data");
			}
			Gson gson = new Gson();
			Map<String, Object> jsonObj = gson.fromJson(value, new TypeToken<Map<String, Object>>() {
			}.getType());

			String stockCode = (String) jsonObj.get("code");
			List<List<String>> dataList = (List<List<String>>) jsonObj.get("div");

			if (CollectionUtils.isNotEmpty(dataList) && StringUtils.isNoneBlank(stockCode)) {
				return getData(dataList, stockCode);
			}
			return null;
		}

		private List<ExcludeRights> getData(List<List<String>> dataList, String stockCode) {
			List<ExcludeRights> excludeRightists = new ArrayList<>(dataList.size());
			for (List<String> list : dataList) {
				ExcludeRights right = new ExcludeRights(stockCode);
				String[] strings = list.toArray(new String[list.size()]);
				try {
					right.setAdjustDay(new SimpleDateFormat(StockUtil.COMMON_DATE_FORMATTER).parse(strings[0].trim()));
				} catch (ParseException e) {
					//igore
				}
				right.setAllotmentStock(NumberUtils.toInt(strings[1].trim()));
				if (right.getAllotmentStock() > 0) {
					right.setAllotmentPrice(SohuInfoExplorer.getInstance().calculateAllotmentPrice(stockCode, right.getAdjustDay()));
				} else {
					right.setAllotmentPrice(0L);
				}
				right.setDistribute(new Double(NumberUtils.toDouble(strings[2].trim()) * 100D).longValue());
				right.setExchangeStock(new Double(NumberUtils.toDouble(strings[3].trim())).intValue());
				excludeRightists.add(right);
			}
			return excludeRightists;
		}

	}
}
