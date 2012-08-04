package jp.gr.java_conf.afterthesunrise.commons.future;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.concurrent.Executor;
import java.util.concurrent.Future;

import org.junit.Before;
import org.junit.Test;

import com.google.common.util.concurrent.ListenableFuture;

/**
 * @author takanori.takase
 */
public class DelegatingListenableFutureTest {

	private DelegatingListenableFuture<String, Integer> target;

	private ListenableFuture<String> future;

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {

		future = mock(ListenableFuture.class);

		target = new DelegatingListenableFuture<String, Integer>(future) {
			@Override
			protected Future<Integer> delegate(String source) {
				return null;
			}
		};

	}

	@Test
	public void testAddListener() {

		Runnable listener = mock(Runnable.class);

		Executor executor = mock(Executor.class);

		target.addListener(listener, executor);

		verify(future).addListener(listener, executor);

	}

}
