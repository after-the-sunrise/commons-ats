package com.after_sunrise.commons.base.object;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import org.junit.Before;
import org.junit.Test;

import com.after_sunrise.commons.base.bean.Reference;

/**
 * @author takanori.takase
 */
public class ReferencesTest {

	private Long referent;

	private ReferenceQueue<Long> queue;

	@Before
	public void setUp() {

		referent = new Long(1234567890L);

		queue = new ReferenceQueue<Long>();

	}

	@Test(expected = IllegalAccessError.class)
	public void testConstructor() throws Throwable {

		Class<?> clazz = References.class;

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
	public void testReference() {

		Reference<?> ref = References.reference(referent);

		assertSame(referent, ref.get());

	}

	@Test
	public void testReferenceCache() {

		assertNotNull(References.referenceCache());

		assertNotNull(References.referenceCache(100));

	}

	@Test
	public void testSoftReference() {

		SoftReference<?> ref = References.softReference(referent);

		assertSame(referent, ref.get());

		assertFalse(ref.enqueue());

	}

	@Test
	public void testSoftReference_Queue() {

		SoftReference<?> ref = References.softReference(referent, queue);

		assertSame(referent, ref.get());

		assertTrue(ref.enqueue());

		assertSame(ref, queue.poll());

	}

	@Test
	public void testWeakReference() {

		WeakReference<?> ref = References.weakReference(referent);

		assertSame(referent, ref.get());

		assertFalse(ref.enqueue());

	}

	@Test
	public void testWeakReference_Queue() {

		WeakReference<?> ref = References.weakReference(referent, queue);

		assertSame(referent, ref.get());

		assertTrue(ref.enqueue());

		assertSame(ref, queue.poll());

	}

	@Test
	public void testPhantomReference() {

		PhantomReference<?> ref = References.phantomReference(referent, queue);

		assertNull(ref.get());

		assertTrue(ref.enqueue());

		assertSame(ref, queue.poll());

	}

}
