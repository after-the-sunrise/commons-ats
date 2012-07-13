package jp.gr.java_conf.afterthesunrise.commons.listener.impl;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import jp.gr.java_conf.afterthesunrise.commons.listener.ListenerPredicate;
import jp.gr.java_conf.afterthesunrise.commons.listener.PredicateListenerManager;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author takanori.takase
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ArrayListenerManagerImpl<L> implements PredicateListenerManager<L> {

	private final List<L> listeners;

	public ArrayListenerManagerImpl() {
		this(new CopyOnWriteArrayList<L>());
	}

	public ArrayListenerManagerImpl(List<L> listeners) {
		this.listeners = checkNotNull(listeners);
	}

	@Override
	public void addListener(L listener) {

		if (listener == null) {
			return;
		}

		listeners.add(listener);

	}

	@Override
	public void removeListener(L listener) {

		if (listener == null) {
			return;
		}

		listeners.remove(listener);

	}

	@Override
	public void process(ListenerPredicate<L> predicate) {

		@SuppressWarnings("unchecked")
		L[] array = (L[]) listeners.toArray();

		for (int i = 0; i < array.length; i++) {

			predicate.process(array[i]);

		}

	}

}
