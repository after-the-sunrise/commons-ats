package com.after_sunrise.commons.base.future;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

/**
 * @author takanori.takase
 */
public class FutureCallbackAdapterTest {

	private FutureCallbackAdapter<String, BigDecimal> target;

	@Before
	public void setUp() throws Exception {
		target = new FutureCallbackAdapter<String, BigDecimal>();
	}

	@Test
	public void testOnStart() {
		target.onStart();
	}

	@Test
	public void testOnEnd() {
		target.onEnd();
	}

	@Test
	public void testOnProgress() {
		target.onProgress(0, 0);
	}

	@Test
	public void testOnSuccess() {
		target.onSuccess(null, null);
	}

	@Test
	public void testOnFailure() {
		target.onFailure(null, null);
	}

	@Test
	public void testOnCancel() {
		target.onCancel(null);
	}

}
