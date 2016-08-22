package com.sun.stock.core.common.storage.type;

/**
 * Created by zksun on 16-4-18.
 */
public interface DataType {

	int compare(Object a, Object b);

	int getMemory(Object object);

}
