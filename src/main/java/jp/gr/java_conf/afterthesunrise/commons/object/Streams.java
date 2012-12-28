package jp.gr.java_conf.afterthesunrise.commons.object;

import static java.nio.file.StandardOpenOption.READ;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.GZIPInputStream;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;

import com.google.common.io.Closeables;

/**
 * @author takanori.takase
 */
public class Streams {

	private Streams() {
		throw new IllegalAccessError("Utility class shouldn't be instantiated.");
	}

	public static InputStream openBufferedStream(URL url) throws IOException {

		InputStream in = null;

		try {

			in = url.openStream();

			in = new BufferedInputStream(in);

		} catch (IOException e) {

			Closeables.closeQuietly(in);

			throw e;

		}

		return in;

	}

	public static InputStream openBufferedStream(Path path) throws IOException {

		InputStream in = null;

		try {

			in = Files.newInputStream(path, READ);

			in = new BufferedInputStream(in);

		} catch (IOException e) {

			Closeables.closeQuietly(in);

			throw e;

		}

		return in;

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

	public static InputStream openGzipStream(Path path) throws IOException {

		InputStream in = openBufferedStream(path);

		try {

			return new GZIPInputStream(in);

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

	public static InputStream openBzip2Stream(Path path) throws IOException {

		InputStream in = openBufferedStream(path);

		try {

			return new BZip2CompressorInputStream(in);

		} catch (IOException e) {

			Closeables.closeQuietly(in);

			throw e;

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

	public static Reader openGzipReader(Path path, Charset charset)
			throws IOException {

		InputStream in = openGzipStream(path);

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

	public static Reader openBzip2Reader(Path path, Charset charset)
			throws IOException {

		InputStream in = openBzip2Stream(path);

		try {

			return new InputStreamReader(in, charset);

		} catch (RuntimeException e) {

			Closeables.closeQuietly(in);

			throw new IOException(e);

		}

	}

}