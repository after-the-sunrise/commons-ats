package info.after_sunrise.commons.future;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author takanori.takase
 */
public abstract class TransformingFuture<S, V> implements Future<V> {

	private final Future<S> delegate;

	public TransformingFuture(Future<S> delegate) {
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
	public V get() throws InterruptedException, ExecutionException {

		S value = delegate.get();

		return transform(value);

	}

	@Override
	public V get(long timeout, TimeUnit unit) throws InterruptedException,
			ExecutionException, TimeoutException {

		S source = delegate.get(timeout, unit);

		return transform(source);

	}

	protected abstract V transform(S source) throws ExecutionException;

}
