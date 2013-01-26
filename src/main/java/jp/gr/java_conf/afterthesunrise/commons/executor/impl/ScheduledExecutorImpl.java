package jp.gr.java_conf.afterthesunrise.commons.executor.impl;

import static java.util.concurrent.Executors.newScheduledThreadPool;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

import java.io.IOException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.locks.ReentrantLock;

import jp.gr.java_conf.afterthesunrise.commons.executor.ScheduledExecutor;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author takanori.takase
 */
@Component
@Scope(SCOPE_PROTOTYPE)
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

				service = null;

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

		try {

			lock.lock();

			if (service == null) {
				service = newScheduledThreadPool(threads, this);
			}

			service.schedule(runnable, delay, MILLISECONDS);

		} finally {
			lock.unlock();
		}

	}

	@Override
	public void scheduleFixed(Runnable runnable, long delay) {

		if (runnable == null) {
			return;
		}

		try {

			lock.lock();

			if (service == null) {
				service = newScheduledThreadPool(threads, this);
			}

			service.scheduleAtFixedRate(runnable, delay, delay, MILLISECONDS);

		} finally {
			lock.unlock();
		}

	}

	@Override
	public void scheduleInterval(Runnable runnable, long delay) {

		if (runnable == null) {
			return;
		}

		try {

			lock.lock();

			if (service == null) {
				service = newScheduledThreadPool(threads, this);
			}

			service.scheduleWithFixedDelay(runnable, delay, delay, MILLISECONDS);

		} finally {
			lock.unlock();
		}

	}
}
