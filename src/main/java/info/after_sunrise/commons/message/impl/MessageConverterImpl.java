package info.after_sunrise.commons.message.impl;

import info.after_sunrise.commons.locale.LocaleManager;
import info.after_sunrise.commons.message.MessageConverter;

import java.util.Locale;

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
