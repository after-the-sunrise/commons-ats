package info.after_sunrise.commons.base.executor.impl;

import static java.util.concurrent.Executors.newFixedThreadPool;
import info.after_sunrise.commons.base.executor.FixedExecutor;
import info.after_sunrise.commons.base.future.FailureFuture;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author takanori.takase
 */
public class FixedExecutorImpl extends AbstractExecutor implements
		FixedExecutor {

	private static final int THREADS_DEFAULT = 10;

	private static final int THREADS_MIN = 1;

	private final ReentrantLock lock = new ReentrantLock();

	private int threads = THREADS_DEFAULT;

	private volatile ExecutorService service;

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
				service = newFixedThreadPool(threads, this);
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

		prepareService();

		service.execute(runnable);

	}

	@Override
	public <V> Future<V> execute(Callable<V> callable) {

		if (callable == null) {

			Exception e = new NullPointerException("Null callable.");

			return new FailureFuture<V>(e);

		}

		prepareService();

		return service.submit(callable);

	}

}
