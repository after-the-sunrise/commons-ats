package jp.gr.java_conf.afterthesunrise.commons.object;

import static com.google.common.base.Charsets.UTF_8;
import static com.google.common.io.Resources.getResource;
import static jp.gr.java_conf.afterthesunrise.commons.object.Streams.openBufferedStream;
import static jp.gr.java_conf.afterthesunrise.commons.object.Streams.openBzip2Reader;
import static jp.gr.java_conf.afterthesunrise.commons.object.Streams.openBzip2Stream;
import static jp.gr.java_conf.afterthesunrise.commons.object.Streams.openGzipReader;
import static jp.gr.java_conf.afterthesunrise.commons.object.Streams.openGzipStream;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.List;

import org.junit.After;
import org.junit.Test;

import com.google.common.io.CharStreams;
import com.google.common.io.Closeables;

/**
 * @author takanori.takase
 */
public class StreamsTest {

	private static final URL URL_GZIP = getResource("sample.txt.gz");

	private static final URL URL_BZIP = getResource("sample.txt.bz2");

	private static final File PATH_GZIP = new File(
			"src/test/resources/sample.txt.gz");

	private static final File PATH_BZIP = new File(
			"src/test/resources/sample.txt.bz2");

	private InputStream in;

	private Reader reader;

	@After
	public void tearDown() {

		Closeables.closeQuietly(reader);

		Closeables.closeQuietly(in);

	}

