package info.after_sunrise.commons.thrift.server;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.String.format;
import info.after_sunrise.commons.bean.Initializable;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.thrift.server.TServer;

/**
 * @author takanori.takase
 */
public class TServerHandler implements Initializable, Closeable {

	private static final String CLS_NAME = TServerHandler.class.getSimpleName();

	private static final long TIMEOUT = 5000L;

	private static final AtomicLong COUNT = new AtomicLong();

	private final TServer server;

	private final boolean daemon;

	private final long timeout;

	private Thread thread;

	public TServerHandler(TServer server) {
		this(server, true, TIMEOUT);
	}

	public TServerHandler(TServer server, boolean daemon, long timeout) {
		this.server = checkNotNull(server);
		this.daemon = daemon;
		this.timeout = timeout;
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

		try {

			long timeLimit = System.currentTimeMillis() + timeout;

			while (true) {

				server.stop();

				thread.join(100L);

				if (!thread.isAlive()) {
					break;
				}

				if (System.currentTimeMillis() > timeLimit) {
					throw new IOException("timeout");
				}

			}

		} catch (InterruptedException e) {
			throw new IOException(e);
		}

		thread = null;

	}
}
