package com.sun.stock.algorithm.victorieux;

import com.sun.stock.algorithm.Decode;
import com.sun.stock.domain.StockTradeDetail;

/**
 * Created by zksun on 6/5/16.
 */
public class VictorieuxDecode implements Decode<StockTradeDetail, byte[]> {
    @Override
    public StockTradeDetail decode(byte[] bytes) {
        if (null == bytes || bytes.length < 1) {
            throw new NullPointerException();
        }
        return null;
    }
}
