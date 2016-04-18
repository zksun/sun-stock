package com.sun.stock.algorithm;

import com.sun.stock.common.IOHandler;

/**
 * Created by zksun on 16-2-13.
 * the AlgorithmInterface and the IOHandler interface's adapter
 * create the datasource and the Algorithm's relationship
 */
public abstract class AbstractAlgorithmHandler<T, V, M> implements Algorithm<M>, IOHandler<T, V> {

	@Override
	public T execute(V value) {
		selectAlgorithm().algorithm(null, createAlgorithmMessage(value));
		return null;
	}

	protected abstract Algorithm selectAlgorithm();

	protected abstract M createAlgorithmMessage(V value);


}
