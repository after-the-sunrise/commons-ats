package com.after_sunrise.commons.base.object;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.Test;

/**
 * @author takanori.takase
 */
public class SocketsTest {

	@Test(expected = IllegalAccessError.class)
	public void testConstructor() throws Throwable {

		Class<?> clazz = Sockets.class;

		Constructor<?> c = clazz.getDeclaredConstructor();

		assertTrue(Modifier.isPrivate(c.getModifiers()));

		c.setAccessible(true);

		try {
			c.newInstance();
		} catch (InvocationTargetException e) {
			throw e.getCause();
		}

	}

	@Test
	public void testCloseQuietly_Socket() throws IOException {

		Socket s = mock(Socket.class);
		Sockets.closeQuietly(s);

		doThrow(new IOException("test")).when(s).close();
		Sockets.closeQuietly(s);

		Sockets.closeQuietly((Socket) null);

		verify(s, times(2)).close();
		verifyNoMoreInteractions(s);

	}

	@Test
	public void testCloseQuietly_ServerSocket() throws IOException {

		ServerSocket s = mock(ServerSocket.class);
		Sockets.closeQuietly(s);

		doThrow(new IOException("test")).when(s).close();
		Sockets.closeQuietly(s);

		Sockets.closeQuietly((ServerSocket) null);

		verify(s, times(2)).close();
		verifyNoMoreInteractions(s);

	}

}
