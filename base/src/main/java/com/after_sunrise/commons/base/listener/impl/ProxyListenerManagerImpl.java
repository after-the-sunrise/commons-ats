package com.after_sunrise.commons.base.listener.impl;

import static com.after_sunrise.commons.base.object.Validations.checkNotNull;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.after_sunrise.commons.base.listener.ListenerPredicate;
import com.after_sunrise.commons.base.listener.PredicateListenerManager;
import com.after_sunrise.commons.base.listener.ProxyListenerManager;

/**
 * @author takanori.takase
 */
public class ProxyListenerManagerImpl<L> implements ProxyListenerManager<L>,
		InvocationHandler {

	private final PredicateListenerManager<L> delegate;

	public ProxyListenerManagerImpl() {
		this(new ArrayListenerManagerImpl<L>());
	}

	public ProxyListenerManagerImpl(PredicateListenerManager<L> delegate) {
		this.delegate = checkNotNull(delegate);
	}

	@Override
	public void addListener(L listener) {
		delegate.addListener(listener);
	}

	@Override
	public void removeListener(L listener) {
		delegate.removeListener(listener);
	}

	@Override
	public L proxy(Class<L> clazz) {

		ClassLoader cl = checkNotNull(clazz).getClassLoader();

		Class<?>[] classes = { clazz };

		Object p = Proxy.newProxyInstance(cl, classes, this);

		return clazz.cast(p);

	}

	@Override
	public Object invoke(Object proxy, final Method method, final Object[] args) {

		delegate.process(new ListenerPredicate<L>() {
			@Override
			public void process(L listener) {

				try {

					method.invoke(listener, args);

				} catch (IllegalAccessException e) {

					throw new RuntimeException(e);

				} catch (InvocationTargetException e) {

					throw new RuntimeException(e.getCause());

				}

			}
		});

		return null;

	}

}
