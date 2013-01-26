package jp.gr.java_conf.afterthesunrise.commons.future;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.NANOSECONDS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Future;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 * @author takanori.takase
 */
public class DelegatingFutureTest {

	private DelegatingFuture<String, Integer> target;

	private Future<String> sourceFuture;

	private Future<Integer> targetFuture;

	private String source;

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {

		sourceFuture = mock(Future.class);

		targetFuture = mock(Future.class);

		source = null;

		target = new DelegatingFuture<String, Integer>(sourceFuture) {
			@Override
			protected Future<Integer> delegate(String s) {

				source = s;

				return targetFuture;

			}
		};

	}

	@Test(expected = NullPointerException.class)
	public void testDelegatingFuture() {
		target = new DelegatingFuture<String, Integer>(null) {
			@Override
			protected Future<Integer> delegate(String source) {
				return null;
			}
		};
	}

	@Test
	public void testCancel() {

		target.cancel(true);
		verify(sourceFuture).cancel(true);

		target.cancel(false);
		verify(sourceFuture).cancel(false);

	}

	@Test
	public void testIsCancelled() {
		when(sourceFuture.isCancelled()).thenReturn(true, false);
		assertTrue(target.isCancelled());
		assertFalse(target.isCancelled());
	}

	@Test
	public void testIsDone() {
		when(sourceFuture.isDone()).thenReturn(true, false);
		assertTrue(target.isDone());
		assertFalse(target.isDone());
	}

	@Test
	public void testGet() throws Exception {

		when(sourceFuture.get()).thenReturn("123");

		when(targetFuture.get()).thenReturn(123);

		assertEquals(Integer.valueOf(123), target.get());

		assertEquals("123", source);

	}

	@Test
	public void testGet_WithTimeout() throws Exception {

		target = spy(target);

		// Source future takes 100 nanoseconds to execute.
		final Queue<Long> queue = new LinkedList<Long>();
		queue.add(-500L);
		queue.add(-400L);

		doAnswer(new Answer<Long>() {
			@Override
			public Long answer(InvocationOnMock invocation) {
				return queue.poll();
			}
		}).when(target).nanoTime();

		when(sourceFuture.get(200000000, NANOSECONDS)).thenReturn("123");

		// Target future is invoked with timeout minus 100 nanoseconds.
		when(targetFuture.get(199999900, NANOSECONDS)).thenReturn(123);

		assertEquals(Integer.valueOf(123), target.get(200, MILLISECONDS));

		assertEquals("123", source);

	}

	@Test
	public void testGet_WithTimeout_NoRemaining() throws Exception {

		target = spy(target);

		final Queue<Long> queue = new LinkedList<Long>();
		queue.add(-800000000L);
		queue.add(-200000000L);

		doAnswer(new Answer<Long>() {
			@Override
			public Long answer(InvocationOnMock invocation) {
				return queue.poll();
			}
		}).when(target).nanoTime();

		when(sourceFuture.get(200000000, NANOSECONDS)).thenReturn("123");

		when(targetFuture.get(0, NANOSECONDS)).thenReturn(123);

		assertEquals(Integer.valueOf(123), target.get(200, MILLISECONDS));

		assertEquals("123", source);

	}

	@Test(timeout = 5000L)
	public void testGet_WithTimeout_NegativeTimeout() throws Exception {

		when(sourceFuture.get(0, NANOSECONDS)).thenReturn("123");

		when(targetFuture.get(0, NANOSECONDS)).thenReturn(123);

		assertEquals(Integer.valueOf(123), target.get(-200, MILLISECONDS));

		assertEquals("123", source);

	}

}
