package com.sun.stock.core.common;

/**
 * Created by zksun on 16-2-10.
 */
public interface IOHandler<V, C> {
    void execute(V value, C context);
}
