package info.after_sunrise.commons.object;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import org.junit.Before;
import org.junit.Test;

import com.google.common.util.concurrent.SettableFuture;

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

	@Test
	public void testSet() throws Exception {

		SettableFuture<Long> future = SettableFuture.create();

		Reference<SettableFuture<Long>> ref = References.weakReference(future);

		assertTrue(References.set(ref, 1L));

		assertEquals((Long) 1L, future.get());

	}

	@Test
	public void testSet_Done() throws Exception {

		SettableFuture<Long> future = SettableFuture.create();

		future.set(1L);

		Reference<SettableFuture<Long>> ref = References.weakReference(future);

		assertFalse(References.set(ref, 1L));

	}

	@Test
	public void testSet_Collected() throws Exception {

		SettableFuture<Long> future = SettableFuture.create();

		Reference<SettableFuture<Long>> ref = References.phantomReference(
				future, new ReferenceQueue<SettableFuture<Long>>());

		assertFalse(References.set(ref, 1L));

		assertFalse(future.isDone());

	}

	@Test
	public void testSetException() throws Exception {

		SettableFuture<Long> future = SettableFuture.create();

		Reference<SettableFuture<Long>> ref = References.weakReference(future);

		assertTrue(References.setException(ref, new RuntimeException("test")));

		assertTrue(future.isDone());

	}

	@Test
	public void testSetException_Done() throws Exception {

		SettableFuture<Long> future = SettableFuture.create();

		future.set(1L);

		Reference<SettableFuture<Long>> ref = References.weakReference(future);

		assertFalse(References.setException(ref, new RuntimeException("test")));

	}

	@Test
	public void testSetException_Collected() throws Exception {

		SettableFuture<Long> future = SettableFuture.create();

		Reference<SettableFuture<Long>> ref = References.phantomReference(
				future, new ReferenceQueue<SettableFuture<Long>>());

		assertFalse(References.setException(ref, new RuntimeException("test")));

		assertFalse(future.isDone());

	}

}
