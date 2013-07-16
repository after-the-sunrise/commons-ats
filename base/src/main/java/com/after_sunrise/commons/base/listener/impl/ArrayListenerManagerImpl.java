package com.after_sunrise.commons.base.listener.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.after_sunrise.commons.base.listener.ListenerPredicate;
import com.after_sunrise.commons.base.listener.PredicateListenerManager;

/**
 * @author takanori.takase
 */
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
