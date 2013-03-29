package info.after_sunrise.commons.listener.impl;

import static com.google.common.base.Preconditions.checkNotNull;
import info.after_sunrise.commons.listener.ListenerPredicate;
import info.after_sunrise.commons.listener.PredicateListenerManager;
import info.after_sunrise.commons.listener.ProxyListenerManager;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author takanori.takase
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ProxyListenerManagerImpl<L> implements ProxyListenerManager<L>,
		InvocationHandler {

	@Autowired
	private PredicateListenerManager<L> delegate;

	public void setDelegate(PredicateListenerManager<L> delegate) {
		this.delegate = delegate;
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
