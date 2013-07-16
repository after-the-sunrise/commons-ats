package com.after_sunrise.commons.base.future;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;

import com.after_sunrise.commons.base.object.Selections;
import com.after_sunrise.commons.base.object.Validations;

/**
 * @author takanori.takase
 */
public class FutureHandler<K, V> implements Runnable {

	@SuppressWarnings("rawtypes")
	private static final FutureCallback NULL = new FutureCallbackAdapter();

	private static final long INTERVAL = 100L;

	private final Map<K, V> done = new IdentityHashMap<K, V>();

	private final Map<K, Throwable> fail = new IdentityHashMap<K, Throwable>();

	private final Map<K, Future<V>> cancel = new IdentityHashMap<K, Future<V>>();

	private final Map<K, Future<V>> values;

	private final FutureCallback<K, V> callback;

	private final long interval;

	private volatile boolean cancelled = false;

	public FutureHandler(Map<K, ? extends Future<V>> values) {
		this(values, null);
	}

	public FutureHandler(Map<K, ? extends Future<V>> values,
			FutureCallback<K, V> callback) {
		this(values, callback, INTERVAL);
	}

	@SuppressWarnings("unchecked")
	public FutureHandler(Map<K, ? extends Future<V>> values,
			FutureCallback<K, V> callback, long interval) {
		this.values = new IdentityHashMap<K, Future<V>>(values);
		this.callback = Selections.selectNotNull(callback, NULL);
		this.interval = Validations.checkNotNegative(interval);
	}

	@Override
	public void run() {

		callback.onStart();

		while (!values.isEmpty() && !cancelled) {

			handleGet(values);

		}

		handleCancel(values);

		callback.onEnd();

	}

	void handleGet(Map<K, Future<V>> map) {

		Iterator<Entry<K, Future<V>>> itr = map.entrySet().iterator();

		while (itr.hasNext() && !cancelled) {

			Entry<K, Future<V>> entry = itr.next();

			K key = entry.getKey();

			Future<V> future = entry.getValue();

			try {

				V value;

				if (future != null) {
					value = future.get(interval, MILLISECONDS);
				} else {
					value = null;
				}

				done.put(key, value);

				callback.onSuccess(key, value);

			} catch (TimeoutException e) {

				// Instead of checking Future#isDone(),
				// let it timeout to trigger internal transformation.
				continue;

			} catch (InterruptedException e) {

				cancel();

				break;

			} catch (Exception e) {

				fail.put(key, e);

				callback.onFailure(key, e);

			}

			itr.remove();

			int finish = done.size() + fail.size();

			int total = finish + map.size();

			callback.onProgress(total, finish);

		}

	}

	void handleCancel(Map<K, Future<V>> map) {

		Iterator<Entry<K, Future<V>>> itr = map.entrySet().iterator();

		while (itr.hasNext()) {

			Entry<K, Future<V>> entry = itr.next();

			cancel.put(entry.getKey(), entry.getValue());

			callback.onCancel(entry.getKey());

			itr.remove();

		}

	}

	public void cancel() {
		cancelled = true;
	}

	public boolean isCancelled() {
		return cancelled;
	}

	public Map<K, V> getDone() {
		return Collections.unmodifiableMap(done);
	}

	public Map<K, Throwable> getFail() {
		return Collections.unmodifiableMap(fail);
	}

	public Map<K, Future<V>> getCancel() {
		return Collections.unmodifiableMap(cancel);
	}

}
