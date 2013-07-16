package com.after_sunrise.commons.base.bean;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.MINUTES;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.after_sunrise.commons.base.object.IOs;

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

			invoke(null, m, new Object[0]);

		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void clear() {
		IOs.closeQuietly(this);
	}

	@Override
	public void refresh() {

		clear();

		load();

	}

	@Override
	public void close() throws IOException {
		try {

			lock.writeLock().lock();

			if (target == null) {
				return;
			}

			target.close();

			target = null;

		} finally {
			lock.writeLock().unlock();
		}
	}

	protected boolean closeIfStale() throws IOException {
		try {

			lock.writeLock().lock();

			if (target == null) {
				return false;
			}

			long limit = lastAccess + timeoutInMillis;

			if (limit >= System.currentTimeMillis()) {
				return false;
			}

			target.close();

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
