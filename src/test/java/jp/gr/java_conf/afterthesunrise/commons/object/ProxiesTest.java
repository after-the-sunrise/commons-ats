package jp.gr.java_conf.afterthesunrise.commons.object;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;

import jp.gr.java_conf.afterthesunrise.commons.bean.SimpleInvocationHandler;

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
	public void testWrap() throws IOException {

		Closeable mock = mock(Closeable.class);

		Closeable proxy = Proxies.wrap(Closeable.class, mock);

		assertTrue(Proxy.isProxyClass(proxy.getClass()));

		proxy.close();

		verify(mock).close();

	}

	@Test
	public void testDelegate() throws IOException {

		Closeable mock = mock(Closeable.class);

		SimpleInvocationHandler handler = new SimpleInvocationHandler(mock);

		Closeable proxy = Proxies.delegate(Closeable.class, handler);

		assertTrue(Proxy.isProxyClass(proxy.getClass()));

		proxy.close();

		verify(mock).close();

	}

}
