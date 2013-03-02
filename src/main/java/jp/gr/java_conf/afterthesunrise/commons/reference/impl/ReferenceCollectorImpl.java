package jp.gr.java_conf.afterthesunrise.commons.reference.impl;

import static jp.gr.java_conf.afterthesunrise.commons.object.Logs.logTrace;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.Map;

import jp.gr.java_conf.afterthesunrise.commons.object.References;
import jp.gr.java_conf.afterthesunrise.commons.reference.ReferenceCollector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.google.common.collect.Maps;

/**
 * @author takanori.takase
 */
@Component
public class ReferenceCollectorImpl implements ReferenceCollector, Runnable {

	private final Map<Reference<?>, Runnable> refs = Maps.newConcurrentMap();

	private final ReferenceQueue<Object> queue = new ReferenceQueue<Object>();

	private final Log log = LogFactory.getLog(getClass());

	@Override
	public void run() {

		Reference<?> reference = queue.poll();

		long size = refs.size();

		long count = 0;

		while (reference != null) {

			count++;

			Runnable runnable = refs.remove(reference);

			if (runnable != null) {
				runnable.run();
			}

			reference = queue.poll();

		}

		logTrace(log, "Collected references : [%,3d / %,3d]", count, size);

	}

	@Override
	public <T> SoftReference<T> collectSoft(T target, Runnable runnable) {

		SoftReference<T> ref = References.softReference(target, queue);

		refs.put(ref, runnable);

		return ref;

	}

	@Override
	public <T> WeakReference<T> collectWeak(T target, Runnable runnable) {

		WeakReference<T> ref = References.weakReference(target, queue);

		refs.put(ref, runnable);

		return ref;

	}

	@Override
	public <T> PhantomReference<T> collectPhantom(T target, Runnable runnable) {

		PhantomReference<T> ref = References.phantomReference(target, queue);

		refs.put(ref, runnable);

		return ref;

	}

}
