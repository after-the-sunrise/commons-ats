package jp.gr.java_conf.afterthesunrise.commons.executor.impl;

import static java.lang.String.format;
import static java.util.concurrent.Executors.defaultThreadFactory;
import static jp.gr.java_conf.afterthesunrise.commons.log.Logs.logErrors;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author takanori.takase
 */
public abstract class AbstractExecutor implements ThreadFactory,
		UncaughtExceptionHandler {

	private final Log log = LogFactory.getLog(getClass());

	private final ThreadFactory delegateFactory = defaultThreadFactory();

	private final AtomicLong count = new AtomicLong();

	private boolean daemon = true;

	private String namePrefix;

	public void setDaemon(boolean daemon) {
		this.daemon = daemon;
	}

	public void setNamePrefix(String namePrefix) {
		this.namePrefix = namePrefix;
	}

	@Override
	public Thread newThread(Runnable r) {

		Thread t = delegateFactory.newThread(r);

		t.setUncaughtExceptionHandler(this);

		t.setDaemon(daemon);

		long i = count.incrementAndGet();

		String prefix = namePrefix;

		if (StringUtils.isNotBlank(prefix)) {
			t.setName(format("%s-%,3d", prefix, i));
		}

		return t;

	}

	@Override
	public void uncaughtException(Thread t, Throwable e) {
		logErrors(log, "Uncaught Exception : %s", e, t.getName());
	}

}
