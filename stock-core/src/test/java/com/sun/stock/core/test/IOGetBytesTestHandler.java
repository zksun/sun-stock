package com.sun.stock.core.test;

import com.sun.stock.core.algorithm.AlgorithmContext;
import com.sun.stock.core.algorithm.utils.IOHandler;
import com.sun.stock.core.domain.StockTradeDetail;
import com.sun.stock.core.domain.information.ExcludeRights;
import com.sun.stock.core.informations.http.sohu.SohuInfoExplorer;
import com.sun.stock.core.util.StockAlgorithmUtil;
import com.sun.stock.core.util.StockUtil;

import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by zksun on 16-2-11.
 */
public class IOGetBytesTestHandler implements IOHandler<byte[], AlgorithmContext> {

    private Map<Long, Long> dataCache = new TreeMap<>(new Comparator<Long>() {
        @Override
        public int compare(Long o1, Long o2) {
            return o2.compareTo(o1);
        }
    });

    private Map<Long, List<StockTradeDetail>> stockTradeDataCache = new TreeMap<>(new Comparator<Long>() {
        @Override
        public int compare(Long o1, Long o2) {
            return o2.compareTo(o1);
        }
    });

    private List<ExcludeRights> excludeRightses = null;

    private String stockCode;

    private String dateString;

    public IOGetBytesTestHandler(String stockCode) {
        this.stockCode = stockCode;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    @Override
    public void execute(byte[] value, AlgorithmContext context) {

//		if (!dateString.equals("20160225")) {
//			return null;
//		}

        if (value.length > 0) {
            ByteBuffer byteBuffer = ByteBuffer.wrap(value);
            byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
            int sequence = byteBuffer.getShort();
            BigDecimal bigDecimal = new BigDecimal(byteBuffer.getInt());
            long price = bigDecimal.divide(new BigDecimal(10L), 2, BigDecimal.ROUND_HALF_UP).longValue();
            int tradeLot = byteBuffer.getInt();
            int tradeNum = byteBuffer.getInt();
            int bs = byteBuffer.getShort();
//			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
//			System.out.println("the sequence is: " + getRealSequence(sequence));
//			System.out.println("the price is: " + price);
//			System.out.println("the tradeLot is: " + tradeLot);
//			System.out.println("the tradeNum is: " + tradeNum);
//			System.out.println("sell or buy is: " + (bs > 0 ? "sell" : "buy"));
//			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

//			if (this.dateString.equals("20151231")) {
//				System.out.println("error");
//			}

            if (null == excludeRightses) {
                excludeRightses = SohuInfoExplorer.getInstance().
                        getExcludeRightsInfo(stockCode, com.sun.stock.core.domain.enums.Timer.DAY);
                if (null == excludeRightses) {
                    excludeRightses = Collections.EMPTY_LIST;
                }
            }

            Long adjustPrice;
            try {
                adjustPrice = StockAlgorithmUtil.exchangeAdjustStockPrice(excludeRightses, price, dateString);
            } catch (Exception e) {
                adjustPrice = price;
            }
            reportData(adjustPrice, tradeLot);
            Date tradeDate = null;
            try {
                tradeDate = new SimpleDateFormat(StockUtil.COMMON_DATE_FORMATTER).parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            StockTradeDetail stockTradeDetail = new StockTradeDetail(this.stockCode, price, tradeLot, tradeDate);
            stockTradeDetail.setBuy(bs > 0 ? false : true);
            stockTradeDetail.setExRightsPrice(adjustPrice);
            reportStockTradeDetail(stockTradeDetail);
        }

    }

    private void reportStockTradeDetail(StockTradeDetail detail) {
        if (this.stockTradeDataCache.containsKey(detail.getExRightsPrice())) {
            List<StockTradeDetail> stockTradeDetails = this.stockTradeDataCache.get(detail.getExRightsPrice());
            stockTradeDetails.add(detail);
        } else {
            List<StockTradeDetail> stockTradeDetails = new ArrayList<>();
            stockTradeDetails.add(detail);
            this.stockTradeDataCache.put(detail.getExRightsPrice(), stockTradeDetails);
        }
    }

    private void reportData(Long price, int tradeLot) {
        if (this.dataCache.containsKey(price)) {
            long tmp = tradeLot + this.dataCache.get(price);
            this.dataCache.put(price, tmp);
        } else {
            this.dataCache.put(price, Long.valueOf(tradeLot));
        }
    }

    public List<Map.Entry<Long, Long>> sortMap() {
        List<Map.Entry<Long, Long>> list = new ArrayList<>(dataCache.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Long, Long>>() {
            @Override
            public int compare(Map.Entry<Long, Long> o1, Map.Entry<Long, Long> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        return list;
    }

    public Map<Long, Long> getDataCache() {
        return dataCache;
    }

    public Map<Long, List<StockTradeDetail>> getStockTradeDataCache() {
        return stockTradeDataCache;
    }

    private int getRealSequence(int source) {
        if (source < 780) {
            return source - 570;
        } else {
            return source - 780 + 120;
        }
    }


}
