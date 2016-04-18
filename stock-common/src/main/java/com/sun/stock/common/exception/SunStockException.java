package com.sun.stock.common.exception;

/**
 * Created by zksun on 16-1-7.
 */
public class SunStockException extends RuntimeException {

	public SunStockException() {
		super();
	}

	public SunStockException(String message) {
		super(message);
	}

	public SunStockException(String message, Throwable cause) {
		super(message, cause);
	}

	public SunStockException(Throwable cause) {
		super(cause);
	}

	protected SunStockException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
