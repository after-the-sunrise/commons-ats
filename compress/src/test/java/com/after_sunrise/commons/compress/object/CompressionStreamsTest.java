package com.after_sunrise.commons.compress.object;

import static com.after_sunrise.commons.compress.object.CompressionStreams.openBzip2Reader;
import static com.after_sunrise.commons.compress.object.CompressionStreams.openBzip2Stream;
import static com.after_sunrise.commons.compress.object.CompressionStreams.openGzipReader;
import static com.after_sunrise.commons.compress.object.CompressionStreams.openGzipStream;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Test;

import com.after_sunrise.commons.base.object.IOs;

/**
 * @author takanori.takase
 */
public class CompressionStreamsTest {

	private static final Charset UTF_8 = Charset.forName("UTF-8");

	private static final ClassLoader CL = CompressionStreams.class
			.getClassLoader();

	private static final String STR_GZIP = "sample.txt.gz";

	private static final String STR_BZIP = "sample.txt.bz2";

	private static final URL URL_GZIP = CL.getResource(STR_GZIP);

	private static final URL URL_BZIP = CL.getResource(STR_BZIP);

	private static final File PATH_GZIP = new File("src/test/resources/"
			+ STR_GZIP);

	private static final File PATH_BZIP = new File("src/test/resources/"
			+ STR_BZIP);

	private InputStream in;

	private Reader reader;

	@After
	public void tearDown() {

		IOs.closeQuietly(reader);

		IOs.closeQuietly(in);

	}

	@Test(expected = IllegalAccessError.class)
	public void testConstructor() throws Throwable {

		Class<?> clazz = CompressionStreams.class;

		Constructor<?> c = clazz.getDeclaredConstructor();

		assertTrue(Modifier.isPrivate(c.getModifiers()));

		c.setAccessible(true);

		try {
			c.newInstance();
		} catch (InvocationTargetException e) {
			throw e.getCause();
		}

	}

	private List<String> readLines(Reader reader) throws IOException {

		BufferedReader br = new BufferedReader(reader);

		List<String> list = new ArrayList<String>();

		String line;

		while ((line = br.readLine()) != null) {
			list.add(line);
		}

		return list;

	}

	@Test
	public void testOpenGzipStream_String() throws IOException {

		in = openGzipStream(STR_GZIP);

		reader = new InputStreamReader(in);

		List<String> lines = readLines(reader);

		assertEquals(1, lines.size());

		assertEquals("sample gzip", lines.get(0));

	}

	@Test
	public void testOpenGzipStream_URL() throws IOException {

		in = openGzipStream(URL_GZIP);

		reader = new InputStreamReader(in);

		List<String> lines = readLines(reader);

		assertEquals(1, lines.size());

		assertEquals("sample gzip", lines.get(0));

	}

	@Test
	public void testOpenGzipStream_Path() throws IOException {

		in = openGzipStream(PATH_GZIP);

		reader = new InputStreamReader(in);

		List<String> lines = readLines(reader);

		assertEquals(1, lines.size());

		assertEquals("sample gzip", lines.get(0));

	}

	@Test(expected = IOException.class)
	public void testOpenGzipStream_String_Exception() throws IOException {
		in = openGzipStream(STR_BZIP);
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

		in = openBzip2Stream(STR_BZIP);

		reader = new InputStreamReader(in);

		List<String> lines = readLines(reader);

		assertEquals(1, lines.size());

		assertEquals("sample bz2", lines.get(0));

	}

	@Test
	public void testOpenBzip2Stream_URL() throws IOException {

		in = openBzip2Stream(URL_BZIP);

		reader = new InputStreamReader(in);

		List<String> lines = readLines(reader);

		assertEquals(1, lines.size());

		assertEquals("sample bz2", lines.get(0));

	}

	@Test
	public void testOpenBzip2Stream_Path() throws IOException {

		in = openBzip2Stream(PATH_BZIP);

		reader = new InputStreamReader(in);

		List<String> lines = readLines(reader);

		assertEquals(1, lines.size());

		assertEquals("sample bz2", lines.get(0));

	}

	@Test(expected = IOException.class)
	public void testOpenBzip2Stream_String_Exception() throws IOException {
		in = openBzip2Stream(STR_GZIP);
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

		reader = openGzipReader(STR_GZIP, UTF_8);

		List<String> lines = readLines(reader);

		assertEquals(1, lines.size());

		assertEquals("sample gzip", lines.get(0));

	}

	@Test
	public void testOpenGzipReader_URL() throws IOException {

		reader = openGzipReader(URL_GZIP, UTF_8);

		List<String> lines = readLines(reader);

		assertEquals(1, lines.size());

		assertEquals("sample gzip", lines.get(0));

	}

	@Test
	public void testOpenGzipReader_Path() throws IOException {

		reader = openGzipReader(PATH_GZIP, UTF_8);

		List<String> lines = readLines(reader);

		assertEquals(1, lines.size());

		assertEquals("sample gzip", lines.get(0));

	}

	@Test(expected = IOException.class)
	public void testOpenGzipReader_String_Exception() throws IOException {
		reader = openGzipReader(STR_GZIP, null);
	}

	@Test(expected = IOException.class)
	public void testOpenGzipReader_URL_Exception() throws IOException {
		reader = openGzipReader(URL_GZIP, null);
	}

	@Test(expected = IOException.class)
	public void testOpenGzipReader_Path_Exception() throws IOException {
		reader = openGzipReader(PATH_GZIP, null);
	}

	@Test
	public void testOpenBzip2Reader_String() throws IOException {

		reader = openBzip2Reader(STR_BZIP, UTF_8);

		List<String> lines = readLines(reader);

		assertEquals(1, lines.size());

		assertEquals("sample bz2", lines.get(0));

	}

	@Test
	public void testOpenBzip2Reader_URL() throws IOException {

		reader = openBzip2Reader(URL_BZIP, UTF_8);

		List<String> lines = readLines(reader);

		assertEquals(1, lines.size());

		assertEquals("sample bz2", lines.get(0));

	}

	@Test
	public void testOpenBzip2Reader_Path() throws IOException {

		reader = openBzip2Reader(PATH_BZIP, UTF_8);

		List<String> lines = readLines(reader);

		assertEquals(1, lines.size());

		assertEquals("sample bz2", lines.get(0));

	}

	@Test(expected = IOException.class)
	public void testOpenBzip2Reader_String_Exception() throws IOException {
		reader = openBzip2Reader(STR_BZIP, null);
	}

	@Test(expected = IOException.class)
	public void testOpenBzip2Reader_URL_Exception() throws IOException {
		reader = openBzip2Reader(URL_BZIP, null);
	}

	@Test(expected = IOException.class)
	public void testOpenBzip2Reader_Path_Exception() throws IOException {
		reader = openBzip2Reader(PATH_BZIP, null);
	}

}
