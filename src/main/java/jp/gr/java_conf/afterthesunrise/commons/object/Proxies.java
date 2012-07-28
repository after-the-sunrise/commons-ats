package jp.gr.java_conf.afterthesunrise.commons.object;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import jp.gr.java_conf.afterthesunrise.commons.bean.SimpleInvocationHandler;

/**
 * @author takanori.takase
 */
public class Proxies {

	private Proxies() {
		throw new IllegalAccessError("Utility class shouldn't be instantiated.");
	}

	public static <T> T delegate(Class<T> clazz, T delegate) {
		return delegate(clazz, delegate, new SimpleInvocationHandler(delegate));
	}

	public static <T> T delegate(Class<T> clazz, T delegate, InvocationHandler h) {

		ClassLoader cl = clazz.getClassLoader();

		Class<?>[] classes = new Class<?>[] { clazz };

		return clazz.cast(Proxy.newProxyInstance(cl, classes, h));

	}

}
