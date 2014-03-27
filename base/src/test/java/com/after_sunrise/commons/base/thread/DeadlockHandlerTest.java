package com.after_sunrise.commons.base.thread;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * @author takanori.takase
 */
public class DeadlockHandlerTest {

	private DeadlockHandler target;

	private ThreadMXBean bean;

	@Before
	public void setUp() throws Exception {

		bean = mock(ThreadMXBean.class);

		target = spy(new DeadlockHandler(bean));

	}

	@Test
	public void testDeadlockHandler() {

		target = new DeadlockHandler();

		target.run();

	}

	@Test
	public void testRun() {

		long[] ids = { 1, 2, 3 };
		ThreadInfo[] info = { mock(ThreadInfo.class) };

		when(bean.findDeadlockedThreads()).thenReturn(ids);
		when(bean.getThreadInfo(ids)).thenReturn(info);

		doNothing().when(target).onDeadlock(Mockito.<ThreadInfo[]> anyObject());

		target.run();

		verify(target).onDeadlock(info);

	}

	@Test
	public void testRun_NoIds() {

		when(bean.findDeadlockedThreads()).thenReturn(null);

		target.run();

		verify(target, never()).onDeadlock(Mockito.<ThreadInfo[]> anyObject());

	}

	@Test
	public void testOnDeadlock_Array() throws IOException {

		ThreadInfo[] infos = new ThreadInfo[2];

		for (int i = 0; i < infos.length; i++) {

			StackTraceElement[] elements = new StackTraceElement[2];
			elements[0] = new StackTraceElement("c1", "do1", "f1", 1);
			elements[1] = new StackTraceElement("c2", "do2", "f2", 2);

			ThreadInfo info = mock(ThreadInfo.class);
			when(info.getThreadName()).thenReturn("Test" + i);
			when(info.getLockName()).thenReturn("Lock" + i);
			when(info.getStackTrace()).thenReturn(elements);

			infos[i] = info;

		}

		doNothing().when(target).onDeadlock(anyString());

		target.onDeadlock(infos);

		StringBuilder sb = new StringBuilder();
		sb.append("Deadlock at \"Test0\" : Lock0\n");
		sb.append("\tc1.do1(f1:1)\n");
		sb.append("\tc2.do2(f2:2)\n");
		sb.append("Deadlock at \"Test1\" : Lock1\n");
		sb.append("\tc1.do1(f1:1)\n");
		sb.append("\tc2.do2(f2:2)\n");
		verify(target).onDeadlock(sb.toString());

	}

	@Test
	public void testOnDeadlock_String() {
		target.onDeadlock("test");
	}

}
