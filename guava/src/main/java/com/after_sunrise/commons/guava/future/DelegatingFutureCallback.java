package com.after_sunrise.commons.guava.future;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.SettableFuture;

/**
 * @author takanori.takase
 */
public class DelegatingFutureCallback<V> implements FutureCallback<V> {

	private final SettableFuture<? super V> future;

	public DelegatingFutureCallback(SettableFuture<? super V> future) {
		this.future = future;
	}

	@Override
	public void onSuccess(V result) {
		if (future != null) {
			future.set(result);
		}
	}

	@Override
	public void onFailure(Throwable t) {
		if (future != null) {
			future.setException(t);
		}
	}

}
