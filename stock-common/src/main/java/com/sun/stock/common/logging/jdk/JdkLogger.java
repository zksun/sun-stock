package com.sun.stock.common.logging.jdk;

import com.sun.stock.common.logging.FormattingTuple;
import com.sun.stock.common.logging.Logger;
import com.sun.stock.common.logging.MessageFormatter;

import java.util.logging.Level;

/**
 * Created by zksun on 16-1-11.
 */
public class JdkLogger implements Logger {


	private java.util.logging.Logger log;

	public JdkLogger(java.util.logging.Logger log) {
		this.log = log;
	}

	@Override
	public void trace(String msg, String... args) {

	}

	@Override
	public void trace(String msg, Object... args) {

	}

	@Override
	public void trace(String msg, Throwable ex, String... args) {

	}

	@Override
	public boolean isTraceEnabled() {
		return log.isLoggable(Level.FINEST);
	}

	@Override
	public void debug(String msg, String... args) {
		if (log.isLoggable(Level.FINE)) {
			FormattingTuple formattingTuple = MessageFormatter.arrayFormat(msg, args);
			log.log(Level.FINE, formattingTuple.getMessage(), formattingTuple.getThrowable());
		}
	}

	@Override
	public void debug(String msg, Object... args) {

	}

	@Override
	public void debug(String msg, Throwable ex, String... args) {

	}

	@Override
	public boolean isDebugEnabled() {
		return log.isLoggable(Level.FINE);
	}

	@Override
	public void info(String msg, String... args) {

	}

	@Override
	public void info(String msg, Throwable ex, String... args) {

	}

	@Override
	public boolean isInfoEnabled() {
		return false;
	}

	@Override
	public void warn(String msg, String... args) {

	}

	@Override
	public void warn(String msg, Object... args) {

	}

	@Override
	public void warn(String msg, Throwable ex, String... args) {

	}

	@Override
	public boolean isWarnEnabled() {
		return false;
	}

	@Override
	public void error(String msg, String... args) {

	}

	@Override
	public void error(String msg, Throwable ex, String... args) {

	}

	@Override
	public boolean isErrorEnabled() {
		return false;
	}

	@Override
	public void fatal(String msg, String... args) {

	}

	@Override
	public void fatal(String msg, Throwable ex, String... args) {

	}

	@Override
	public boolean isFatalEnabled() {
		return false;
	}
}
