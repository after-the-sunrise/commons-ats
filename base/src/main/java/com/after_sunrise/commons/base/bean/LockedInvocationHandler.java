package com.after_sunrise.commons.base.bean;

import static com.after_sunrise.commons.base.object.Validations.checkNotNull;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author takanori.takase
 */
public class LockedInvocationHandler implements InvocationHandler {

	private final ReentrantLock lock = new ReentrantLock();

	private Object target;

	public LockedInvocationHandler(Object target) {
		this.target = checkNotNull(target);
	}

	@Override
	public Object invoke(Object p, Method m, Object[] v) throws Throwable {

		try {

			lock.lock();

			return m.invoke(target, v);

		} catch (InvocationTargetException e) {

			throw e.getCause();

		} finally {
			lock.unlock();
		}

	}

}
