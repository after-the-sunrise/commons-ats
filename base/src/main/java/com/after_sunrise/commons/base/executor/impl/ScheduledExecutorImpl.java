package com.after_sunrise.commons.base.executor.impl;

import static java.util.concurrent.Executors.newScheduledThreadPool;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

import java.io.IOException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.locks.ReentrantLock;

import com.after_sunrise.commons.base.executor.ScheduledExecutor;

/**
 * @author takanori.takase
 */
public class ScheduledExecutorImpl extends AbstractExecutor implements
		ScheduledExecutor {

	private static final int THREADS_DEFAULT = 10;

	private static final int THREADS_MIN = 1;

	private final ReentrantLock lock = new ReentrantLock();

	private int threads = THREADS_DEFAULT;

	private volatile ScheduledExecutorService service;

	public void setThreads(int threads) {
		this.threads = Math.max(THREADS_MIN, threads);
	}

	@Override
	public void close() throws IOException {

		try {

			lock.lock();

			if (service != null) {

				service.shutdown();

			}

		} finally {
			lock.unlock();
		}

	}

	private void prepareService() {

		try {

			lock.lock();

			if (service == null) {
				service = newScheduledThreadPool(threads, this);
			}

		} finally {
			lock.unlock();
		}

	}

	@Override
	public void scheduleOnce(Runnable runnable, long delay) {

		if (runnable == null) {
			return;
		}

		prepareService();

		service.schedule(runnable, delay, MILLISECONDS);

	}

	@Override
	public void scheduleFixed(Runnable runnable, long delay) {

		if (runnable == null) {
			return;
		}

		prepareService();

		service.scheduleAtFixedRate(runnable, delay, delay, MILLISECONDS);

	}

	@Override
	public void scheduleInterval(Runnable runnable, long delay) {

		if (runnable == null) {
			return;
		}

		prepareService();

		service.scheduleWithFixedDelay(runnable, delay, delay, MILLISECONDS);

	}

}
