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
		if (isTraceEnabled()) {
			FormattingTuple formattingTuple = MessageFormatter.arrayFormat(msg, args);
			log.log(Level.FINEST, formattingTuple.getMessage(), formattingTuple.getThrowable());
		}
	}

	@Override
	public void trace(String msg, Object... args) {
		if (isTraceEnabled()) {
			FormattingTuple formattingTuple = MessageFormatter.arrayFormat(msg, args);
			log.log(Level.FINEST, formattingTuple.getMessage(), formattingTuple.getThrowable());
		}
	}

	@Override
	public void trace(String msg, Throwable ex, String... args) {
		if (isTraceEnabled()) {
			FormattingTuple formattingTuple = MessageFormatter.arrayFormat(msg, args);
			log.log(Level.FINEST, formattingTuple.getMessage(), ex);
		}
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
		if (isDebugEnabled()) {
			FormattingTuple formattingTuple = MessageFormatter.arrayFormat(msg, args);
			log.log(Level.FINE, formattingTuple.getMessage(), formattingTuple.getThrowable());
		}
	}

	@Override
	public void debug(String msg, Throwable ex, String... args) {
		if (isDebugEnabled()) {
			FormattingTuple formattingTuple = MessageFormatter.arrayFormat(msg, args);
			log.log(Level.FINE, formattingTuple.getMessage(), ex);
		}
	}

	@Override
	public boolean isDebugEnabled() {
		return log.isLoggable(Level.FINE);
	}

	@Override
	public void info(String msg, String... args) {
		if (isInfoEnabled()) {
			FormattingTuple formattingTuple = MessageFormatter.arrayFormat(msg, args);
			log.log(Level.INFO, formattingTuple.getMessage(), formattingTuple.getThrowable());
		}
	}

	@Override
	public void info(String msg, Object... args) {
		if (isInfoEnabled()) {
			FormattingTuple formattingTuple = MessageFormatter.arrayFormat(msg, args);
			log.log(Level.INFO, formattingTuple.getMessage(), formattingTuple.getThrowable());
		}
	}

	@Override
	public void info(String msg, Throwable ex, String... args) {
		if (isInfoEnabled()) {
			FormattingTuple formattingTuple = MessageFormatter.arrayFormat(msg, args);
			log.log(Level.INFO, formattingTuple.getMessage(), ex);
		}
	}

	@Override
	public boolean isInfoEnabled() {
		return log.isLoggable(Level.INFO);
	}

	@Override
	public void warn(String msg, String... args) {
		if (isWarnEnabled()) {
			FormattingTuple formattingTuple = MessageFormatter.arrayFormat(msg, args);
			log.log(Level.WARNING, formattingTuple.getMessage(), formattingTuple.getThrowable());
		}
	}

	@Override
	public void warn(String msg, Object... args) {
		if (isWarnEnabled()) {
			FormattingTuple formattingTuple = MessageFormatter.arrayFormat(msg, args);
			log.log(Level.WARNING, formattingTuple.getMessage(), formattingTuple.getThrowable());
		}
	}

	@Override
	public void warn(String msg, Throwable ex, String... args) {
		if (isWarnEnabled()) {
			FormattingTuple formattingTuple = MessageFormatter.arrayFormat(msg, args);
			log.log(Level.WARNING, formattingTuple.getMessage(), ex);
		}
	}

	@Override
	public boolean isWarnEnabled() {
		return log.isLoggable(Level.WARNING);
	}

	@Override
	public void error(String msg, String... args) {
		if (isErrorEnabled()) {
			FormattingTuple formattingTuple = MessageFormatter.arrayFormat(msg, args);
			log.log(Level.SEVERE, formattingTuple.getMessage(), formattingTuple.getThrowable());
		}
	}

	@Override
	public void error(String msg, Object... args) {
		if (isErrorEnabled()) {
			FormattingTuple formattingTuple = MessageFormatter.arrayFormat(msg, args);
			log.log(Level.SEVERE, formattingTuple.getMessage(), formattingTuple.getThrowable());
		}
	}

	@Override
	public void error(String msg, Throwable ex, String... args) {
		if (isErrorEnabled()) {
			FormattingTuple formattingTuple = MessageFormatter.arrayFormat(msg, args);
			log.log(Level.SEVERE, formattingTuple.getMessage(), ex);
		}
	}

	@Override
	public boolean isErrorEnabled() {
		return log.isLoggable(Level.SEVERE);
	}

	@Override
	public void fatal(String msg, String... args) {
		if (isFatalEnabled()) {
			FormattingTuple formattingTuple = MessageFormatter.arrayFormat(msg, args);
			log.log(Level.SEVERE, formattingTuple.getMessage(), formattingTuple.getThrowable());
		}
	}

	@Override
	public void fatal(String msg, Object... args) {
		if (isFatalEnabled()) {
			FormattingTuple formattingTuple = MessageFormatter.arrayFormat(msg, args);
			log.log(Level.SEVERE, formattingTuple.getMessage(), formattingTuple.getThrowable());
		}
	}

	@Override
	public void fatal(String msg, Throwable ex, String... args) {
		if (isFatalEnabled()) {
			FormattingTuple formattingTuple = MessageFormatter.arrayFormat(msg, args);
			log.log(Level.SEVERE, formattingTuple.getMessage(), ex);
		}
	}

	@Override
	public boolean isFatalEnabled() {
		return log.isLoggable(Level.SEVERE);
	}
}
