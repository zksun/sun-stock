package com.sun.stock.core.informations;

/**
 * Created by zksun on 16-2-13.
 */
public interface InformationHandler<T, V> {
	T getInformation(V value);
}
