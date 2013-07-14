package info.after_sunrise.commons.base.executor.impl;

import static java.util.concurrent.Executors.newSingleThreadExecutor;
import info.after_sunrise.commons.base.executor.SingleExecutor;
import info.after_sunrise.commons.base.future.FailureFuture;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author takanori.takase
 */
public class SingleExecutorImpl extends AbstractExecutor implements
		SingleExecutor {

	private final ReentrantLock lock = new ReentrantLock();

	private volatile ExecutorService service;

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
				service = newSingleThreadExecutor(this);
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
