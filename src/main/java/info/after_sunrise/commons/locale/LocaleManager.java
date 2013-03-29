package info.after_sunrise.commons.locale;

import info.after_sunrise.commons.listener.ListenerManager;

import java.util.Locale;

/**
 * @author takanori.takase
 */
public interface LocaleManager extends ListenerManager<LocaleListener> {

	Locale getCurrent();

	void updateLocale(Locale locale);

}
