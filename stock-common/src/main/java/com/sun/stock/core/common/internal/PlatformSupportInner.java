package com.sun.stock.core.common.internal;

import com.sun.stock.core.common.logging.Logger;
import com.sun.stock.core.common.logging.LoggerFactory;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * Created by zksun on 16-1-11.
 */
public class PlatformSupportInner {
	private static final Logger logger = LoggerFactory.getLogger(PlatformSupportInner.class);

	private static final Unsafe UNSAFE;
	private static final long ADDRESS_FIELD_OFFSET;

	static {
		boolean directBufferFreeable = false;

		try {
			Class<?> cls = SystemUtil.loadClass("sun.nio.ch.DirectBuffer", false, SystemUtil.getClassLoader(PlatformSupport.class));
			Method method = cls.getMethod("cleaner");
			if ("sun.misc.Cleaner".equals(method.getReturnType().getName())) {
				directBufferFreeable = true;
			}
		} catch (Throwable t) {
			//ignore
		}

		if (logger.isDebugEnabled()) {
			logger.debug("sun.nio.ch.DirectBuffer.cleaner():{}", directBufferFreeable ? "available" : "unavailable");
		}

		Field addressField;

		try {
			addressField = Buffer.class.getDeclaredField("address");
			addressField.setAccessible(true);
			if (addressField.getLong(ByteBuffer.allocate(1)) != 0) {
				addressField = null;
			} else {
				ByteBuffer direct = ByteBuffer.allocateDirect(1);
				if (addressField.getLong(direct) == 0) {
					addressField = null;
				}
			}
		} catch (Throwable t) {
			addressField = null;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("java.nio.Buffer.address: {}", null != addressField ? "available" : "unavailable");
		}

		Unsafe unsafe;
		if (null != addressField && directBufferFreeable) {

			Field unsafeField = null;
			try {
				unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
				unsafeField.setAccessible(true);
				unsafe = (Unsafe) unsafeField.get(null);
				if (logger.isDebugEnabled()) {
					logger.debug("sun.misc.Unsafe.theUnsafe: {}", null != unsafe ? "available" : "unavailable");
				}

				try {
					unsafe.getClass().getDeclaredMethod(
							"copyMemory",
							new Class[]{Object.class, long.class, Object.class, long.class, long.class});

					if (logger.isDebugEnabled()) {
						logger.debug("sun.misc.Unsafe.copyMemory: available");
					}
				} catch (NoSuchMethodError t) {
					if (logger.isDebugEnabled()) {
						logger.debug("sun.misc.Unsafe.copyMemory: unavailable");
					}
					throw t;
				} catch (NoSuchMethodException e) {
					if (logger.isDebugEnabled()) {
						logger.debug("sun.misc.Unsafe.copyMemory: unavailable");
					}
					throw e;
				}

			} catch (Throwable cause) {
				unsafe = null;
			}

		} else {
			unsafe = null;
		}

		UNSAFE = unsafe;

		if (null == unsafe) {
			ADDRESS_FIELD_OFFSET = -1;
		} else {
			ADDRESS_FIELD_OFFSET = objectFieldOffset(addressField);
		}
	}

	static long objectFieldOffset(Field field) {
		return UNSAFE.objectFieldOffset(field);
	}

	static boolean hasUnsafe() {
		return UNSAFE != null;
	}

	static void throwException(Throwable t) {
		UNSAFE.throwException(t);
	}

	static <T> AtomicIntegerFieldUpdater<T> newAtomicIntegerFieldUpdater(
			Class<?> tclass, String fieldName) throws Exception {
		return new UnsafeAtomicIntegerFieldUpdater<T>(UNSAFE, tclass, fieldName);
	}

}
