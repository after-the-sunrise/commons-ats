package com.after_sunrise.commons.base.thread;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * @author takanori.takase
 */
public class DeadlockHandler implements Runnable {

	private final ThreadMXBean bean;

	public DeadlockHandler() {
		this(ManagementFactory.getThreadMXBean());
	}

	public DeadlockHandler(ThreadMXBean bean) {
		this.bean = bean;
	}

	@Override
	public void run() {

		long[] ids = bean.findDeadlockedThreads();

		if (ids == null) {
			return;
		}

		onDeadlock(bean.getThreadInfo(ids));

	}

	protected void onDeadlock(ThreadInfo[] threads) {

		StringBuilder sb = new StringBuilder();

		for (ThreadInfo t : threads) {

			sb.append("Deadlock at \"");
			sb.append(t.getThreadName());
			sb.append("\" : ");
			sb.append(t.getLockName());
			sb.append('\n');

			for (StackTraceElement element : t.getStackTrace()) {
				sb.append('\t');
				sb.append(element);
				sb.append('\n');
			}

		}

		onDeadlock(sb.toString());

	}

	protected void onDeadlock(String dump) {
		System.err.println(dump);
	}

}
