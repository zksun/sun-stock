package com.sun.stock.core.test.grpc.mock;

import java.util.concurrent.atomic.AtomicInteger;

public class PortNumberGenerator {
    
    private static final AtomicInteger current = new AtomicInteger(30000);
    
    public static int getPort() {
        return current.getAndIncrement();
    }
}
