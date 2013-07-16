package com.after_sunrise.commons.base.future;

/**
 * @author takanori.takase
 */
public class FutureCallbackAdapter<K, V> implements FutureCallback<K, V> {

	@Override
	public void onStart() {
	}

	@Override
	public void onEnd() {
	}

	@Override
	public void onProgress(int total, int done) {
	}

	@Override
	public void onSuccess(K key, V value) {
	}

	@Override
	public void onFailure(K key, Exception e) {
	}

	@Override
	public void onCancel(K key) {
	}

}
