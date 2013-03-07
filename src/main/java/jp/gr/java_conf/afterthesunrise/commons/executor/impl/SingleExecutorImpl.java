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

import com.google.common.util.concurrent.Futures;

/**
 * @author takanori.takase
 */
@Component
@Scope(SCOPE_PROTOTYPE)
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

			return Futures.immediateFailedFuture(e);

		}

		prepareService();

		return service.submit(callable);

	}

}
