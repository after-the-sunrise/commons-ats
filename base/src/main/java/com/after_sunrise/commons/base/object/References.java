package com.after_sunrise.commons.base.object;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

import com.after_sunrise.commons.base.bean.Reference;
import com.after_sunrise.commons.base.bean.ReferenceCache;

/**
 * @author takanori.takase
 */
public final class References {

	private References() {
		throw new IllegalAccessError("Utility class shouldn't be instantiated.");
	}

	public static <T> Reference<T> refecence(T value) {
		return new Reference<T>(value);
	}

	public static <K, V> ReferenceCache<K, V> referecenCache() {
		return new ReferenceCache<K, V>();
	}

	public static <K, V> ReferenceCache<K, V> referecenCache(int capacity) {
		return new ReferenceCache<K, V>(capacity);
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

}
