package com.after_sunrise.commons.base.thread;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Arrays;
import java.util.List;

/**
 * @author takanori.takase
 */
public class UncaughtExceptionListHandler implements UncaughtExceptionHandler {

	private final List<UncaughtExceptionHandler> handlers;

	public UncaughtExceptionListHandler(UncaughtExceptionHandler... handlers) {
		this(Arrays.asList(handlers));
	}

	public UncaughtExceptionListHandler(List<UncaughtExceptionHandler> handlers) {
		this.handlers = handlers;
	}

	@Override
	public void uncaughtException(Thread t, Throwable e) {

		for (UncaughtExceptionHandler handler : handlers) {
			handler.uncaughtException(t, e);
		}

	}

}
