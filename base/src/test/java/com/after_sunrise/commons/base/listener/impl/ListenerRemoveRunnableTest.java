package com.after_sunrise.commons.base.listener.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.io.Closeable;

import org.junit.Before;
import org.junit.Test;

import com.after_sunrise.commons.base.listener.ListenerManager;

/**
 * @author takanori.takase
 */
public class ListenerRemoveRunnableTest {

	private ListenerManager<Closeable> listenerManager;

	private Closeable closeable;

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {

		listenerManager = mock(ListenerManager.class);

		closeable = mock(Closeable.class);

	}

	@Test
	public void testRun() {

		Runnable r = ListenerRemoveRunnable.create(listenerManager, closeable);

		verifyNoMoreInteractions(listenerManager, closeable);

		r.run();

		verify(listenerManager).removeListener(closeable);

	}

	@Test
	public void testRun_Null() {

		Runnable r = ListenerRemoveRunnable.create(listenerManager, null);

		verifyNoMoreInteractions(listenerManager);

		r.run();

		verify(listenerManager).removeListener(null);

	}

}
