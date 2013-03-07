package jp.gr.java_conf.afterthesunrise.commons.future;

import java.util.concurrent.Executor;
import java.util.concurrent.Future;

import com.google.common.base.Function;
import com.google.common.util.concurrent.ListenableFuture;

/**
 * @author takanori.takase
 */
public class FunctionListenableFuture<S, T> extends FunctionFuture<S, T>
		implements ListenableFuture<T> {

	private final ListenableFuture<S> delegate;

	public FunctionListenableFuture(ListenableFuture<S> delegate,
			Function<S, Future<T>> function) {

		super(delegate, function);

		this.delegate = delegate;

	}

	@Override
	public void addListener(Runnable listener, Executor executor) {
		delegate.addListener(listener, executor);
	}

	public static <S, T> FunctionListenableFuture<S, T> create(
			ListenableFuture<S> delegate, Function<S, Future<T>> function) {
		return new FunctionListenableFuture<S, T>(delegate, function);
	}

}
