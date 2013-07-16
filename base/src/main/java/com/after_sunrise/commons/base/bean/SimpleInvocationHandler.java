package com.after_sunrise.commons.base.bean;

import static com.after_sunrise.commons.base.object.Validations.checkNotNull;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author takanori.takase
 */
public class SimpleInvocationHandler implements InvocationHandler {

	private Object target;

	public SimpleInvocationHandler(Object target) {
		this.target = checkNotNull(target);
	}

	@Override
	public Object invoke(Object p, Method m, Object[] v) throws Throwable {

		try {

			return m.invoke(target, v);

		} catch (InvocationTargetException e) {

			throw e.getCause();

		}

	}

}
