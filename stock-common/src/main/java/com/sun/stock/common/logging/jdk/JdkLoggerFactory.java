package com.sun.stock.common.logging.jdk;


import com.sun.stock.common.logging.Logger;
import com.sun.stock.common.logging.LoggerFactory;

/**
 * Created by zksun on 16-1-11.
 */
public class JdkLoggerFactory extends LoggerFactory {

	@Override
	protected Logger getLoggerImpl(String name) {
		return new JdkLogger(java.util.logging.Logger.getLogger(name));
	}

	@Override
	protected Logger getLoggerImpl(Class<?> cls) {
		return new JdkLogger(java.util.logging.Logger.getLogger(cls.getName()));
	}
}
