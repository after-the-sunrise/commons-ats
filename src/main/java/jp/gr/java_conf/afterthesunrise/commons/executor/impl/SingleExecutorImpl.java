package jp.gr.java_conf.afterthesunrise.commons.executor.impl;

import static java.util.concurrent.Executors.newSingleThreadExecutor;
import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.locks.ReentrantLock;

import jp.gr.java_conf.afterthesunrise.commons.executor.SingleExecutor;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author takanori.takase
 */
@Component
@Scope(SCOPE_PROTOTYPE)
public class SingleExecutorImpl extends AbstractExecutor implements
		SingleExecutor {

	private final ReentrantLock lock = new ReentrantLock();

	private volatile ExecutorService executorService;

	@Override
	public void close() throws IOException {

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
				executorService = newSingleThreadExecutor(this);
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
				executorService = newSingleThreadExecutor(this);
			}

			return executorService.submit(callable);

		} finally {
			lock.unlock();
		}

	}

}
