package com.sun.stock.core.algorithm;

/**
 * Created by zksun on 16-2-13.
 */
public interface Algorithm<M> {

	Algorithm parent();

	String getAlgorithmName();

	void algorithm(AlgorithmContext context, M message);
}
