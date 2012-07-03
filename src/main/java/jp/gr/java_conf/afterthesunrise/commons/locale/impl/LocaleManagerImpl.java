package jp.gr.java_conf.afterthesunrise.commons.locale.impl;

import java.util.Locale;

import jp.gr.java_conf.afterthesunrise.commons.listener.ListenerPredicate;
import jp.gr.java_conf.afterthesunrise.commons.listener.PredicateListenerManager;
import jp.gr.java_conf.afterthesunrise.commons.locale.LocaleListener;
import jp.gr.java_conf.afterthesunrise.commons.locale.LocaleManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Objects;

/**
 * @author takanori.takase
 */
@Component
public class LocaleManagerImpl implements LocaleManager {

	private static final Locale DEFAULT = Locale.US;

	@Autowired
	private PredicateListenerManager<LocaleListener> delegate;

	private volatile Locale locale = DEFAULT;

	public void setDeleagte(PredicateListenerManager<LocaleListener> delegate) {
		this.delegate = delegate;
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

		final Locale l = Objects.firstNonNull(locale, DEFAULT);

		this.locale = l;

		delegate.process(new ListenerPredicate<LocaleListener>() {
			@Override
			public void process(LocaleListener listener) {
				listener.onLocaleUpdate(l);
			}
		});

	}

}
