package com.sun.stock.core.algorithm;

import com.sun.stock.core.algorithm.util.IOHandler;

/**
 * Created by zksun on 16-2-13.
 * the AlgorithmInterface and the IOHandler interface's adapter
 * create the datasource and the Algorithm's relationship
 */
public abstract class AbstractAlgorithmHandler<C extends AlgorithmContext, V, M> implements Algorithm<M>, IOHandler<V, C> {

    @Override
    public void execute(V value, C context) {
        selectAlgorithm().algorithm(context, createAlgorithmMessage(value));
    }

    protected abstract Algorithm selectAlgorithm();

    protected abstract M createAlgorithmMessage(V value);


}
