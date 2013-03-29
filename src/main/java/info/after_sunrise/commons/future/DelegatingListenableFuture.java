package info.after_sunrise.commons.future;

import java.util.concurrent.Executor;

import com.google.common.util.concurrent.ListenableFuture;

/**
 * @author takanori.takase
 */
public abstract class DelegatingListenableFuture<S, T> extends
		DelegatingFuture<S, T> implements ListenableFuture<T> {

	private final ListenableFuture<S> delegate;

	public DelegatingListenableFuture(ListenableFuture<S> delegate) {

		super(delegate);

		this.delegate = delegate;

	}

	@Override
	public void addListener(Runnable listener, Executor executor) {
		delegate.addListener(listener, executor);
	}

}
