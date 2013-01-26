package jp.gr.java_conf.afterthesunrise.commons.executor;

import java.io.Closeable;

/**
 * @author takanori.takase
 */
public interface ScheduledExecutor extends Closeable {

	void scheduleOnce(Runnable runnable, long delay);

	void scheduleFixed(Runnable runnable, long delay);

	void scheduleInterval(Runnable runnable, long delay);

}
