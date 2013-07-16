package com.after_sunrise.commons.base.object;

import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author takanori.takase
 */
public final class Sockets {

	private Sockets() {
		throw new IllegalAccessError("Utility class shouldn't be instantiated.");
	}

	private static class ClientCloseable implements Closeable {

		private Socket socket;

		@Override
		public void close() throws IOException {
			if (socket != null) {
				socket.close();
			}
		}

	}

	private static class ServerCloseable implements Closeable {

		private ServerSocket socket;

		@Override
		public void close() throws IOException {
			if (socket != null) {
				socket.close();
			}
		}

	}

	private static final ThreadLocal<ClientCloseable> CC = new ThreadLocal<ClientCloseable>() {
		@Override
		protected ClientCloseable initialValue() {
			return new ClientCloseable();
		}
	};

	private static final ThreadLocal<ServerCloseable> SC = new ThreadLocal<ServerCloseable>() {
		@Override
		protected ServerCloseable initialValue() {
			return new ServerCloseable();
		}
	};

	public static void closeQuietly(Socket socket) {

		ClientCloseable c = CC.get();

		c.socket = socket;

		try {

			IOs.closeQuietly(c);

		} finally {

			c.socket = null;

		}

	}

	public static void closeQuietly(ServerSocket socket) {

		ServerCloseable c = SC.get();

		c.socket = socket;

		try {

			IOs.closeQuietly(c);

		} finally {

			c.socket = null;

		}

	}

}
