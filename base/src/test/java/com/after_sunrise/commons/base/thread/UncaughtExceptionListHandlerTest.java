package com.after_sunrise.commons.base.thread;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.lang.Thread.UncaughtExceptionHandler;

import org.junit.Test;

/**
 * @author takanori.takase
 */
public class UncaughtExceptionListHandlerTest {

	@Test
	public void testUncaughtExceptionListHandler() {

		UncaughtExceptionHandler h1 = mock(UncaughtExceptionHandler.class);
		UncaughtExceptionHandler h2 = mock(UncaughtExceptionHandler.class);

		UncaughtExceptionHandler h = new UncaughtExceptionListHandler(h1, h2);

		h.uncaughtException(null, null);

		verify(h1).uncaughtException(null, null);
		verify(h2).uncaughtException(null, null);

	}

}
