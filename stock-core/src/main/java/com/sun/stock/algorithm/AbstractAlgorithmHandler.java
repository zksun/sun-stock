package com.sun.stock.algorithm;

import com.sun.stock.common.IOHandler;

/**
 * Created by zksun on 16-2-13.
 * the AlgorithmInterface and the IOHandler interface's adapter
 * create the datasource and the Algorithm's relationship
 */
public abstract class AbstractAlgorithmHandler<A extends AlgorithmContext, V, M> implements Algorithm<M>, IOHandler<V, A> {

    @Override
    public void execute(V value, A context) {
        selectAlgorithm().algorithm(context, createAlgorithmMessage(value));
    }

    protected abstract Algorithm selectAlgorithm();

    protected abstract M createAlgorithmMessage(V value);


}
