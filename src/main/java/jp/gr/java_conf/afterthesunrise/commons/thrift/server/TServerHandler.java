package jp.gr.java_conf.afterthesunrise.commons.thrift.server;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.String.format;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

import jp.gr.java_conf.afterthesunrise.commons.bean.Initializable;

import org.apache.thrift.server.TServer;

/**
 * @author takanori.takase
 */
public class TServerHandler implements Initializable, Closeable {

	private static final String CLS_NAME = TServerHandler.class.getSimpleName();

	private static final AtomicLong COUNT = new AtomicLong();

	private final TServer server;

	private final boolean daemon;

	private Thread thread;

	public TServerHandler(TServer server) {
		this(server, false);
	}

	public TServerHandler(TServer server, boolean daemon) {
		this.server = checkNotNull(server);
		this.daemon = daemon;
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

		thread = null;

	}

}
