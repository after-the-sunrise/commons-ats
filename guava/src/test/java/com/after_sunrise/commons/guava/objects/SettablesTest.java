package com.after_sunrise.commons.guava.objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import org.junit.Test;

import com.after_sunrise.commons.guava.object.Settables;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.SettableFuture;

/**
 * @author takanori.takase
 */
public class SettablesTest {

	@Test(expected = IllegalAccessError.class)
	public void testConstructor() throws Throwable {

		Class<?> clazz = Settables.class;

		Constructor<?> c = clazz.getDeclaredConstructor();

		assertTrue(Modifier.isPrivate(c.getModifiers()));

		c.setAccessible(true);

		try {
			c.newInstance();
		} catch (InvocationTargetException e) {
			throw e.getCause();
		}

	}

	@Test(timeout = 1000L)
	public void testEmpty() throws Exception {

		ListenableFuture<String> f = Settables.empty();

		assertSame(Settables.empty(), f);
		assertSame(Settables.empty(), f);
		assertSame(Settables.empty(), f);

		assertNull(f.get());

	}

	@Test
	public void testSet() throws Exception {

		SettableFuture<Long> future = SettableFuture.create();

		Reference<SettableFuture<Long>> ref = new WeakReference<SettableFuture<Long>>(
				future);

		assertTrue(Settables.set(ref, 1L));

		assertEquals((Long) 1L, future.get());

	}

	@Test
	public void testSet_Done() throws Exception {

		SettableFuture<Long> future = SettableFuture.create();

		future.set(1L);

		Reference<SettableFuture<Long>> ref = new WeakReference<SettableFuture<Long>>(
				future);

		assertFalse(Settables.set(ref, 1L));

	}

	@Test
	public void testSet_Collected() throws Exception {

		SettableFuture<Long> future = SettableFuture.create();

		Reference<SettableFuture<Long>> ref = new PhantomReference<SettableFuture<Long>>(
				future, new ReferenceQueue<SettableFuture<Long>>());

		assertFalse(Settables.set(ref, 1L));

		assertFalse(future.isDone());

	}

	@Test
	public void testSetException() throws Exception {

		SettableFuture<Long> future = SettableFuture.create();

		Reference<SettableFuture<Long>> ref = new WeakReference<SettableFuture<Long>>(
				future);

		assertTrue(Settables.setException(ref, new RuntimeException("test")));

		assertTrue(future.isDone());

	}

	@Test
	public void testSetException_Done() throws Exception {

		SettableFuture<Long> future = SettableFuture.create();

		future.set(1L);

		Reference<SettableFuture<Long>> ref = new WeakReference<SettableFuture<Long>>(
				future);

		assertFalse(Settables.setException(ref, new RuntimeException("test")));

	}

	@Test
	public void testSetException_Collected() throws Exception {

		SettableFuture<Long> future = SettableFuture.create();

		Reference<SettableFuture<Long>> ref = new PhantomReference<SettableFuture<Long>>(
				future, new ReferenceQueue<SettableFuture<Long>>());

		assertFalse(Settables.setException(ref, new RuntimeException("test")));

		assertFalse(future.isDone());

	}

	@Test
	public void testAddCallback() {

		SettableFuture<Integer> source = SettableFuture.create();
		SettableFuture<Number> target = SettableFuture.create();

		Settables.addCallback(source, target);

		assertFalse(target.isDone());

		source.set(1);

		assertTrue(target.isDone());

	}

	@Test
	public void testAddCallback_Executor() {

		SettableFuture<Integer> source = SettableFuture.create();
		SettableFuture<Number> target = SettableFuture.create();

		Settables.addCallback(source, target, MoreExecutors.directExecutor());

		assertFalse(target.isDone());

		source.set(1);

		assertTrue(target.isDone());

	}

}
