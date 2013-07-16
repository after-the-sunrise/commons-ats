package com.after_sunrise.commons.base.reference.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.lang.ref.Reference;

import org.junit.Before;
import org.junit.Test;

/**
 * @author takanori.takase
 */
public class ReferenceCollectorImplTest {

	private ReferenceCollectorImpl target;

	@Before
	public void setUp() throws Exception {
		target = new ReferenceCollectorImpl();
	}

	@Test
	public void test() {

		Runnable r1 = mock(Runnable.class);
		Runnable r2 = mock(Runnable.class);
		Runnable r3 = mock(Runnable.class);

		Reference<?> soft = target.collectSoft(new Object(), r1);
		Reference<?> weak = target.collectWeak(new Object(), r2);
		Reference<?> phantom = target.collectPhantom(new Object(), r3);
		Reference<?> nullable = target.collectPhantom(new Object(), null);

		soft.enqueue();
		target.run();
		verify(r1).run();

		weak.enqueue();
		phantom.enqueue();
		nullable.enqueue();
		target.run();
		verify(r2).run();
		verify(r3).run();

		target.run();

		verifyNoMoreInteractions(r1, r2, r3);

	}

}
