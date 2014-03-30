package com.after_sunrise.commons.log.thread;

import org.junit.Before;
import org.junit.Test;

/**
 * @author takanori.takase
 */
public class UncaughtExceptionLoggerTest {

	private UncaughtExceptionLogger target;

	@Before
	public void setUp() throws Exception {
		target = new UncaughtExceptionLogger();
	}

	@Test
	public void testUncaughtException() {

		target.uncaughtException(null, new Exception("test"));

		target.uncaughtException(Thread.currentThread(), null);

	}

}
