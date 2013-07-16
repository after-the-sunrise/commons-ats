package com.after_sunrise.commons.base.object;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import com.after_sunrise.commons.base.bean.SimpleInvocationHandler;

/**
 * @author takanori.takase
 */
public class Proxies {

	private Proxies() {
		throw new IllegalAccessError("Utility class shouldn't be instantiated.");
	}

	public static <T> T wrap(Class<T> clazz, T target) {

		SimpleInvocationHandler h = new SimpleInvocationHandler(target);

		return delegate(clazz, h);

	}

	public static <T> T delegate(Class<T> clazz, InvocationHandler h) {

		ClassLoader cl = clazz.getClassLoader();

		Class<?>[] classes = new Class<?>[] { clazz };

		return clazz.cast(Proxy.newProxyInstance(cl, classes, h));

	}

}
