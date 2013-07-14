package info.after_sunrise.commons.base.executor.impl;

import static info.after_sunrise.commons.base.object.Validations.checkNotNull;
import info.after_sunrise.commons.base.bean.Initializable;

import java.io.Closeable;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author takanori.takase
 */
public class TimerAdapter extends TimerTask implements Initializable, Closeable {

	private static final String NAME = TimerAdapter.class.getSimpleName() + "-";

	private static final AtomicLong COUNT = new AtomicLong();

	private final Runnable runnable;

	private final Timer timer;

	private final long interval;

	public TimerAdapter(Runnable r, long interval) {
		this(r, interval, new Timer(NAME + COUNT.incrementAndGet(), true));
	}

	public TimerAdapter(Runnable r, long interval, Timer timer) {

		this.runnable = checkNotNull(r);

		this.interval = interval;

		this.timer = checkNotNull(timer);

	}

	@Override
	public void initialize() throws IOException {
		timer.schedule(this, interval, interval);
	}

	@Override
	public void close() throws IOException {
		timer.cancel();
	}

	@Override
	public void run() {
		runnable.run();
	}

}
