package jp.gr.java_conf.afterthesunrise.commons.reference;

import java.lang.ref.PhantomReference;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * @author takanori.takase
 */
public interface ReferenceCollector {

	<T> SoftReference<T> collectSoft(T target, Runnable runnable);

	<T> WeakReference<T> collectWeak(T target, Runnable runnable);

	<T> PhantomReference<T> collectPhantom(T target, Runnable runnable);

}
