package jp.gr.java_conf.afterthesunrise.commons.object;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;

import org.junit.Test;

/**
 * @author takanori.takase
 */
public class ProxiesTest {

	@Test(expected = IllegalAccessError.class)
	public void testConstructor() throws Throwable {

		Class<?> clazz = Proxies.class;

		Constructor<?> c = clazz.getDeclaredConstructor();

		assertTrue(Modifier.isPrivate(c.getModifiers()));

		c.setAccessible(true);

		try {
			c.newInstance();
		} catch (InvocationTargetException e) {
			throw e.getCause();
		}

	}

	@Test
	public void testDelegate() throws IOException {

		Closeable mock = mock(Closeable.class);

		Closeable proxy = Proxies.delegate(Closeable.class, mock);

		assertTrue(Proxy.isProxyClass(proxy.getClass()));

		proxy.close();

		verify(mock).close();

	}

	@Test(expected = IOException.class)
	public void testDelegate_Exception() throws IOException {

		Closeable mock = mock(Closeable.class);

		doThrow(new IOException("test")).when(mock).close();

		Closeable proxy = Proxies.delegate(Closeable.class, mock);

		assertTrue(Proxy.isProxyClass(proxy.getClass()));

		proxy.close();

	}

}
