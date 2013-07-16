package com.after_sunrise.commons.base.object;

import static com.after_sunrise.commons.base.object.Validations.checkNotNull;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilterInputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

/**
 * @author takanori.takase
 */
public class Streams {

	private Streams() {
		throw new IllegalAccessError("Utility class shouldn't be instantiated.");
	}

	protected static class UncloseableInputStream extends FilterInputStream {

		protected UncloseableInputStream(InputStream in) {
			super(checkNotNull(in));
		}

		@Override
		public void close() throws IOException {
			// Do nothing
		}

	}

	protected static class UncloseableOutputStream extends FilterOutputStream {

		protected UncloseableOutputStream(OutputStream out) {
			super(checkNotNull(out));
		}

		@Override
		public void close() throws IOException {
			// Do nothing
		}

	}

	public static InputStream wrapUncloseable(InputStream in) {
		return new UncloseableInputStream(in);
	}

	public static OutputStream wrapUncloseable(OutputStream out) {
		return new UncloseableOutputStream(out);
	}

	public static InputStream openBufferedStream(String path)
			throws IOException {

		InputStream in;

		try {

			File file = new File(path);

			if (file.exists()) {

				in = openBufferedStream(file);

			} else {

				ClassLoader cl = Streams.class.getClassLoader();

				in = openBufferedStream(cl.getResource(path));

			}

		} catch (RuntimeException e) {
			throw new IOException(e);
		}

		return in;

	}

	public static InputStream openBufferedStream(URL url) throws IOException {

		InputStream in = null;

		try {

			in = url.openStream();

			in = new BufferedInputStream(in);

		} catch (Exception e) {

			IOs.closeQuietly(in);

			throw new IOException(e);

		}

		return in;

	}

	public static InputStream openBufferedStream(File file) throws IOException {

		InputStream in = null;

		try {

			in = new FileInputStream(file);

			in = new BufferedInputStream(in);

		} catch (Exception e) {

			IOs.closeQuietly(in);

			throw new IOException(e);

		}

		return in;

	}

	public static byte[] getBytes(InputStream in) throws IOException {

		byte[] bytes = new byte[Byte.MAX_VALUE];

		byte[] buffer = new byte[0];

		int used = 0;

		int read;

		while ((read = in.read(bytes)) != -1) {

			byte[] newBuffer = new byte[buffer.length + read];

			System.arraycopy(buffer, 0, newBuffer, 0, buffer.length);

			buffer = newBuffer;

			System.arraycopy(bytes, 0, buffer, used, read);

			used += read;

		}
		;

		return buffer;

	}

	public static byte[] getBytes(String path) throws IOException {

		InputStream in = openBufferedStream(path);

		byte[] bytes;

		try {
			bytes = getBytes(in);
		} finally {
			in.close();
		}

		return bytes;

	}

	public static byte[] getBytes(URL url) throws IOException {

		InputStream in = openBufferedStream(url);

		byte[] bytes;

		try {
			bytes = getBytes(in);
		} finally {
			in.close();
		}

		return bytes;

	}

	public static byte[] getBytes(File file) throws IOException {

		InputStream in = openBufferedStream(file);

		byte[] bytes;

		try {
			bytes = getBytes(in);
		} finally {
			in.close();
		}

		return bytes;

	}

}
