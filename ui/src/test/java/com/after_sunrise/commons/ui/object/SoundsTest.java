package com.after_sunrise.commons.ui.object;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.awt.Toolkit;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.concurrent.atomic.AtomicReference;

import javax.sound.sampled.Clip;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.after_sunrise.commons.base.object.Streams;

/**
 * @author takanori.takase
 */
public class SoundsTest {

	private byte[] sample;

	@Before
	public void setUp() throws Exception {

		URL url = getClass().getClassLoader().getResource("sample.wav");

		InputStream in = url.openStream();

		try {
			sample = Streams.getBytes(in);
		} finally {
			in.close();
		}

		Sounds.TOOLKIT = null;

		System.clearProperty(Sounds.ENABLE_TOOLKIT);

	}

	@After
	public void tearDown() {

		Sounds.TOOLKIT = null;

		System.clearProperty(Sounds.ENABLE_TOOLKIT);

	}

	@Test(expected = IllegalAccessError.class)
	public void testConstructor() throws Throwable {

		Class<?> clazz = Sounds.class;

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
	public void testGetToolkit() {

		System.setProperty(Sounds.ENABLE_TOOLKIT, "true");

		Toolkit t = Sounds.getToolkit();
		assertSame(Toolkit.getDefaultToolkit(), t);

		assertSame(t, Sounds.getToolkit());
		assertSame(t, Sounds.getToolkit());
		assertSame(t, Sounds.getToolkit());

	}

	@Test
	public void testGetToolkit_Disabled() {

		assertNull(Sounds.getToolkit());
		assertNull(Sounds.getToolkit());
		assertNull(Sounds.getToolkit());

	}

	@Test
	public void testBeep() {

		Toolkit mock = mock(Toolkit.class);

		Sounds.TOOLKIT = new AtomicReference<Toolkit>(mock);

		Sounds.beep();

		verify(mock).beep();

	}

	@Test
	public void testBeep_Null() {

		Sounds.TOOLKIT = new AtomicReference<Toolkit>(null);

		Sounds.beep();

		assertNull(Sounds.TOOLKIT.get());

	}

	@Test
	public void testGetClip() throws IOException {

		try {

			Clip clip = Sounds.getClip(sample);

			assertNotNull(clip);

			try {
				clip.close();
			} catch (RuntimeException e) {
				// Ignore close error
			}

		} catch (IOException e) {
			// Ignore since some platform may not like ".wav"
		}

		assertNull(Sounds.getClip(new byte[0]));

		assertNull(Sounds.getClip(null));

	}

	@Test(expected = IOException.class)
	public void testGetClip_Invalid() throws IOException {
		Sounds.getClip(new byte[] { 0 });
	}

	@Test
	public void testCloseQuietly() throws IOException {

		Clip clip = mock(Clip.class);

		Sounds.closeQuietly(clip);

		verify(clip).close();

		Sounds.closeQuietly(null);

	}

}
