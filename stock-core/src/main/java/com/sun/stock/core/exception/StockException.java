package com.sun.stock.core.exception;

/**
 * Created by zksun on 8/22/16.
 */
public class StockException extends RuntimeException {
    
    public StockException() {
        super();
    }

    public StockException(String message) {
        super(message);
    }

    public StockException(String message, Throwable cause) {
        super(message, cause);
    }

    public StockException(Throwable cause) {
        super(cause);
    }

    protected StockException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
