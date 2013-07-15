package com.after_sunrise.commons.base.listener.impl;

import static com.after_sunrise.commons.base.object.Validations.checkNotNull;

import com.after_sunrise.commons.base.listener.ListenerManager;

/**
 * @author takanori.takase
 */
public class ListenerRemoveRunnable<L> implements Runnable {

	private final ListenerManager<L> listenerManager;

	private final L listener;

	public ListenerRemoveRunnable(ListenerManager<L> listenerManager, L listener) {

		this.listenerManager = checkNotNull(listenerManager);

		this.listener = listener;

	}

	@Override
	public void run() {

		listenerManager.removeListener(listener);

	}

	public static <L> ListenerRemoveRunnable<L> create(
			ListenerManager<L> listenerManager, L listener) {

		return new ListenerRemoveRunnable<L>(listenerManager, listener);

	}

}
