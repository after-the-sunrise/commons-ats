package info.after_sunrise.commons.listener.impl;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import javax.management.NotificationListener;

import org.junit.Before;
import org.junit.Test;

/**
 * @author takanori.takase
 */
public class ProxyListenerManagerImplTest {

	private ProxyListenerManagerImpl<NotificationListener> target;

	@Before
	public void setUp() throws Exception {

		target = new ProxyListenerManagerImpl<NotificationListener>();

		target.setDelegate(new ArrayListenerManagerImpl<NotificationListener>());

	}

	@Test
	public void testProxy() {

		NotificationListener proxy = target.proxy(NotificationListener.class);

		NotificationListener listener = mock(NotificationListener.class);

		proxy.handleNotification(null, null);

		target.addListener(null);
		target.addListener(listener);

		proxy.handleNotification(null, null);

		target.removeListener(null);
		target.removeListener(listener);

		proxy.handleNotification(null, null);

		verify(listener).handleNotification(null, null);

	}

	@Test(expected = RuntimeException.class)
	public void testInvoke_IllegalAccess() throws Exception {

		NotificationListener l = mock(NotificationListener.class);

		target.addListener(l);

		target.invoke(null, l.getClass().getDeclaredMethod("clone"), null);

	}

	@Test(expected = RuntimeException.class)
	public void testInvoke_Failure() throws Exception {

		NotificationListener l = mock(NotificationListener.class);

		doThrow(new RuntimeException()).when(l).handleNotification(null, null);

		target.addListener(l);

		target.proxy(NotificationListener.class).handleNotification(null, null);

	}

}
