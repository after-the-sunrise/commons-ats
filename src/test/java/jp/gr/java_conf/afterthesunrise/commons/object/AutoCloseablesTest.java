package jp.gr.java_conf.afterthesunrise.commons.object;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.junit.Test;

/**
 * @author takanori.takase
 */
public class AutoCloseablesTest {

	@Test(expected = IllegalAccessError.class)
	public void testConstructor() throws Throwable {

		Class<?> clazz = AutoCloseables.class;

		Constructor<?> c = clazz.getDeclaredConstructor();

		c.setAccessible(true);

		try {
			c.newInstance();
		} catch (InvocationTargetException e) {
			throw e.getCause();
		}

	}

	@Test
	public void testCloseQuietly() throws Exception {

		AutoCloseable c = mock(AutoCloseable.class);

		AutoCloseables.closeQuietly(c);

		verify(c).close();

	}

	@Test
	public void testCloseQuietly_Exception() throws Exception {

		AutoCloseable c = mock(AutoCloseable.class);

		doThrow(new IOException("test")).when(c).close();

		AutoCloseables.closeQuietly(c);

		verify(c).close();

	}

	@Test
	public void testCloseQuietly_Null() throws Exception {
		AutoCloseables.closeQuietly(null);
	}

}
