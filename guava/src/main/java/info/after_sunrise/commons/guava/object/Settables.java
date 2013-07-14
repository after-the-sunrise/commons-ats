package info.after_sunrise.commons.guava.object;

import java.lang.ref.Reference;

import com.google.common.util.concurrent.SettableFuture;

/**
 * @author takanori.takase
 */
public final class Settables {

	private Settables() {
		throw new IllegalAccessError("Utility class shouldn't be instantiated.");
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

}
