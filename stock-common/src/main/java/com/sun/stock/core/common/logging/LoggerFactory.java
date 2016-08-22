package com.sun.stock.core.common.logging;

import com.sun.stock.core.common.SunStockConstants;
import com.sun.stock.core.common.exception.SunStockException;
import com.sun.stock.core.common.logging.jdk.JdkLoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by zksun on 16-1-11.
 */

public abstract class LoggerFactory {
	private static final ReadWriteLock lock = new ReentrantReadWriteLock();
	private static LoggerFactory factory;

	private static final List<LoggerClass> loggers = new LinkedList<LoggerClass>();

	protected static void addDefaultLoggerFactory(LoggerClass... loggerClasses) {
		if (null == loggerClasses) throw new NullPointerException();
		lock.writeLock().lock();
		try {
			for (LoggerClass loggerClass : loggerClasses) {
				loggers.add(loggerClass);
			}
		} finally {
			lock.writeLock().unlock();
		}
	}

	public static void setLoggerFactory(LoggerFactory factory) {
		lock.writeLock().lock();
		try {
			LoggerFactory.factory = factory;
		} finally {
			lock.writeLock().unlock();
		}
	}

	public static Logger getLogger(Class<?> cls) {
		return getLoggerFactory().getLoggerImpl(cls);
	}

	public static Logger getLogger(String name) {
		return getLoggerFactory().getLoggerImpl(name);
	}


	protected abstract Logger getLoggerImpl(String name);

	protected abstract Logger getLoggerImpl(Class<?> cls);

	protected static LoggerFactory getLoggerFactory() {
		lock.readLock().lock();
		try {
			if (factory != null) {
				return factory;
			}
		} finally {
			lock.readLock().unlock();
		}
		lock.writeLock().lock();
		try {
			if (factory == null) {
				createLoggerFactory();
			}
			return factory;
		} finally {
			lock.writeLock().unlock();
		}
	}

	private static void createLoggerFactory() {
		String userLoggerFactory = System.getProperty(SunStockConstants.SCM_LOGGER_FACTORY);
		if (userLoggerFactory != null) {
			try {
				Class clazz = Class.forName(userLoggerFactory);
				factory = (LoggerFactory) clazz.newInstance();
			} catch (Exception e) {
				throw new SunStockException("System property [" + SunStockConstants.SCM_LOGGER_FACTORY +
						"] was defined as [" + userLoggerFactory + "] but there is a problem to use that LoggerFactory!", e);
			}
		} else {
			factory = new JdkLoggerFactory();
			for (LoggerClass logger : loggers) {
				if (logger.isSupported()) {
					factory = logger.createInstance();
					break;
				}
			}
		}
	}

	public static class LoggerClass<T extends LoggerFactory> {

		private final String loggerClazzName;
		private final Class<T> loggerImplClazz;

		public LoggerClass(String loggerClazzName, Class<T> loggerImplClazz) {
			this.loggerClazzName = loggerClazzName;
			this.loggerImplClazz = loggerImplClazz;
		}

		public boolean isSupported() {
			try {
				Class.forName(loggerClazzName);
				return true;
			} catch (ClassNotFoundException ignore) {
				return false;
			}
		}

		public LoggerFactory createInstance() {
			try {
				return loggerImplClazz.newInstance();
			} catch (Exception e) {
				throw new SunStockException(e);
			}
		}
	}
}
