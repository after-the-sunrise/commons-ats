package com.after_sunrise.commons.base.reference.impl;

import static com.after_sunrise.commons.base.object.Selections.selectNotNull;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.after_sunrise.commons.base.object.Adapters;
import com.after_sunrise.commons.base.object.References;
import com.after_sunrise.commons.base.reference.ReferenceCollector;

/**
 * @author takanori.takase
 */
public class ReferenceCollectorImpl implements ReferenceCollector {

	private static final Runnable RUNNABLE = Adapters.runnable();

	private final Map<Reference<?>, Runnable> refs = new ConcurrentHashMap<Reference<?>, Runnable>();

	private final ReferenceQueue<Object> queue = new ReferenceQueue<Object>();

	@Override
	public void run() {

		Reference<?> reference = queue.poll();

		while (reference != null) {

			Runnable runnable = refs.remove(reference);

			selectNotNull(runnable, RUNNABLE).run();

			reference = queue.poll();

		}

	}

	@Override
	public <T> SoftReference<T> collectSoft(T target, Runnable runnable) {

		SoftReference<T> ref = References.softReference(target, queue);

		refs.put(ref, selectNotNull(runnable, RUNNABLE));

		return ref;

	}

	@Override
	public <T> WeakReference<T> collectWeak(T target, Runnable runnable) {

		WeakReference<T> ref = References.weakReference(target, queue);

		refs.put(ref, selectNotNull(runnable, RUNNABLE));

		return ref;

	}

	@Override
	public <T> PhantomReference<T> collectPhantom(T target, Runnable runnable) {

		PhantomReference<T> ref = References.phantomReference(target, queue);

		refs.put(ref, selectNotNull(runnable, RUNNABLE));

		return ref;

	}

}
