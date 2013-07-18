package com.after_sunrise.commons.base.bean;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.MINUTES;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
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

	private final ReentrantReadWriteLock closeLock = new ReentrantReadWriteLock();

	private final ReentrantLock initLock = new ReentrantLock();

	private final AtomicReference<T> reference = new AtomicReference<T>();

	private final AtomicLong lastAccess = new AtomicLong();

	private final long timeoutInMillis;

	public TimedInvocationHandler() {
		this(TIMEOUT);
	}

	public TimedInvocationHandler(long timeoutInMillis) {
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

			closeLock.writeLock().lock();

			T target = reference.get();

			if (target != null) {

				target.close();

				reference.set(null);

			}

		} finally {
			closeLock.writeLock().unlock();
		}
	}

	protected void closeIfStale() throws IOException {
		try {

			closeLock.writeLock().lock();

			long timeLimit = getLastAccess() + timeoutInMillis;

			if (timeLimit < getTime()) {

				close();

			}

		} finally {
			closeLock.writeLock().unlock();
		}
	}

	protected long getTime() {
		return System.currentTimeMillis();
	}

	protected long getLastAccess() {
		return lastAccess.get();
	}

	@Override
	public Object invoke(Object p, Method m, Object[] v) throws Throwable {
		try {

			closeLock.readLock().lock();

			T target;

			try {

				initLock.lock();

				target = reference.get();

				if (target == null) {

					target = generateTarget();

					reference.set(target);

				}

			} finally {
				initLock.unlock();
			}

			lastAccess.set(getTime());

			return m.invoke(target, v);

		} catch (InvocationTargetException e) {
			throw e.getCause();
		} finally {
			closeLock.readLock().unlock();
		}
	}

	protected abstract T generateTarget();

}
