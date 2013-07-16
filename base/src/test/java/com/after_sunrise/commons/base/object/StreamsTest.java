package com.after_sunrise.commons.base.object;

import static com.after_sunrise.commons.base.object.Streams.getBytes;
import static com.after_sunrise.commons.base.object.Streams.openBufferedStream;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.net.URL;

import org.junit.After;
import org.junit.Test;

/**
 * @author takanori.takase
 */
public class StreamsTest {

	private static final ClassLoader CL = Streams.class.getClassLoader();

	private static final String STR = "test.properties";

	private static final URL URL = CL.getResource(STR);

	private static final File PATH = new File("src/test/resources/" + STR);

	private InputStream in;

	private Reader reader;

	@After
	public void tearDown() {

		IOs.closeQuietly(reader);

		IOs.closeQuietly(in);

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
	public void testwrapUncloseable_InputStream() throws IOException {

		InputStream underlying = mock(InputStream.class);

		in = Streams.wrapUncloseable(underlying);

		in.close();

		verify(underlying, never()).close();

	}

	@Test
	public void testwrapUncloseable_OutputStream() throws IOException {

		OutputStream underlying = mock(OutputStream.class);

		OutputStream out = Streams.wrapUncloseable(underlying);

		out.close();

		verify(underlying, never()).close();

	}

	@Test
	public void testOpenBufferedStream_String_Url() throws IOException {

		in = openBufferedStream(STR);

		assertTrue(in instanceof BufferedInputStream);

	}

	@Test
	public void testOpenBufferedStream_String_Path() throws IOException {

		in = openBufferedStream(PATH.getAbsolutePath());

		assertTrue(in instanceof BufferedInputStream);

	}

	@Test
	public void testOpenBufferedStream_URL() throws IOException {

		in = openBufferedStream(URL);

		assertTrue(in instanceof BufferedInputStream);

	}

	@Test
	public void testOpenBufferedStream_Path() throws IOException {

		in = openBufferedStream(PATH);

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
	public void testGetBytes_String() throws IOException {
		assertTrue(getBytes(STR).length > 0);
	}

	@Test
	public void testGetBytes_URL() throws IOException {
		assertTrue(getBytes(URL).length > 0);
	}

	@Test
	public void testGetBytes_Path() throws IOException {
		assertTrue(getBytes(PATH).length > 0);
	}

}
