package info.after_sunrise.commons.thrift.server;

import static info.after_sunrise.commons.base.object.Validations.checkNotNegative;
import static info.after_sunrise.commons.base.object.Validations.checkNotNull;
import static info.after_sunrise.commons.base.object.Validations.checkPositive;
import static java.lang.String.format;
import info.after_sunrise.commons.base.bean.Initializable;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.thrift.server.TServer;

/**
 * @author takanori.takase
 */
public class TServerHandler implements Initializable, Closeable {

	private static final String CLS_NAME = TServerHandler.class.getSimpleName();

	private static final boolean DAEMON = true;

	private static final long TIMEOUT = 5000L;

	private static final long INTERVAL = 100L;

	private static final AtomicLong COUNT = new AtomicLong();

	private final TServer server;

	private final boolean daemon;

	private final long timeout;

	private final long interval;

	private Thread thread;

	public TServerHandler(TServer server) {
		this(server, DAEMON);
	}

	public TServerHandler(TServer server, boolean daemon) {
		this(server, daemon, TIMEOUT);
	}

	public TServerHandler(TServer server, boolean daemon, long timeout) {
		this(server, daemon, timeout, INTERVAL);
	}

	public TServerHandler(TServer server, boolean daemon, long timeout,
			long interval) {
		this.server = checkNotNull(server);
		this.daemon = daemon;
		this.timeout = checkNotNegative(timeout);
		this.interval = checkPositive(interval);
	}

	@Override
	public synchronized void initialize() throws IOException {

		if (thread != null) {
			throw new IOException("Already initialized.");
		}

		thread = new Thread(new Runnable() {
			@Override
			public void run() {
				server.serve();
			}
		});

		thread.setDaemon(daemon);

		thread.setName(format("%s-%02d", CLS_NAME, COUNT.incrementAndGet()));

		thread.start();

	}

	@Override
	public synchronized void close() throws IOException {

		if (thread == null) {
			return;
		}

		server.stop();

		long timeLimit = System.currentTimeMillis() + timeout;

		while (thread.isAlive()) {

			if (System.currentTimeMillis() > timeLimit) {

				String msg = format("Timeout : %,3dms", timeout);

				throw new IOException(msg);

			}

			try {
				thread.join(interval);
			} catch (InterruptedException e) {
				throw new IOException(e);
			}

		}

		thread = null;

	}

}
