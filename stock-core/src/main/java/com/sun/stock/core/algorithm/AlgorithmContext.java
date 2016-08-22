package com.sun.stock.core.algorithm;

/**
 * Created by zksun on 16-2-13.
 */
public interface AlgorithmContext {

	AlgorithmContext next();

	AlgorithmContext pre();

	Algorithm node();

}
