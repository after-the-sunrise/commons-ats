package jp.gr.java_conf.afterthesunrise.commons.listener.impl;

import static com.google.common.base.Preconditions.checkNotNull;
import jp.gr.java_conf.afterthesunrise.commons.listener.ListenerManager;

/**
 * @author takanori.takase
 */
public class ListenerAddRunnable<L> implements Runnable {

	private final ListenerManager<L> listenerManager;

	private final L listener;

	public ListenerAddRunnable(ListenerManager<L> listenerManager, L listener) {

		this.listenerManager = checkNotNull(listenerManager);

		this.listener = listener;

	}

	@Override
	public void run() {

		listenerManager.addListener(listener);

	}

	public static <L> ListenerAddRunnable<L> create(
			ListenerManager<L> listenerManager, L listener) {

		return new ListenerAddRunnable<L>(listenerManager, listener);

	}

}
