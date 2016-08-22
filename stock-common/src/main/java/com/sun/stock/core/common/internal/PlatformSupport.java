package com.sun.stock.core.common.internal;

import com.sun.stock.core.common.logging.Logger;
import com.sun.stock.core.common.logging.LoggerFactory;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.regex.Pattern;

/**
 * Created by zksun on 16-1-11.
 */
public class PlatformSupport {
	private static final Logger logger = LoggerFactory.getLogger(PlatformSupport.class);

	private static final Pattern MAX_DIRECT_MEMORY_SIZE_ARG_PATTERN = Pattern.compile(
			"\\s*-XX:MaxDirectMemorySize\\s*=\\s*([0-9]+)\\s*([kKmMgG]?)\\s*$");

	private static final boolean HAS_UNSAFE = checkUnsafe();


	private static boolean checkUnsafe() {
		try {
			boolean hasUnsafe = PlatformSupportInner.hasUnsafe();
			if (logger.isDebugEnabled()) {
				logger.debug("sun.misc.Unsafe: {}", hasUnsafe ? "available" : "unavailable");
			}
			return hasUnsafe;
		} catch (Throwable t) {
			return false;
		}
	}

	public static boolean hasUnsafe() {
		return HAS_UNSAFE;
	}

	private static <E extends Throwable> void doThrowException(Throwable t) throws E {
		throw (E) t;
	}

	public static void throwException(Throwable t) {
		if (hasUnsafe()) {
			PlatformSupportInner.throwException(t);
		} else {
			PlatformSupport.<RuntimeException>doThrowException(t);
		}
	}

	public static <T> AtomicIntegerFieldUpdater<T> newAtomicIntegerFieldUpdater(
			Class<?> tclass, String fieldName) {
		if (hasUnsafe()) {
			try {
				return PlatformSupportInner.newAtomicIntegerFieldUpdater(tclass, fieldName);
			} catch (Throwable ignore) {
				// ignore
			}
		}
		return null;
	}


}
