package jp.gr.java_conf.afterthesunrise.commons.executor.impl;

import static java.util.concurrent.Executors.newFixedThreadPool;
import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.locks.ReentrantLock;

import jp.gr.java_conf.afterthesunrise.commons.executor.FixedExecutor;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author takanori.takase
 */
@Component
@Scope(SCOPE_PROTOTYPE)
public class FixedExecutorImpl extends AbstractExecutor implements
		FixedExecutor {

	private static final int THREADS_DEFAULT = 10;

	private static final int THREADS_MIN = 1;

	private final ReentrantLock lock = new ReentrantLock();

	private int threads = THREADS_DEFAULT;

	private volatile ExecutorService executorService;

	public void setThreads(int threads) {
		this.threads = Math.max(THREADS_MIN, threads);
	}

	@Override
	public void close() throws Exception {

		try {

			lock.lock();

			if (executorService != null) {

				executorService.shutdown();

				executorService = null;

			}

		} finally {
			lock.unlock();
		}

	}

	@Override
	public void execute(Runnable runnable) {

		if (runnable == null) {
			return;
		}

		try {

			lock.lock();

			if (executorService == null) {
				executorService = newFixedThreadPool(threads, this);
			}

			executorService.execute(runnable);

		} finally {
			lock.unlock();
		}

	}

	@Override
	public <V> Future<V> execute(Callable<V> callable) {

		if (callable == null) {
			return null;
		}

		try {

			lock.lock();

			if (executorService == null) {
				executorService = newFixedThreadPool(threads, this);
			}

			return executorService.submit(callable);

		} finally {
			lock.unlock();
		}

	}

}
