package com.after_sunrise.commons.log.thread;

import static java.lang.Thread.currentThread;

import java.lang.Thread.UncaughtExceptionHandler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author takanori.takase
 */
public class UncaughtExceptionLogger implements UncaughtExceptionHandler {

	private final Log log = LogFactory.getLog(getClass());

	@Override
	public void uncaughtException(Thread thread, Throwable throwable) {

		Thread t = thread != null ? thread : currentThread();

		log.error("Uncaught exception : " + t.getName(), throwable);

	}

}
