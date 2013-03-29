package info.after_sunrise.commons.future;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.concurrent.TimeUnit.NANOSECONDS;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.google.common.annotations.VisibleForTesting;

/**
 * @author takanori.takase
 */
public abstract class DelegatingFuture<S, T> implements Future<T> {

	private final Future<S> delegate;

	public DelegatingFuture(Future<S> delegate) {
		this.delegate = checkNotNull(delegate);
	}

	@Override
	public boolean cancel(boolean mayInterruptIfRunning) {
		return delegate.cancel(mayInterruptIfRunning);
	}

	@Override
	public boolean isCancelled() {
		return delegate.isCancelled();
	}

	@Override
	public boolean isDone() {
		return delegate.isDone();
	}

	@Override
	public T get() throws InterruptedException, ExecutionException {

		S value = delegate.get();

		Future<T> future = delegate(value);

		return future.get();

	}

	@Override
	public T get(long timeout, TimeUnit unit) throws InterruptedException,
			ExecutionException, TimeoutException {

		long startTime = nanoTime();

		long timeoutInNanos = NANOSECONDS.convert(Math.max(0, timeout), unit);

		S source = delegate.get(timeoutInNanos, NANOSECONDS);

		Future<T> future = delegate(source);

		long remaining = timeoutInNanos - (nanoTime() - startTime);

		return future.get(Math.max(0, remaining), NANOSECONDS);

	}

	@VisibleForTesting
	long nanoTime() {
		return System.nanoTime();
	}

	protected abstract Future<T> delegate(S source) throws ExecutionException;

}
