package jp.gr.java_conf.afterthesunrise.commons.listener.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import javax.management.NotificationListener;

import jp.gr.java_conf.afterthesunrise.commons.listener.ListenerPredicate;

import org.junit.Before;
import org.junit.Test;

/**
 * @author takanori.takase
 */
public class ArrayListenerManagerImplTest {

	private ArrayListenerManagerImpl<NotificationListener> target;

	private ListenerPredicate<NotificationListener> predicate;

	@Before
	public void setUp() throws Exception {

		target = new ArrayListenerManagerImpl<NotificationListener>();

		predicate = new ListenerPredicate<NotificationListener>() {
			@Override
			public void process(NotificationListener listener) {
				listener.handleNotification(null, null);
			}
		};

	}

	@Test
	public void testProcess() {

		target.process(predicate);

		NotificationListener listener = mock(NotificationListener.class);

		target.addListener(null);
		target.addListener(listener);

		target.process(predicate);

		target.removeListener(null);
		target.removeListener(listener);

		target.process(predicate);

		verify(listener).handleNotification(null, null);

	}

}
