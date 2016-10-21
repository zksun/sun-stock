package com.sun.stock.core.test;

import com.sun.stock.core.algorithm.AbstractAlgorithmHandler;
import com.sun.stock.core.algorithm.Algorithm;
import com.sun.stock.core.algorithm.AlgorithmContext;

/**
 * Created by zksun on 16-2-10.
 */
public class IOReadLineTestHandler extends AbstractAlgorithmHandler<AlgorithmContext,String, byte[]> {

    @Override
    protected Algorithm selectAlgorithm() {
        return null;
    }

    @Override
    protected byte[] createAlgorithmMessage(String value) {
        return new byte[0];
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
    public void algorithm(AlgorithmContext context, byte[] message) {

    }
}
