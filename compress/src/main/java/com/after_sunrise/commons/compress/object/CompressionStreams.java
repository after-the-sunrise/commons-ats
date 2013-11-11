package com.after_sunrise.commons.compress.object;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.zip.GZIPInputStream;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;

import com.after_sunrise.commons.base.object.IOs;
import com.after_sunrise.commons.base.object.Streams;

/**
 * @author takanori.takase
 */
public class CompressionStreams {

	private CompressionStreams() {
		throw new IllegalAccessError("Utility class shouldn't be instantiated.");
	}

	public static InputStream openGzipStream(String path) throws IOException {

		InputStream in = Streams.openBufferedStream(path);

		try {

			return new GZIPInputStream(in);

		} catch (IOException e) {

			IOs.closeQuietly(in);

			throw e;

		}

	}

	public static InputStream openGzipStream(URL url) throws IOException {

		InputStream in = Streams.openBufferedStream(url);

		try {

			return new GZIPInputStream(in);

		} catch (IOException e) {

			IOs.closeQuietly(in);

			throw e;

		}

	}

	public static InputStream openGzipStream(File file) throws IOException {

		InputStream in = Streams.openBufferedStream(file);

		try {

			return new GZIPInputStream(in);

		} catch (IOException e) {

			IOs.closeQuietly(in);

			throw e;

		}

	}

	public static InputStream openBzip2Stream(String path) throws IOException {
		return openBzip2Stream(path, true);
	}

	public static InputStream openBzip2Stream(URL url) throws IOException {
		return openBzip2Stream(url, true);
	}

	public static InputStream openBzip2Stream(File file) throws IOException {
		return openBzip2Stream(file, true);
	}

	public static InputStream openBzip2Stream(String path, boolean cat)
			throws IOException {

		InputStream in = Streams.openBufferedStream(path);

		try {

			return new BZip2CompressorInputStream(in, cat);

		} catch (IOException e) {

			IOs.closeQuietly(in);

			throw e;

		}

	}

	public static InputStream openBzip2Stream(URL url, boolean cat)
			throws IOException {

		InputStream in = Streams.openBufferedStream(url);

		try {

			return new BZip2CompressorInputStream(in, cat);

		} catch (IOException e) {

			IOs.closeQuietly(in);

			throw e;

		}

	}

	public static InputStream openBzip2Stream(File file, boolean cat)
			throws IOException {

		InputStream in = Streams.openBufferedStream(file);

		try {

			return new BZip2CompressorInputStream(in, cat);

		} catch (IOException e) {

			IOs.closeQuietly(in);

			throw e;

		}

	}

	public static Reader openGzipReader(String path, Charset charset)
			throws IOException {

		InputStream in = openGzipStream(path);

		try {

			return new InputStreamReader(in, charset);

		} catch (RuntimeException e) {

			IOs.closeQuietly(in);

			throw new IOException(e);

		}

	}

	public static Reader openGzipReader(URL url, Charset charset)
			throws IOException {

		InputStream in = openGzipStream(url);

		try {

			return new InputStreamReader(in, charset);

		} catch (RuntimeException e) {

			IOs.closeQuietly(in);

			throw new IOException(e);

		}

	}

	public static Reader openGzipReader(File file, Charset charset)
			throws IOException {

		InputStream in = openGzipStream(file);

		try {

			return new InputStreamReader(in, charset);

		} catch (RuntimeException e) {

			IOs.closeQuietly(in);

			throw new IOException(e);

		}

	}

	public static Reader openBzip2Reader(String path, Charset charset)
			throws IOException {
		return openBzip2Reader(path, charset, true);
	}

	public static Reader openBzip2Reader(URL url, Charset charset)
			throws IOException {
		return openBzip2Reader(url, charset, true);
	}

	public static Reader openBzip2Reader(File file, Charset charset)
			throws IOException {
		return openBzip2Reader(file, charset, true);
	}

	public static Reader openBzip2Reader(String path, Charset charset,
			boolean cat) throws IOException {

		InputStream in = openBzip2Stream(path, cat);

		try {

			return new InputStreamReader(in, charset);

		} catch (RuntimeException e) {

			IOs.closeQuietly(in);

			throw new IOException(e);

		}

	}

	public static Reader openBzip2Reader(URL url, Charset charset, boolean cat)
			throws IOException {

		InputStream in = openBzip2Stream(url, cat);

		try {

			return new InputStreamReader(in, charset);

		} catch (RuntimeException e) {

			IOs.closeQuietly(in);

			throw new IOException(e);

		}

	}

	public static Reader openBzip2Reader(File file, Charset charset, boolean cat)
			throws IOException {

		InputStream in = openBzip2Stream(file, cat);

		try {

			return new InputStreamReader(in, charset);

		} catch (RuntimeException e) {

			IOs.closeQuietly(in);

			throw new IOException(e);

		}

	}

}
