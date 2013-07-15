package com.after_sunrise.commons.base.executor;

import java.io.Closeable;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;

/**
 * @author takanori.takase
 */
public interface SingleExecutor extends Closeable, Executor {

	<V> Future<V> execute(Callable<V> callable);

}
