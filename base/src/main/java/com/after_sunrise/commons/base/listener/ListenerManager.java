package com.after_sunrise.commons.base.listener;

/**
 * @author takanori.takase
 */
public interface ListenerManager<L> {

	void addListener(L listener);

	void removeListener(L listener);

}
