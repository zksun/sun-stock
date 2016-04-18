package com.sun.stock.common;

/**
 * Created by zksun on 16-2-10.
 */
public interface IOHandler<T, V> {
	T execute(V value);
}
