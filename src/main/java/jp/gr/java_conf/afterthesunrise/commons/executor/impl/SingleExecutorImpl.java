package jp.gr.java_conf.afterthesunrise.commons.executor.impl;

import static java.util.concurrent.Executors.newSingleThreadExecutor;
import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

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

	private volatile ExecutorService executorService;

	@Override
	public void close() throws Exception {

		synchronized (this) {

			if (executorService == null) {
				return;
			}

			executorService.shutdown();

			executorService = null;

		}

	}

	@Override
	public void execute(Runnable runnable) {

		if (runnable == null) {
			return;
		}

		synchronized (this) {

			if (executorService == null) {
				executorService = newSingleThreadExecutor(this);
			}

			executorService.execute(runnable);

		}

	}

	@Override
	public <V> Future<V> execute(Callable<V> callable) {

		if (callable == null) {
			return Futures.immediateFuture(null);
		}

		synchronized (this) {

			if (executorService == null) {
				executorService = newSingleThreadExecutor(this);
			}

			return executorService.submit(callable);

		}

	}

}
