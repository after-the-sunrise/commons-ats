package jp.gr.java_conf.afterthesunrise.commons.bean;

import static com.google.common.base.Preconditions.checkNotNull;

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
