package com.after_sunrise.commons.base.executor.impl;

import static java.lang.String.format;
import static java.util.concurrent.Executors.defaultThreadFactory;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author takanori.takase
 */
public abstract class AbstractExecutor implements ThreadFactory {

	private final ThreadFactory delegateFactory = defaultThreadFactory();

	private final AtomicLong count = new AtomicLong();

	private boolean daemon = true;

	private String namePrefix;

	private UncaughtExceptionHandler uncaughtExceptionHandler;

	public void setDaemon(boolean daemon) {
		this.daemon = daemon;
	}

	public void setNamePrefix(String namePrefix) {
		this.namePrefix = namePrefix;
	}

	public void setUncaughtExceptionHandler(UncaughtExceptionHandler handler) {
		this.uncaughtExceptionHandler = handler;
	}

	@Override
	public Thread newThread(Runnable r) {

		Thread t = delegateFactory.newThread(r);

		t.setDaemon(daemon);

		long i = count.incrementAndGet();

		String prefix = namePrefix;

		if (prefix != null) {
			t.setName(format("%s-%03d", prefix, i));
		}

		if (uncaughtExceptionHandler != null) {
			t.setUncaughtExceptionHandler(uncaughtExceptionHandler);
		}

		return t;

	}

}
