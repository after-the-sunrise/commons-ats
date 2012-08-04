package jp.gr.java_conf.afterthesunrise.commons.executor;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;

/**
 * @author takanori.takase
 */
public interface SingleExecutor extends AutoCloseable, Executor {

	<V> Future<V> execute(Callable<V> callable);

}
