package info.after_sunrise.commons.future;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.google.common.base.Function;

/**
 * @author takanori.takase
 */
public class FunctionFuture<S, T> extends DelegatingFuture<S, T> {

	private final Function<S, Future<T>> function;

	public FunctionFuture(Future<S> delegate, Function<S, Future<T>> function) {

		super(delegate);

		this.function = checkNotNull(function);

	}

	@Override
	protected Future<T> delegate(S source) throws ExecutionException {
		return function.apply(source);
	}

	public static <S, T> FunctionFuture<S, T> create(Future<S> delegate,
			Function<S, Future<T>> function) {
		return new FunctionFuture<S, T>(delegate, function);
	}

}
