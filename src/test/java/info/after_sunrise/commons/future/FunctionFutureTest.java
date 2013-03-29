package info.after_sunrise.commons.future;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.Future;

import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Function;
import com.google.common.util.concurrent.Futures;

/**
 * @author takanori.takase
 */
public class FunctionFutureTest {

	private FunctionFuture<String, Integer> target;

	private Future<String> future;

	private Function<String, Future<Integer>> function;

	@Before
	public void setUp() throws Exception {

		future = Futures.immediateFuture("8");

		function = new Function<String, Future<Integer>>() {
			@Override
			public Future<Integer> apply(String input) {
				return Futures.immediateFuture(Integer.valueOf(input));
			}
		};

		target = FunctionFuture.create(future, function);

	}

	@Test(timeout = 5000L)
	public void test() throws Exception {
		assertEquals((Integer) 8, target.get());
	}

}
