package info.after_sunrise.commons.spring.locale.impl;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import info.after_sunrise.commons.spring.locale.LocaleListener;

import java.util.Locale;

import org.junit.Before;
import org.junit.Test;

/**
 * @author takanori.takase
 */
public class LocaleManagerImplTest {

	private LocaleManagerImpl target;

	@Before
	public void setUp() throws Exception {
		target = new LocaleManagerImpl();
	}

	@Test
	public void testGetCurrent() {
		assertSame(Locale.US, target.getCurrent());
	}

	@Test
	public void testUpdateLocale() {

		LocaleListener l = mock(LocaleListener.class);

		target.addListener(null);
		target.addListener(l);

		target.updateLocale(Locale.JAPAN);
		target.updateLocale(null);

		target.removeListener(null);
		target.removeListener(l);

		target.updateLocale(Locale.CHINA);

		verify(l).onLocaleUpdate(Locale.JAPAN);
		verify(l).onLocaleUpdate(Locale.US);
		verifyNoMoreInteractions(l);

	}

}
