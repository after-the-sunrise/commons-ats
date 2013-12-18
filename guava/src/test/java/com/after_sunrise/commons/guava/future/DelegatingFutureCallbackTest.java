package com.after_sunrise.commons.guava.future;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.google.common.util.concurrent.SettableFuture;

/**
 * @author takanori.takase
 */
public class DelegatingFutureCallbackTest {

	private DelegatingFutureCallback<Integer> target;

	private SettableFuture<Number> future;

	@Before
	public void setUp() throws Exception {

		future = SettableFuture.create();

		target = new DelegatingFutureCallback<Integer>(future);

	}

	@Test
	public void testOnSuccess() {

		Integer val = 1;

		target.onSuccess(val);

		assertTrue(future.isDone());

		new DelegatingFutureCallback<Integer>(null).onSuccess(val);

	}

	@Test
	public void testOnFailure() {

		Throwable t = new RuntimeException("test");

		target.onFailure(t);

		assertTrue(future.isDone());

		new DelegatingFutureCallback<Integer>(null).onFailure(t);

	}

}
