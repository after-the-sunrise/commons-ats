package jp.gr.java_conf.afterthesunrise.commons.future;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.concurrent.Future;

import org.junit.Before;
import org.junit.Test;

/**
 * @author takanori.takase
 */
public class TransformingFutureTest {

	private TransformingFuture<String, Integer> target;

	private Future<String> future;

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {

		future = mock(Future.class);

		target = new TransformingFuture<String, Integer>(future) {
			@Override
			protected Integer transform(String source) {
				return Integer.parseInt(source);
			}
		};

	}

	@Test(expected = NullPointerException.class)
	public void testDelegatingFuture() {
		target = new TransformingFuture<String, Integer>(null) {
			@Override
			protected Integer transform(String source) {
				return Integer.parseInt(source);
			}
		};
	}

	@Test
	public void testCancel() {

		target.cancel(true);
		verify(future).cancel(true);

		target.cancel(false);
		verify(future).cancel(false);

	}

	@Test
	public void testIsCancelled() {
		when(future.isCancelled()).thenReturn(true, false);
		assertTrue(target.isCancelled());
		assertFalse(target.isCancelled());
	}

	@Test
	public void testIsDone() {
		when(future.isDone()).thenReturn(true, false);
		assertTrue(target.isDone());
		assertFalse(target.isDone());
	}

	@Test
	public void testGet() throws Exception {

		when(future.get()).thenReturn("123");

		assertEquals(Integer.valueOf(123), target.get());

	}

	@Test(timeout = 5000L)
	public void testGet_WithTimeout() throws Exception {

		when(future.get(100, MILLISECONDS)).thenReturn("123");

		assertEquals(Integer.valueOf(123), target.get(100, MILLISECONDS));

	}

}
