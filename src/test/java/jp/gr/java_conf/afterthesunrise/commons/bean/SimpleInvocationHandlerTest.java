package jp.gr.java_conf.afterthesunrise.commons.bean;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.Closeable;
import java.io.IOException;

import jp.gr.java_conf.afterthesunrise.commons.object.Proxies;

import org.junit.Before;
import org.junit.Test;

/**
 * @author takanori.takase
 */
public class SimpleInvocationHandlerTest {

	private SimpleInvocationHandler target;

	private Closeable delegate;

	private Closeable proxy;

	@Before
	public void setUp() throws Exception {

		delegate = mock(Closeable.class);

		target = new SimpleInvocationHandler(delegate);

		proxy = Proxies.delegate(Closeable.class, target);

	}

	@Test
	public void testInvoke() throws Exception {

		proxy.close();

		verify(delegate).close();

	}

	@Test(expected = IOException.class)
	public void testInvoke_Exception() throws Exception {

		doThrow(new IOException("test")).when(delegate).close();

		proxy.close();

	}

}
