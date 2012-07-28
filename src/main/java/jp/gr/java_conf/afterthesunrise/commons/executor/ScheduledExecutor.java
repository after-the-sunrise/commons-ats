package jp.gr.java_conf.afterthesunrise.commons.executor;

/**
 * @author takanori.takase
 */
public interface ScheduledExecutor extends AutoCloseable {

	void scheduleOnce(Runnable runnable, long delay);

	void scheduleFixed(Runnable runnable, long delay);

	void scheduleInterval(Runnable runnable, long delay);

}
