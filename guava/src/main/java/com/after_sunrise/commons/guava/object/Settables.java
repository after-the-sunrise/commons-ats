package com.after_sunrise.commons.guava.object;

import java.lang.ref.Reference;
import java.util.concurrent.Executor;

import com.after_sunrise.commons.guava.future.DelegatingFutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;

/**
 * @author takanori.takase
 */
public final class Settables {

	private Settables() {
		throw new IllegalAccessError("Utility class shouldn't be instantiated.");
	}

	@SuppressWarnings("rawtypes")
	private static final ListenableFuture EMPTY = Futures.immediateFuture(null);

	@SuppressWarnings("unchecked")
	public static <V> ListenableFuture<V> empty() {
		return EMPTY;
	}

	public static <V> boolean set(Reference<SettableFuture<V>> reference,
			V value) {

		SettableFuture<? super V> future = reference.get();

		if (future == null) {
			return false;
		}

		return future.set(value);

	}

	public static boolean setException(
			Reference<? extends SettableFuture<?>> reference,
			Throwable throwable) {

		SettableFuture<?> future = reference.get();

		if (future == null) {
			return false;
		}

		return future.setException(throwable);

	}

	public static <V> void addCallback(ListenableFuture<V> source,
			SettableFuture<? super V> target) {
		Futures.addCallback(source, new DelegatingFutureCallback<V>(target));
	}

	public static <V> void addCallback(ListenableFuture<V> source,
			SettableFuture<? super V> target, Executor executor) {
		Futures.addCallback(source, new DelegatingFutureCallback<V>(target),
				executor);
	}

}
