package com.after_sunrise.commons.base.future;

/**
 * @author takanori.takase
 */
public interface FutureCallback<K, V> {

	void onStart();

	void onEnd();

	void onProgress(int total, int done);

	void onSuccess(K key, V value);

	void onFailure(K key, Exception e);

	void onCancel(K key);

}