	@Test(expected = IllegalAccessError.class)
	public void testConstructor() throws Throwable {

		Class<?> clazz = Streams.class;

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
	public void testOpenBufferedStream_String_Url() throws IOException {

		in = openBufferedStream(URL_GZIP.getPath());

		assertTrue(in instanceof BufferedInputStream);

	}

	@Test
	public void testOpenBufferedStream_String_Path() throws IOException {

		in = openBufferedStream(PATH_GZIP.getAbsolutePath());

		assertTrue(in instanceof BufferedInputStream);

	}

	@Test
	public void testOpenBufferedStream_URL() throws IOException {

		in = openBufferedStream(URL_GZIP);

		assertTrue(in instanceof BufferedInputStream);

	}

	@Test
	public void testOpenBufferedStream_Path() throws IOException {

		in = openBufferedStream(PATH_GZIP);

		assertTrue(in instanceof BufferedInputStream);

	}

	@Test(expected = IOException.class)
	public void testOpenBufferedStream_String_Exception() throws IOException {
		in = openBufferedStream((String) null);
	}

	@Test(expected = IOException.class)
	public void testOpenBufferedStream_URL_Exception() throws IOException {
		in = openBufferedStream((URL) null);
	}

	@Test(expected = IOException.class)
	public void testOpenBufferedStream_Path_Exception() throws IOException {
		in = openBufferedStream((File) null);
	}

	@Test
	public void testOpenGzipStream_String() throws IOException {

		in = openGzipStream(URL_GZIP.getPath());

		reader = new InputStreamReader(in);

		List<String> lines = CharStreams.readLines(reader);

		assertEquals(1, lines.size());

		assertEquals("sample gzip", lines.get(0));

	}

	@Test
	public void testOpenGzipStream_URL() throws IOException {

		in = openGzipStream(URL_GZIP);

		reader = new InputStreamReader(in);

		List<String> lines = CharStreams.readLines(reader);

		assertEquals(1, lines.size());

		assertEquals("sample gzip", lines.get(0));

	}

	@Test
	public void testOpenGzipStream_Path() throws IOException {

		in = openGzipStream(PATH_GZIP);

		reader = new InputStreamReader(in);

		List<String> lines = CharStreams.readLines(reader);

		assertEquals(1, lines.size());

		assertEquals("sample gzip", lines.get(0));

	}

	@Test(expected = IOException.class)
	public void testOpenGzipStream_String_Exception() throws IOException {
		in = openGzipStream(URL_BZIP.getPath());
	}

	@Test(expected = IOException.class)
	public void testOpenGzipStream_URL_Exception() throws IOException {
		in = openGzipStream(URL_BZIP);
	}

	@Test(expected = IOException.class)
	public void testOpenGzipStream_Path_Exception() throws IOException {
		in = openGzipStream(PATH_BZIP);
	}

	@Test
	public void testOpenBzip2Stream_String() throws IOException {

		in = openBzip2Stream(URL_BZIP.getPath());

		reader = new InputStreamReader(in);

		List<String> lines = CharStreams.readLines(reader);

		assertEquals(1, lines.size());

		assertEquals("sample bz2", lines.get(0));

	}

	@Test
	public void testOpenBzip2Stream_URL() throws IOException {

		in = openBzip2Stream(URL_BZIP);

		reader = new InputStreamReader(in);

		List<String> lines = CharStreams.readLines(reader);

		assertEquals(1, lines.size());

		assertEquals("sample bz2", lines.get(0));

	}

	@Test
	public void testOpenBzip2Stream_Path() throws IOException {

		in = openBzip2Stream(PATH_BZIP);

		reader = new InputStreamReader(in);

		List<String> lines = CharStreams.readLines(reader);

		assertEquals(1, lines.size());

		assertEquals("sample bz2", lines.get(0));

	}

	@Test(expected = IOException.class)
	public void testOpenBzip2Stream_String_Exception() throws IOException {
		in = openBzip2Stream(URL_GZIP.getPath());
	}

	@Test(expected = IOException.class)
	public void testOpenBzip2Stream_URL_Exception() throws IOException {
		in = openBzip2Stream(URL_GZIP);
	}

	@Test(expected = IOException.class)
	public void testOpenBzip2Stream_Path_Exception() throws IOException {
		in = openBzip2Stream(PATH_GZIP);
	}

	@Test
	public void testOpenGzipReader_String() throws IOException {

		reader = openGzipReader(URL_GZIP.getPath(), UTF_8);

		List<String> lines = CharStreams.readLines(reader);

		assertEquals(1, lines.size());

		assertEquals("sample gzip", lines.get(0));

	}

	@Test
	public void testOpenGzipReader_URL() throws IOException {

		reader = openGzipReader(URL_GZIP, UTF_8);

		List<String> lines = CharStreams.readLines(reader);

		assertEquals(1, lines.size());

		assertEquals("sample gzip", lines.get(0));

	}

	@Test
	public void testOpenGzipReader_Path() throws IOException {

		reader = openGzipReader(PATH_GZIP, UTF_8);

		List<String> lines = CharStreams.readLines(reader);

		assertEquals(1, lines.size());

		assertEquals("sample gzip", lines.get(0));

	}

	@Test(expected = IOException.class)
	public void testOpenGzipReader_String_Exception() throws IOException {
		reader = openGzipReader(URL_BZIP.getPath(), null);
	}

	@Test(expected = IOException.class)
	public void testOpenGzipReader_URL_Exception() throws IOException {
		reader = openGzipReader(URL_BZIP, null);
	}

	@Test(expected = IOException.class)
	public void testOpenGzipReader_Path_Exception() throws IOException {
		reader = openGzipReader(PATH_BZIP, null);
	}

	@Test
	public void testOpenBzip2Reader_String() throws IOException {

		reader = openBzip2Reader(URL_BZIP.getPath(), UTF_8);

		List<String> lines = CharStreams.readLines(reader);

		assertEquals(1, lines.size());

		assertEquals("sample bz2", lines.get(0));

	}

	@Test
	public void testOpenBzip2Reader_URL() throws IOException {

		reader = openBzip2Reader(URL_BZIP, UTF_8);

		List<String> lines = CharStreams.readLines(reader);

		assertEquals(1, lines.size());

		assertEquals("sample bz2", lines.get(0));

	}

	@Test
	public void testOpenBzip2Reader_Path() throws IOException {

		reader = openBzip2Reader(PATH_BZIP, UTF_8);

		List<String> lines = CharStreams.readLines(reader);

		assertEquals(1, lines.size());

		assertEquals("sample bz2", lines.get(0));

	}

	@Test(expected = IOException.class)
	public void testOpenBzip2Reader_String_Exception() throws IOException {
		reader = openBzip2Reader(URL_GZIP.getPath(), null);
	}

	@Test(expected = IOException.class)
	public void testOpenBzip2Reader_URL_Exception() throws IOException {
		reader = openBzip2Reader(URL_GZIP, null);
	}

	@Test(expected = IOException.class)
	public void testOpenBzip2Reader_Path_Exception() throws IOException {
		reader = openBzip2Reader(PATH_GZIP, null);
	}

}
