package jp.gr.java_conf.afterthesunrise.commons.message.impl;

import static java.util.Locale.JAPAN;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import jp.gr.java_conf.afterthesunrise.commons.locale.LocaleManager;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.MessageSource;

/**
 * @author takanori.takase
 */
public class MessageConverterImplTest {

	private MessageConverterImpl target;

	private MessageSource messageSource;

	private LocaleManager localeManager;

	@Before
	public void setUp() throws Exception {

		messageSource = mock(MessageSource.class);

		localeManager = mock(LocaleManager.class);

		target = new MessageConverterImpl();

		target.setMessageSource(messageSource);

		target.setLocaleManager(localeManager);

		target.setKeyPrefix("foo.bar.");

	}

	@Test
	public void testConvert() {

		String key = "foo.bar.test";

		when(localeManager.getCurrent()).thenReturn(JAPAN);

		when(messageSource.getMessage(key, null, JAPAN)).thenReturn("hoge");

		assertEquals("hoge", target.convert("test", (Object[]) null));

	}

}
