package info.after_sunrise.commons.message.impl;

import static java.util.Locale.JAPAN;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import info.after_sunrise.commons.locale.LocaleManager;

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

	@Test
	public void testConvert_NullKey() {

		// Preset mocks
		testConvert();

		assertNull(target.convert(null, (Object[]) null));

	}

	@Test
	public void testConvert_NullLocale() {

		// Preset mocks
		testConvert();

		when(localeManager.getCurrent()).thenReturn(null);

		assertNull(target.convert("test", (Object[]) null));

	}

	@Test
	public void testConvert_NullPrefix() {

		// Preset mocks
		testConvert();

		target.setKeyPrefix(null);

		assertEquals("hoge", target.convert("foo.bar.test", (Object[]) null));

	}

}
