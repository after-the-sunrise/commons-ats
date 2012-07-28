package jp.gr.java_conf.afterthesunrise.commons.executor;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * @author takanori.takase
 */
public interface FixedExecutor extends AutoCloseable {

	void execute(Runnable runnable);

	<V> Future<V> execute(Callable<V> callable);

}
