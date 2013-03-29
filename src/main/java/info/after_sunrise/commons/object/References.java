package info.after_sunrise.commons.object;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

import com.google.common.util.concurrent.SettableFuture;

/**
 * @author takanori.takase
 */
public final class References {

	private References() {
		throw new IllegalAccessError("Utility class shouldn't be instantiated.");
	}

	public static <T> SoftReference<T> softReference(T referent) {
		return new SoftReference<T>(referent);
	}

	public static <T> SoftReference<T> softReference(T referent,
			ReferenceQueue<? super T> queue) {
		return new SoftReference<T>(referent, queue);
	}

	public static <T> WeakReference<T> weakReference(T referent) {
		return new WeakReference<T>(referent);
	}

	public static <T> WeakReference<T> weakReference(T referent,
			ReferenceQueue<? super T> queue) {
		return new WeakReference<T>(referent, queue);
	}

	public static <T> PhantomReference<T> phantomReference(T referent,
			ReferenceQueue<? super T> queue) {
		return new PhantomReference<T>(referent, queue);
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
