package com.after_sunrise.commons.base.listener;

/**
 * @author takanori.takase
 */
public interface ProxyListenerManager<L> extends ListenerManager<L> {

	L proxy(Class<L> clazz);

}
