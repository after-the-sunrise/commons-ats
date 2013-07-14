package info.after_sunrise.commons.base.future;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author takanori.takase
 */
public class FailureFuture<V> implements Future<V> {

	private final Throwable throwable;

	public FailureFuture(Throwable throwable) {
		this.throwable = throwable;
	}

	@Override
	public boolean cancel(boolean mayInterruptIfRunning) {
		return false;
	}

	@Override
	public V get() throws InterruptedException, ExecutionException {
		throw new ExecutionException(throwable);
	}

	@Override
	public V get(long timeout, TimeUnit unit) throws InterruptedException,
			ExecutionException, TimeoutException {
		throw new ExecutionException(throwable);
	}

	@Override
	public boolean isCancelled() {
		return false;
	}

	@Override
	public boolean isDone() {
		return true;
	}

}
