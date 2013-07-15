package com.after_sunrise.commons.spring.locale.impl;

import static com.after_sunrise.commons.base.object.Validations.checkNotNull;

import java.util.Locale;

import com.after_sunrise.commons.base.listener.ListenerPredicate;
import com.after_sunrise.commons.base.listener.PredicateListenerManager;
import com.after_sunrise.commons.base.listener.impl.ArrayListenerManagerImpl;
import com.after_sunrise.commons.spring.locale.LocaleListener;
import com.after_sunrise.commons.spring.locale.LocaleManager;

/**
 * @author takanori.takase
 */
public class LocaleManagerImpl implements LocaleManager {

	private static final Locale DEFAULT = Locale.US;

	private final PredicateListenerManager<LocaleListener> delegate;

	private volatile Locale locale = DEFAULT;

	public LocaleManagerImpl() {
		this(new ArrayListenerManagerImpl<LocaleListener>());
	}

	public LocaleManagerImpl(PredicateListenerManager<LocaleListener> delegate) {
		this.delegate = checkNotNull(delegate);
	}

	@Override
	public void addListener(LocaleListener listener) {
		delegate.addListener(listener);
	}

	@Override
	public void removeListener(LocaleListener listener) {
		delegate.removeListener(listener);
	}

	@Override
	public Locale getCurrent() {
		return locale;
	}

	@Override
	public void updateLocale(Locale locale) {

		final Locale l = locale != null ? locale : DEFAULT;

		this.locale = l;

		delegate.process(new ListenerPredicate<LocaleListener>() {
			@Override
			public void process(LocaleListener listener) {
				listener.onLocaleUpdate(l);
			}
		});

	}

}
