package com.sun.stock.core.test;

import com.sun.stock.core.algorithm.AbstractAlgorithmHandler;
import com.sun.stock.core.algorithm.Algorithm;
import com.sun.stock.core.algorithm.AlgorithmContext;

/**
 * Created by zksun on 16-2-10.
 */
public class IOReadLineTestHandler extends AbstractAlgorithmHandler<AlgorithmContext, byte[], String> {

    @Override
    protected Algorithm selectAlgorithm() {
        return this;
    }

    @Override
    protected String createAlgorithmMessage(byte[] value) {
        return "";
    }

    @Override
    public Algorithm parent() {
        return this;
    }

    @Override
    public String getAlgorithmName() {
        return null;
    }

    @Override
    public void algorithm(AlgorithmContext context, String message) {

    }

}
