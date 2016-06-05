package com.sun.stock.algorithm;

import com.sun.stock.common.IOHandler;

/**
 * Created by zksun on 16-2-13.
 * the AlgorithmInterface and the IOHandler interface's adapter
 * create the datasource and the Algorithm's relationship
 */
public abstract class AbstractAlgorithmHandler<V, M> implements Algorithm<M>, IOHandler<V> {

    @Override
    public void execute(V value) {
        selectAlgorithm().algorithm(getContext(), createAlgorithmMessage(value));
    }

    protected abstract Algorithm selectAlgorithm();

    protected abstract M createAlgorithmMessage(V value);

    protected AlgorithmContext getContext() {
        return null;
    }


}
