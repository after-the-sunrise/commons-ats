package info.after_sunrise.commons.object;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.zip.GZIPInputStream;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;

import com.google.common.io.ByteStreams;
import com.google.common.io.Closeables;
import com.google.common.io.Resources;

/**
 * @author takanori.takase
 */
public class Streams {

	private Streams() {
		throw new IllegalAccessError("Utility class shouldn't be instantiated.");
	}

	public static InputStream openBufferedStream(String path)
			throws IOException {

		InputStream in;

		try {

			File file = new File(path);

			if (file.exists()) {

				in = openBufferedStream(file);

			} else {

				URL url = Resources.getResource(path);

				in = openBufferedStream(url);

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

			Closeables.closeQuietly(in);

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

			Closeables.closeQuietly(in);

			throw new IOException(e);

		}

		return in;

	}

	public static InputStream openGzipStream(String path) throws IOException {

		InputStream in = openBufferedStream(path);

		try {

			return new GZIPInputStream(in);

		} catch (IOException e) {

			Closeables.closeQuietly(in);

			throw e;

		}

	}

	public static InputStream openGzipStream(URL url) throws IOException {

		InputStream in = openBufferedStream(url);

		try {

			return new GZIPInputStream(in);

		} catch (IOException e) {

			Closeables.closeQuietly(in);

			throw e;

		}

	}

	public static InputStream openGzipStream(File file) throws IOException {

		InputStream in = openBufferedStream(file);

		try {

			return new GZIPInputStream(in);

		} catch (IOException e) {

			Closeables.closeQuietly(in);

			throw e;

		}

	}

	public static InputStream openBzip2Stream(String path) throws IOException {

		InputStream in = openBufferedStream(path);

		try {

			return new BZip2CompressorInputStream(in);

		} catch (IOException e) {

			Closeables.closeQuietly(in);

			throw e;

		}

	}

	public static InputStream openBzip2Stream(URL url) throws IOException {

		InputStream in = openBufferedStream(url);

		try {

			return new BZip2CompressorInputStream(in);

		} catch (IOException e) {

			Closeables.closeQuietly(in);

			throw e;

		}

	}

	public static InputStream openBzip2Stream(File file) throws IOException {

		InputStream in = openBufferedStream(file);

		try {

			return new BZip2CompressorInputStream(in);

		} catch (IOException e) {

			Closeables.closeQuietly(in);

			throw e;

		}

	}

	public static Reader openGzipReader(String path, Charset charset)
			throws IOException {

		InputStream in = openGzipStream(path);

		try {

			return new InputStreamReader(in, charset);

		} catch (RuntimeException e) {

			Closeables.closeQuietly(in);

			throw new IOException(e);

		}

	}

	public static Reader openGzipReader(URL url, Charset charset)
			throws IOException {

		InputStream in = openGzipStream(url);

		try {

			return new InputStreamReader(in, charset);

		} catch (RuntimeException e) {

			Closeables.closeQuietly(in);

			throw new IOException(e);

		}

	}

	public static Reader openGzipReader(File file, Charset charset)
			throws IOException {

		InputStream in = openGzipStream(file);

		try {

			return new InputStreamReader(in, charset);

		} catch (RuntimeException e) {

			Closeables.closeQuietly(in);

			throw new IOException(e);

		}

	}

	public static Reader openBzip2Reader(String path, Charset charset)
			throws IOException {

		InputStream in = openBzip2Stream(path);

		try {

			return new InputStreamReader(in, charset);

		} catch (RuntimeException e) {

			Closeables.closeQuietly(in);

			throw new IOException(e);

		}

	}

	public static Reader openBzip2Reader(URL url, Charset charset)
			throws IOException {

		InputStream in = openBzip2Stream(url);

		try {

			return new InputStreamReader(in, charset);

		} catch (RuntimeException e) {

			Closeables.closeQuietly(in);

			throw new IOException(e);

		}

	}

	public static Reader openBzip2Reader(File file, Charset charset)
			throws IOException {

		InputStream in = openBzip2Stream(file);

		try {

			return new InputStreamReader(in, charset);

		} catch (RuntimeException e) {

			Closeables.closeQuietly(in);

			throw new IOException(e);

		}

	}

	public static byte[] getBytes(String path) throws IOException {

		InputStream in = openBufferedStream(path);

		byte[] bytes;

		try {
			bytes = ByteStreams.toByteArray(in);
		} finally {
			in.close();
		}

		return bytes;

	}

	public static byte[] getBytes(URL url) throws IOException {

		InputStream in = openBufferedStream(url);

		byte[] bytes;

		try {
			bytes = ByteStreams.toByteArray(in);
		} finally {
			in.close();
		}

		return bytes;

	}

	public static byte[] getBytes(File file) throws IOException {

		InputStream in = openBufferedStream(file);

		byte[] bytes;

		try {
			bytes = ByteStreams.toByteArray(in);
		} finally {
			in.close();
		}

		return bytes;

	}

}
