package jp.gr.java_conf.afterthesunrise.commons.future;

import java.util.concurrent.Executor;

import com.google.common.util.concurrent.ListenableFuture;

/**
 * @author takanori.takase
 */
public abstract class TransformingListenableFuture<S, V> extends
		TransformingFuture<S, V> implements ListenableFuture<V> {

	public TransformingListenableFuture(ListenableFuture<S> delegate) {
		super(delegate);
	}

	@Override
	public void addListener(Runnable listener, Executor executor) {
		((ListenableFuture<?>) delegate).addListener(listener, executor);
	}

}
