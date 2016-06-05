package com.sun.stock.informations.http.sohu;

import com.sun.stock.domain.enums.Timer;
import com.sun.stock.domain.information.ExcludeRights;
import com.sun.stock.domain.information.KlineItem;
import com.sun.stock.informations.Explorer;
import com.sun.stock.util.StockUtil;
import com.sun.stock.util.http.HttpGet;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by zksun on 16-2-13.
 */
public final class SohuInfoExplorer implements Explorer {

    private static SohuInfoExplorer _instance;

    private final static String DEFAULT_LOCAL = "cn";

    private final static String INFO_TYPE_PARAM = "type";

    private final static String INFO_TRADEDETAIL_VALUE = "tradedetail";

    private final static String INFO_KLINE_VALUE = "kline";

    private final static String INFO_CODE_PARAM = "code";

    private final static String INFO_SET_PARAM = "set";

    private final static String INFO_COUNT_PARAM = "count";

    private final static String INFO_PERIOD_PARAM = "period";

    private final static String INFO_ADJUST_PARAM = "dr";

    private final static String INFO_STAR_DAY_PARAM = "date";

    private final static String DEFAULT_ADJUST = "0";

    public synchronized static SohuInfoExplorer getInstance() {
        if (null == _instance) {
            _instance = new SohuInfoExplorer();
        }
        return _instance;
    }

    @Override
    public Long calculateAllotmentPrice(String stockCode, Date date) {
        List<KlineItem> klineInfo = getKlineInfo(stockCode, Timer.DAY, false, date, -21);
        if (CollectionUtils.isEmpty(klineInfo)) {
            throw new RuntimeException("get day line data error");
        }
        klineInfo.remove(0);
        return calculateAveragePrice(klineInfo);
    }

    @Override
    public Long calculateAllotmentPrice(String stockCode, String date) {
        List<KlineItem> klineInfo = getKlineInfo(stockCode, Timer.DAY, false, date, -21);
        if (CollectionUtils.isEmpty(klineInfo)) {
            throw new RuntimeException("get day line data error");
        }
        klineInfo.remove(0);
        return calculateAveragePrice(klineInfo);
    }

    private Long calculateAveragePrice(List<KlineItem> klineInfo) {
        long total = 0;
        for (KlineItem item : klineInfo) {
            total += item.getClosePrice();
        }

        BigDecimal b = new BigDecimal(total);
        return b.divide(new BigDecimal(klineInfo.size()), 2, BigDecimal.ROUND_HALF_UP)
                .multiply(new BigDecimal(7)).longValue();
    }


    @Override
    public String getTradeDetailInfo(String stockCode) {
        if (StringUtils.isBlank(stockCode)) {
            throw new NullPointerException("no stockCode");
        }

        stockCode = getRealStockCode(stockCode);

        try {
            return HttpGet.getHttpGetInstance(PathConstant.MAIN_PATH).setPath(PathConstant.INFO_PATH)
                    .setParameters(INFO_TYPE_PARAM, INFO_TRADEDETAIL_VALUE)
                    .setParameters(INFO_CODE_PARAM, stockCode)
                    .setParameters(INFO_SET_PARAM, DEFAULT_LOCAL)
                    .setParameters(INFO_COUNT_PARAM, String.valueOf(INFO_DEFAULT_COUNT)).getHttpStringResponse();
        } catch (Exception e) {
            //todo logger
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<KlineItem> getKlineInfo(String stockCode, Timer timer, boolean adjust, String startDay, int count) {
        try {
            return getKlineInfo(stockCode, timer, adjust, new SimpleDateFormat(StockUtil.COMMON_DATE_FORMATTER).parse(startDay), count);
        } catch (Exception e) {
            //todo logger
        }
        return null;
    }

    @Override
    public List<KlineItem> getKlineInfo(String stockCode, Timer timer, boolean adjust, Date startDay, int count) {

        if (StringUtils.isBlank(stockCode) || null == timer) {
            throw new NullPointerException();
        }

        stockCode = getRealStockCode(stockCode);

        if (null != startDay) {
            if (count == 0)
                throw new IllegalArgumentException("count can not be 0");
            if (!timer.equals(Timer.DAY)) {
                throw new IllegalArgumentException("timer must be day");
            }
        }

        HttpGet httpGet = HttpGet.getHttpGetInstance(PathConstant.MAIN_PATH).setPath(PathConstant.INFO_PATH)
                .setParameters(INFO_TYPE_PARAM, INFO_KLINE_VALUE)
                .setParameters(INFO_CODE_PARAM, stockCode)
                .setParameters(INFO_SET_PARAM, DEFAULT_LOCAL)
                .setParameters(INFO_PERIOD_PARAM, timer.getAbbreviation())
                .setParameters(INFO_ADJUST_PARAM, adjust == true ? "1" : "0");

        if (null != startDay) {
            httpGet.setParameters(INFO_STAR_DAY_PARAM, new SimpleDateFormat(StockUtil.COMMON_DATE_FORMATTER).format(startDay));
            httpGet.setParameters(INFO_COUNT_PARAM, String.valueOf(count));
        }

        try {
            return httpGet.getHttpStringResponse(new SohuGetInformationHandler.KlineInfoHandler());
        } catch (Exception e) {
            //todo logger;
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<ExcludeRights> getExcludeRightsInfo(String stockCode, Timer timer) {
        if (StringUtils.isBlank(stockCode) || null == timer) {
            throw new NullPointerException();
        }

        stockCode = getRealStockCode(stockCode);

        try {
            return HttpGet.getHttpGetInstance(PathConstant.MAIN_PATH).setPath(PathConstant.INFO_PATH)
                    .setParameters(INFO_TYPE_PARAM, INFO_KLINE_VALUE)
                    .setParameters(INFO_CODE_PARAM, stockCode)
                    .setParameters(INFO_SET_PARAM, DEFAULT_LOCAL)
                    .setParameters(INFO_PERIOD_PARAM, timer.getAbbreviation())
                    .setParameters(INFO_ADJUST_PARAM, DEFAULT_ADJUST).getHttpStringResponse(new SohuGetInformationHandler.AdjustInfoHandler());
        } catch (Exception e) {
            //todo logger
            e.printStackTrace();
        }
        return null;
    }

    private String getRealStockCode(String code) {
        if (code.split("_").length > 1) {
            return code.substring(3, code.length());
        }
        return code;
    }

    private SohuInfoExplorer() {
    }
}
