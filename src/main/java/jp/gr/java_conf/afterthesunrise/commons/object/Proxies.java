package jp.gr.java_conf.afterthesunrise.commons.object;

import static com.google.common.base.Preconditions.checkNotNull;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author takanori.takase
 */
public class Proxies {

	private Proxies() {
		throw new IllegalAccessError("Utility class shouldn't be instantiated.");
	}

	private static class DelegateHandler implements InvocationHandler {

		private final Object delegate;

		public DelegateHandler(Object delegate) {
			this.delegate = checkNotNull(delegate);
		}

		@Override
		public Object invoke(Object p, Method m, Object[] v) throws Throwable {
			try {
				return m.invoke(delegate, v);
			} catch (InvocationTargetException e) {
				throw e.getCause();
			}
		}

	}

	public static <T> T delegate(Class<T> clazz, T delegate) {

		ClassLoader cl = clazz.getClassLoader();

		Class<?>[] classes = new Class<?>[] { clazz };

		InvocationHandler handler = new DelegateHandler(delegate);

		return clazz.cast(Proxy.newProxyInstance(cl, classes, handler));

	}

}
