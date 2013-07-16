package com.after_sunrise.commons.base.object;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author takanori.takase
 */
public final class Adapters {

	private Adapters() {
		throw new IllegalAccessError("Utility class shouldn't be instantiated.");
	}

	private static final Runnable RUNNABLE = new Runnable() {
		@Override
		public void run() {
		}
	};

	@SuppressWarnings("rawtypes")
	private static final Callable CALLABLE = new Callable() {
		@Override
		public Object call() throws Exception {
			return null;
		}
	};

	@SuppressWarnings("rawtypes")
	private static final Future FUTURE = new Future() {
		@Override
		public boolean cancel(boolean mayInterruptIfRunning) {
			return false;
		}

		@Override
		public Object get() {
			return null;
		}

		@Override
		public Object get(long timeout, TimeUnit unit) {
			return null;
		}

		@Override
		public boolean isCancelled() {
			return false;
		}

		@Override
		public boolean isDone() {
			return true;
		}
	};

	public static Runnable runnable() {
		return RUNNABLE;
	}

	@SuppressWarnings("unchecked")
	public static <V> Callable<V> callable() {
		return CALLABLE;
	}

	@SuppressWarnings("unchecked")
	public static <V> Future<V> future() {
		return FUTURE;
	}

}
