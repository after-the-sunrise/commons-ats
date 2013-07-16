package com.after_sunrise.commons.base.future;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.ZERO;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;

/**
 * @author takanori.takase
 */
public class FutureHandlerTest {

	private FutureHandler<String, BigDecimal> target;

	private FutureCallback<String, BigDecimal> callback;

	private Map<String, Future<BigDecimal>> values;

	private Future<BigDecimal> future1;

	private Future<BigDecimal> future2;

	private Future<BigDecimal> future3;

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {

		future1 = mock(Future.class);
		future2 = mock(Future.class);
		future3 = mock(Future.class);

		values = new HashMap<String, Future<BigDecimal>>();
		values.put("f1", future1);
		values.put("f2", future2);
		values.put("f3", future3);
		values.put("f4", null);

		callback = mock(FutureCallback.class);

		target = new FutureHandler<String, BigDecimal>(values, callback);

	}

	@Test
	public void testFutureHandler() {

		target = new FutureHandler<String, BigDecimal>(values);

		target.run();

	}

	@Test(timeout = 5000L)
	public void testRun() throws Exception {

		when(future1.get(anyLong(), any(TimeUnit.class))).thenReturn(ZERO);
		when(future2.get(anyLong(), any(TimeUnit.class))).thenReturn(ONE);
		when(future3.get(anyLong(), any(TimeUnit.class))).thenReturn(TEN);

		target.run();

		InOrder inOrder = Mockito.inOrder(callback);
		inOrder.verify(callback).onStart();
		inOrder.verify(callback, times(4)).onSuccess(anyString(),
				any(BigDecimal.class));
		inOrder.verify(callback).onProgress(anyInt(), anyInt());
		inOrder.verify(callback).onEnd();
		inOrder.verifyNoMoreInteractions();

		assertEquals(4, target.getDone().size());
		assertEquals(0, target.getFail().size());
		assertEquals(0, target.getCancel().size());

	}

	@Test(timeout = 5000L)
	public void testRun_Exception() throws Exception {

		OngoingStubbing<BigDecimal> stub1 = when(future1.get(anyLong(),
				any(TimeUnit.class)));
		stub1 = stub1.thenThrow(new TimeoutException());
		stub1 = stub1.thenReturn(BigDecimal.ONE);

		OngoingStubbing<BigDecimal> stub2 = when(future2.get(anyLong(),
				any(TimeUnit.class)));
		stub2 = stub2.thenThrow(new TimeoutException());
		stub2 = stub2.thenThrow(new TimeoutException());
		stub2 = stub2.thenThrow(new TimeoutException());
		stub2 = stub2.thenThrow(new InterruptedException("test2"));

		RuntimeException e3 = new RuntimeException("test3");
		OngoingStubbing<BigDecimal> s3 = when(future3.get(anyLong(),
				any(TimeUnit.class)));
		s3 = s3.thenThrow(new TimeoutException());
		s3 = s3.thenThrow(new TimeoutException());
		s3 = s3.thenThrow(e3);

		target.run();

		InOrder inOrder = Mockito.inOrder(callback);
		inOrder.verify(callback).onStart();
		inOrder.verify(callback).onSuccess("f4", null);
		inOrder.verify(callback).onSuccess("f1", BigDecimal.ONE);
		inOrder.verify(callback).onProgress(4, 2);
		inOrder.verify(callback).onFailure("f3", e3);
		inOrder.verify(callback).onProgress(4, 3);
		inOrder.verify(callback).onCancel("f2");
		inOrder.verify(callback).onEnd();
		inOrder.verifyNoMoreInteractions();

		assertEquals(2, target.getDone().size());
		assertEquals(1, target.getFail().size());
		assertEquals(1, target.getCancel().size());

	}

	@Test(timeout = 5000L)
	public void testCancel() {

		assertFalse(target.isCancelled());

		target.cancel();

		assertTrue(target.isCancelled());

		target.run();

		InOrder inOrder = Mockito.inOrder(callback);
		inOrder.verify(callback).onStart();
		inOrder.verify(callback, times(4)).onCancel(anyString());
		inOrder.verify(callback).onEnd();
		inOrder.verifyNoMoreInteractions();

		assertEquals(0, target.getDone().size());
		assertEquals(0, target.getFail().size());
		assertEquals(4, target.getCancel().size());

	}

	@Test(timeout = 5000L)
	public void testCancel_MultipleRun() {

		target.cancel();

		target.run();
		target.run();
		target.run();

		InOrder inOrder = Mockito.inOrder(callback);
		inOrder.verify(callback).onStart();
		inOrder.verify(callback, times(4)).onCancel(anyString());
		inOrder.verify(callback).onEnd();

		inOrder.verify(callback).onStart();
		inOrder.verify(callback).onEnd();

		inOrder.verify(callback).onStart();
		inOrder.verify(callback).onEnd();

		inOrder.verifyNoMoreInteractions();

		assertEquals(0, target.getDone().size());
		assertEquals(0, target.getFail().size());
		assertEquals(4, target.getCancel().size());

	}

}
