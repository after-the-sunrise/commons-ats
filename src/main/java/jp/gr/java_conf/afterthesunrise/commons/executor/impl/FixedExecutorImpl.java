package jp.gr.java_conf.afterthesunrise.commons.executor.impl;

import static java.util.concurrent.Executors.newFixedThreadPool;
import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

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

	private int threads = THREADS_DEFAULT;

	private volatile ExecutorService executorService;

	public void setThreads(int threads) {
		this.threads = Math.max(THREADS_MIN, threads);
	}

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
				executorService = newFixedThreadPool(threads, this);
			}

			executorService.execute(runnable);

		}

	}

	@Override
	public <V> Future<V> execute(Callable<V> callable) {

		if (callable == null) {
			return null;
		}

		synchronized (this) {

			if (executorService == null) {
				executorService = newFixedThreadPool(threads, this);
			}

			return executorService.submit(callable);

		}

	}

}
