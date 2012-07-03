package jp.gr.java_conf.afterthesunrise.commons.locale;

import java.util.Locale;

import jp.gr.java_conf.afterthesunrise.commons.listener.ListenerManager;

/**
 * @author takanori.takase
 */
public interface LocaleManager extends ListenerManager<LocaleListener> {

	Locale getCurrent();

	void updateLocale(Locale locale);

}
