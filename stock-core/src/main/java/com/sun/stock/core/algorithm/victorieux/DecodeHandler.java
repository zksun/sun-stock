package com.sun.stock.core.algorithm.victorieux;

import com.sun.stock.core.algorithm.AbstractAlgorithmHandler;
import com.sun.stock.core.algorithm.Algorithm;
import com.sun.stock.core.algorithm.AlgorithmContext;
import com.sun.stock.core.domain.StockTradeDetail;

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
