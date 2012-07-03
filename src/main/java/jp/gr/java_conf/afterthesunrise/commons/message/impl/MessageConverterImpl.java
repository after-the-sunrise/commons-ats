package jp.gr.java_conf.afterthesunrise.commons.message.impl;

import java.util.Locale;

import jp.gr.java_conf.afterthesunrise.commons.locale.LocaleManager;
import jp.gr.java_conf.afterthesunrise.commons.message.MessageConverter;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Component;

/**
 * @author takanori.takase
 */
@Component
public class MessageConverterImpl implements MessageConverter,
		MessageSourceAware {

	@Autowired
	private LocaleManager localeManager;

	private MessageSource messageSource;

	private String keyPrefix;

	@Autowired
	public void setLocaleManager(LocaleManager localeManager) {
		this.localeManager = localeManager;
	}

	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	public void setKeyPrefix(String keyPrefix) {
		this.keyPrefix = keyPrefix;
	}

	@Override
	public String convert(String key, Object... args) {

		if (StringUtils.isBlank(key)) {
			return null;
		}

		Locale locale = localeManager.getCurrent();

		if (locale == null) {
			return null;
		}

		String prefixed = key;

		if (StringUtils.isNotEmpty(keyPrefix)) {
			prefixed = keyPrefix + key;
		}

		return messageSource.getMessage(prefixed, args, locale);

	}

}
