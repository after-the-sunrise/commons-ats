package jp.gr.java_conf.afterthesunrise.commons.listener.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

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

	private final ReadWriteLock lock = new ReentrantReadWriteLock();

	private final List<L> listeners = new ArrayList<L>();

	@Override
	public void addListener(L listener) {

		if (listener == null) {
			return;
		}

		try {

			lock.writeLock().lock();

			listeners.add(listener);

		} finally {
			lock.writeLock().unlock();
		}

	}

	@Override
	public void removeListener(L listener) {

		if (listener == null) {
			return;
		}

		try {

			lock.writeLock().lock();

			listeners.remove(listener);

		} finally {
			lock.writeLock().unlock();
		}

	}

	@Override
	public void process(ListenerPredicate<L> predicate) {

		List<L> list;

		try {

			lock.readLock().lock();

			list = new ArrayList<L>(listeners);

		} finally {
			lock.readLock().unlock();
		}

		for (int i = 0; i < list.size(); i++) {

			L listener = list.get(i);

			predicate.process(listener);

		}

	}

}
