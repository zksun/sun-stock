package com.sun.stock.common.internal;

import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * Created by zksun on 16-1-6.
 */
public final class SystemUtil {

	public static ClassLoader getCurrentClassLoader() {
		return Thread.currentThread().getContextClassLoader();
	}

	public static ClassLoader getThreadClassLoader(Thread thread) {
		if (null == thread) {
			throw new NullPointerException("argument can not be null.");
		}
		return thread.getContextClassLoader();
	}

	public static ClassLoader getClassLoader(final Class<?> clazz) {
		if (null == System.getSecurityManager()) {
			return clazz.getClassLoader();
		} else {
			return AccessController.doPrivileged(new PrivilegedAction<ClassLoader>() {
				@Override
				public ClassLoader run() {
					return clazz.getClassLoader();
				}
			});
		}
	}

	public static Class loadClass(String className, boolean initialize, ClassLoader classLoader) throws ClassNotFoundException {
		return Class.forName(className, initialize, classLoader);
	}

	public static Class loadClass(String className, Class callingClass) throws ClassNotFoundException {
		try {
			return getCurrentClassLoader().loadClass(className);
		} catch (ClassNotFoundException e) {
			try {
				return Class.forName(className);
			} catch (ClassNotFoundException ex) {
				try {
					return SystemUtil.class.getClassLoader().loadClass(className);
				} catch (ClassNotFoundException e1) {
					return callingClass.getClassLoader().loadClass(className);
				}
			}
		}
	}

	public static ClassLoader getContextClassLoader() {
		if (System.getSecurityManager() == null) {
			return Thread.currentThread().getContextClassLoader();
		} else {
			return AccessController.doPrivileged(new PrivilegedAction<ClassLoader>() {
				@Override
				public ClassLoader run() {
					return Thread.currentThread().getContextClassLoader();
				}
			});
		}
	}

	public static ClassLoader getSystemClassLoader() {
		if (System.getSecurityManager() == null) {
			return ClassLoader.getSystemClassLoader();
		} else {
			return AccessController.doPrivileged(new PrivilegedAction<ClassLoader>() {
				@Override
				public ClassLoader run() {
					return ClassLoader.getSystemClassLoader();
				}
			});
		}
	}


}
