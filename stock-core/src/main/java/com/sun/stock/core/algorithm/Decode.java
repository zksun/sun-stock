package com.sun.stock.core.algorithm;

/**
 * Created by zksun on 6/5/16.
 */
public interface Decode<R, T> {
    R decode(T t);
}
