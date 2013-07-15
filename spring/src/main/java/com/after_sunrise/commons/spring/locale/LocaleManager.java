package com.after_sunrise.commons.spring.locale;

import java.util.Locale;

import com.after_sunrise.commons.base.listener.ListenerManager;

/**
 * @author takanori.takase
 */
public interface LocaleManager extends ListenerManager<LocaleListener> {

	Locale getCurrent();

	void updateLocale(Locale locale);

}
