package com.after_sunrise.commons.base.future;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author takanori.takase
 */
public class SuccessFuture<V> implements Future<V> {

	@SuppressWarnings("rawtypes")
	private static final Future EMPTY = create(null);

	@SuppressWarnings("unchecked")
	public static <V> Future<V> empty() {
		return EMPTY;
	}

	public static <V> Future<V> create(V value) {
		return new SuccessFuture<V>(value);
	}

	private final V value;

	public SuccessFuture(V value) {
		this.value = value;
	}

	@Override
	public boolean cancel(boolean mayInterruptIfRunning) {
		return false;
	}

	@Override
	public V get() throws InterruptedException, ExecutionException {
		return value;
	}

	@Override
	public V get(long timeout, TimeUnit unit) throws InterruptedException,
			ExecutionException, TimeoutException {
		return value;
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
