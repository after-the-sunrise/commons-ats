package info.after_sunrise.commons.spring.locale;

import info.after_sunrise.commons.base.listener.ListenerManager;

import java.util.Locale;

/**
 * @author takanori.takase
 */
public interface LocaleManager extends ListenerManager<LocaleListener> {

	Locale getCurrent();

	void updateLocale(Locale locale);

}
