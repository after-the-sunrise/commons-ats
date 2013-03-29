package info.after_sunrise.commons.reference;

import java.lang.ref.PhantomReference;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * @author takanori.takase
 */
public interface ReferenceCollector extends Runnable {

	<T> SoftReference<T> collectSoft(T target, Runnable runnable);

	<T> WeakReference<T> collectWeak(T target, Runnable runnable);

	<T> PhantomReference<T> collectPhantom(T target, Runnable runnable);

}
