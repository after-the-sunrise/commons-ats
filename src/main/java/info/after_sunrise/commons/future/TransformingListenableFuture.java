package info.after_sunrise.commons.future;

import java.util.concurrent.Executor;

import com.google.common.util.concurrent.ListenableFuture;

/**
 * @author takanori.takase
 */
public abstract class TransformingListenableFuture<S, V> extends
		TransformingFuture<S, V> implements ListenableFuture<V> {

	private final ListenableFuture<S> delegate;

	public TransformingListenableFuture(ListenableFuture<S> delegate) {

		super(delegate);

		this.delegate = delegate;
	}

	@Override
	public void addListener(Runnable listener, Executor executor) {
		delegate.addListener(listener, executor);
	}

}
