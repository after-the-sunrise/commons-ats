package com.after_sunrise.commons.base.bean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.after_sunrise.commons.base.object.Proxies;

/**
 * @author takanori.takase
 */
public class TimedInvocationHandlerTest {

	private TimedInvocationHandler<Closeable> target;

	private List<Closeable> generated;

	@Before
	public void setUp() throws Exception {

		generated = new ArrayList<Closeable>();

		target = spy(new TimedInvocationHandler<Closeable>() {
			@Override
			protected Closeable generateTarget() {

				Closeable mock = mock(Closeable.class);

				generated.add(mock);

				return mock;

			}
		});

	}

	@Test
	public void testLoad() throws Throwable {

		doReturn(null).when(target).invoke(any(), any(Method.class),
				any(Object[].class));

		target.load();

		verify(target).invoke(any(), any(Method.class), any(Object[].class));

	}

	@Test(expected = RuntimeException.class)
	public void testLoad_Exception() throws Throwable {

		doThrow(new IllegalArgumentException("test")).when(target).invoke(
				any(), any(Method.class), any(Object[].class));

		target.load();

		verify(target).invoke(any(), any(Method.class), any(Object[].class));

	}

	@Test
	public void testClear() throws Exception {

		doNothing().when(target).close();

		target.clear();

		verify(target).close();

	}

	@Test
	public void testClear_Exception() throws Exception {

		doThrow(new IOException("test")).when(target).close();

		target.clear();

		verify(target).close();

	}

	@Test
	public void testRefresh() throws Exception {

		doNothing().when(target).load();

		doNothing().when(target).clear();

		target.refresh();

		verify(target).load();

		verify(target).clear();

	}

	@Test
	public void testClose() throws Exception {
		target.close();
	}

	@Test
	public void testCloseIfStale() throws Exception {

		doReturn(System.currentTimeMillis()).when(target).getLastAccess();

		target.closeIfStale();

		verify(target, never()).close();

	}

	@Test
	public void testCloseIfStale_Stale() throws Exception {

		doReturn(0L).when(target).getLastAccess();

		target.closeIfStale();

		verify(target).close();

	}

	@Test
	public void testGetTime() {

		long time = System.currentTimeMillis();

		assertTrue(time <= target.getTime());

	}

	@Test
	public void testLastAccess() {
		assertEquals(0L, target.getLastAccess());
	}

	@Test
	public void testInvoke() throws Throwable {

		Closeable proxy = Proxies.delegate(Closeable.class, target);

		assertEquals(0, generated.size());

		// Generate first
		proxy.close();

		assertEquals(1, generated.size());
		verify(generated.get(0), times(1)).close();

		// Invoke on first
		proxy.close();
		proxy.close();
		proxy.close();

		assertEquals(1, generated.size());
		verify(generated.get(0), times(4)).close();

		// Terminate first
		target.close();

		// Generate second
		proxy.close();

		assertEquals(2, generated.size());
		verify(generated.get(1), times(1)).close();

		// Not stale yet
		target.closeIfStale();

		// Invoke on second
		proxy.close();
		proxy.close();

		assertEquals(2, generated.size());
		verify(generated.get(1), times(3)).close();

	}

	@Test
	public void testInvoke_Exception() throws Throwable {

		Closeable proxy = Proxies.delegate(Closeable.class, target);

		assertEquals(0, generated.size());

		// Generate first
		proxy.close();

		assertEquals(1, generated.size());
		verify(generated.get(0), times(1)).close();

		// Simulate invocation failure
		IOException exp = new IOException("test");
		doThrow(exp).when(generated.get(0)).close();

		try {

			proxy.close();

		} catch (IOException e) {

			assertSame(exp, e);

		}

	}

}
