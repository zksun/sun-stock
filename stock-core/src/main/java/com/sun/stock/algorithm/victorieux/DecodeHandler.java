package com.sun.stock.algorithm.victorieux;

import com.sun.stock.algorithm.AbstractAlgorithmHandler;
import com.sun.stock.algorithm.Algorithm;
import com.sun.stock.algorithm.AlgorithmContext;
import com.sun.stock.domain.StockTradeDetail;

/**
 * Created by zksun on 6/5/16.
 */
public class DecodeHandler extends AbstractAlgorithmHandler<AlgorithmContext, StockTradeDetail, String> {

    @Override
    protected Algorithm selectAlgorithm() {
        return null;
    }

    @Override
    protected String createAlgorithmMessage(StockTradeDetail value) {
        return null;
    }


    @Override
    public Algorithm parent() {
        return null;
    }

    @Override
    public String getAlgorithmName() {
        return null;
    }

    @Override
    public void algorithm(AlgorithmContext context, String message) {

    }


}
