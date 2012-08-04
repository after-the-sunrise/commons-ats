package jp.gr.java_conf.afterthesunrise.commons.future;

import java.util.concurrent.Executor;

import com.google.common.util.concurrent.ListenableFuture;

/**
 * @author takanori.takase
 */
public abstract class DelegatingListenableFuture<S, T> extends
		DelegatingFuture<S, T> implements ListenableFuture<T> {

	public DelegatingListenableFuture(ListenableFuture<S> delegate) {
		super(delegate);
	}

	@Override
	public void addListener(Runnable listener, Executor executor) {
		((ListenableFuture<?>) delegate).addListener(listener, executor);
	}

}
