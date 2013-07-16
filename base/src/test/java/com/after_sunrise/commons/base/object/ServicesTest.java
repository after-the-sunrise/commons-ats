package com.after_sunrise.commons.base.object;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.Closeable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.List;

import org.junit.Test;

import com.after_sunrise.commons.base.object.spi.DuplicateService;
import com.after_sunrise.commons.base.object.spi.TestService;

/**
 * @author takanori.takase
 */
public class ServicesTest {

	@Test(expected = IllegalAccessError.class)
	public void testConstructor() throws Throwable {

		Class<?> clazz = Services.class;

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
	public void testGetServices() {

		List<TestService> tests = Services.getServices(TestService.class);

		assertEquals(1, tests.size());

		assertEquals("Hello World!", tests.get(0).sayHello());

		assertEquals(2, Services.getServices(DuplicateService.class).size());

		assertEquals(0, Services.getServices(Closeable.class).size());

	}

	@Test
	public void testGetService() {

		TestService service = Services.getService(TestService.class);

		assertEquals("Hello World!", service.sayHello());

	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetService_Duplicate() {

		Services.getService(DuplicateService.class);

	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetService_Unknown() {

		Services.getService(Closeable.class);

	}

}
