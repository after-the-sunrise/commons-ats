package com.after_sunrise.commons.spring.message.impl;

import static com.after_sunrise.commons.base.object.Validations.checkNotNull;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;

import com.after_sunrise.commons.spring.locale.LocaleManager;
import com.after_sunrise.commons.spring.locale.impl.LocaleManagerImpl;
import com.after_sunrise.commons.spring.message.MessageConverter;

/**
 * @author takanori.takase
 */
public class MessageConverterImpl implements MessageConverter,
		MessageSourceAware {

	private final LocaleManager localeManager;

	private MessageSource messageSource;

	private String keyPrefix;

	public MessageConverterImpl() {
		this(new LocaleManagerImpl());
	}

	public MessageConverterImpl(LocaleManager localeManager) {
		this.localeManager = checkNotNull(localeManager);
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

		if (key == null || key.isEmpty()) {
			return null;
		}

		Locale locale = localeManager.getCurrent();

		if (locale == null) {
			return null;
		}

		String prefixed = key;

		if (keyPrefix != null && !keyPrefix.isEmpty()) {
			prefixed = keyPrefix + key;
		}

		return messageSource.getMessage(prefixed, args, locale);

	}

}
