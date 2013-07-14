package info.after_sunrise.commons.log.object;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import info.after_sunrise.commons.log.objects.Logs;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.logging.Log;
import org.junit.Before;
import org.junit.Test;

/**
 * @author takanori.takase
 */
public class LogsTest {

	private Log log;

	private Exception e;

	@Before
	public void setUp() throws Exception {

		log = mock(Log.class);

		e = new IllegalArgumentException("test");

	}

	@Test(expected = IllegalAccessError.class)
	public void testConstructor() throws Throwable {

		Class<?> clazz = Logs.class;

		Constructor<?> c = clazz.getDeclaredConstructor();

		c.setAccessible(true);

		try {
			c.newInstance();
		} catch (InvocationTargetException e) {
			throw e.getCause();
		}

	}

	@Test
	public void testLogTrace() {

		assertNull(Logs.logTrace(log, "Foo %s", "Bar"));

		when(log.isTraceEnabled()).thenReturn(true);

		assertEquals("Foo Bar", Logs.logTrace(log, "Foo %s", "Bar"));

		assertNull(Logs.logTrace(null, "Foo %s", "Bar"));

		assertNull(Logs.logTrace(log, null, "Bar"));

	}

	@Test
	public void testLogTraces() {

		assertNull(Logs.logTraces(log, "Foo %s", e, "Bar"));

		when(log.isTraceEnabled()).thenReturn(true);

		assertEquals("Foo Bar", Logs.logTraces(log, "Foo %s", e, "Bar"));

		assertNull(Logs.logTraces(null, "Foo %s", e, "Bar"));

		assertNull(Logs.logTraces(log, null, e, "Bar"));

	}

	@Test
	public void testLogDebug() {

		assertNull(Logs.logDebug(log, "Foo %s", "Bar"));

		when(log.isDebugEnabled()).thenReturn(true);

		assertEquals("Foo Bar", Logs.logDebug(log, "Foo %s", "Bar"));

		assertNull(Logs.logDebug(null, "Foo %s", "Bar"));

		assertNull(Logs.logDebug(log, null, "Bar"));

	}

	@Test
	public void testLogDebugs() {

		assertNull(Logs.logDebugs(log, "Foo %s", e, "Bar"));

		when(log.isDebugEnabled()).thenReturn(true);

		assertEquals("Foo Bar", Logs.logDebugs(log, "Foo %s", e, "Bar"));

		assertNull(Logs.logDebugs(null, "Foo %s", e, "Bar"));

		assertNull(Logs.logDebugs(log, null, e, "Bar"));

	}

	@Test
	public void testLogInfo() {

		assertNull(Logs.logInfo(log, "Foo %s", "Bar"));

		when(log.isInfoEnabled()).thenReturn(true);

		assertEquals("Foo Bar", Logs.logInfo(log, "Foo %s", "Bar"));

		assertNull(Logs.logInfo(null, "Foo %s", "Bar"));

		assertNull(Logs.logInfo(log, null, "Bar"));

	}

	@Test
	public void testLogInfos() {

		assertNull(Logs.logInfos(log, "Foo %s", e, "Bar"));

		when(log.isInfoEnabled()).thenReturn(true);

		assertEquals("Foo Bar", Logs.logInfos(log, "Foo %s", e, "Bar"));

		assertNull(Logs.logInfos(null, "Foo %s", e, "Bar"));

		assertNull(Logs.logInfos(log, null, e, "Bar"));

	}

	@Test
	public void testLogWarn() {

		assertNull(Logs.logWarn(log, "Foo %s", "Bar"));

		when(log.isWarnEnabled()).thenReturn(true);

		assertEquals("Foo Bar", Logs.logWarn(log, "Foo %s", "Bar"));

		assertNull(Logs.logWarn(null, "Foo %s", "Bar"));

		assertNull(Logs.logWarn(log, null, "Bar"));

	}

	@Test
	public void testLogWarns() {

		assertNull(Logs.logWarns(log, "Foo %s", e, "Bar"));

		when(log.isWarnEnabled()).thenReturn(true);

		assertEquals("Foo Bar", Logs.logWarns(log, "Foo %s", e, "Bar"));

		assertNull(Logs.logWarns(null, "Foo %s", e, "Bar"));

		assertNull(Logs.logWarns(log, null, e, "Bar"));

	}

	@Test
	public void testLogError() {

		assertNull(Logs.logError(log, "Foo %s", "Bar"));

		when(log.isErrorEnabled()).thenReturn(true);

		assertEquals("Foo Bar", Logs.logError(log, "Foo %s", "Bar"));

		assertNull(Logs.logError(null, "Foo %s", "Bar"));

		assertNull(Logs.logError(log, null, "Bar"));

	}

	@Test
	public void testLogErrors() {

		assertNull(Logs.logErrors(log, "Foo %s", e, "Bar"));

		when(log.isErrorEnabled()).thenReturn(true);

		assertEquals("Foo Bar", Logs.logErrors(log, "Foo %s", e, "Bar"));

		assertNull(Logs.logErrors(null, "Foo %s", e, "Bar"));

		assertNull(Logs.logErrors(log, null, e, "Bar"));

	}

	@Test
	public void testLogFatal() {

		assertNull(Logs.logFatal(log, "Foo %s", "Bar"));

		when(log.isFatalEnabled()).thenReturn(true);

		assertEquals("Foo Bar", Logs.logFatal(log, "Foo %s", "Bar"));

		assertNull(Logs.logFatal(null, "Foo %s", "Bar"));

		assertNull(Logs.logFatal(log, null, "Bar"));

	}

	@Test
	public void testLogFatals() {

		assertNull(Logs.logFatals(log, "Foo %s", e, "Bar"));

		when(log.isFatalEnabled()).thenReturn(true);

		assertEquals("Foo Bar", Logs.logFatals(log, "Foo %s", e, "Bar"));

		assertNull(Logs.logFatals(null, "Foo %s", e, "Bar"));

		assertNull(Logs.logFatals(log, null, e, "Bar"));

	}

}
