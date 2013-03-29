package info.after_sunrise.commons.bean;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.MINUTES;
import static org.apache.commons.lang.ArrayUtils.EMPTY_OBJECT_ARRAY;

import java.io.Closeable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.google.common.io.Closeables;

/**
 * @author takanori.takase
 */
public abstract class TimedInvocationHandler<T extends Closeable> implements
		InvocationHandler, Closeable, CacheMxBean {

	private static final String METHOD = "getClass";

	private static final long TIMEOUT = MILLISECONDS.convert(60, MINUTES);

	private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

	private final ReentrantLock initLock = new ReentrantLock();

	private long timeoutInMillis = TIMEOUT;

	private volatile long lastAccess;

	private volatile T target;

	public void setTimeoutInMillis(long timeoutInMillis) {
		this.timeoutInMillis = timeoutInMillis;
	}

	@Override
	public void load() {
		try {

			Method m = Object.class.getMethod(METHOD);

			Object[] v = EMPTY_OBJECT_ARRAY;

			invoke(null, m, v);

		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void clear() {

		close();

	}

	@Override
	public void refresh() {

		clear();

		load();

	}

	@Override
	public void close() {
		try {

			lock.writeLock().lock();

			if (target == null) {
				return;
			}

			Closeables.closeQuietly(target);

			target = null;

		} finally {
			lock.writeLock().unlock();
		}
	}

	protected boolean closeIfStale() {
		try {

			lock.writeLock().lock();

			if (target == null) {
				return false;
			}

			long limit = lastAccess + timeoutInMillis;

			if (limit >= System.currentTimeMillis()) {
				return false;
			}

			Closeables.closeQuietly(target);

			target = null;

			return true;

		} finally {
			lock.writeLock().unlock();
		}
	}

	@Override
	public Object invoke(Object p, Method m, Object[] v) throws Throwable {
		try {

			lock.readLock().lock();

			try {
				initLock.lock();

				if (target == null) {
					target = generateTarget();
				}

			} finally {
				initLock.unlock();
			}

			lastAccess = System.currentTimeMillis();

			return m.invoke(target, v);

		} catch (InvocationTargetException e) {

			throw e.getCause();

		} finally {
			lock.readLock().unlock();
		}
	}

	protected abstract T generateTarget();

}
