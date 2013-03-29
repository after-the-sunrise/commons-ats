package info.after_sunrise.commons.thrift.server.args;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.apache.thrift.server.TThreadPoolServer;
import org.junit.Before;
import org.junit.Test;

/**
 * @author takanori.takase
 */
public class TThreadPoolArgsSupportTest {

	private TThreadPoolArgsSupport target;

	private TThreadPoolServer.Args args;

	@Before
	public void setUp() throws Exception {

		args = mock(TThreadPoolServer.Args.class);

		target = new TThreadPoolArgsSupport(args);

	}

	@Test(expected = NullPointerException.class)
	public void testTThreadPoolArgsSupport() {
		target = new TThreadPoolArgsSupport(null);
	}

	@Test
	public void testSetMinWorkerThreads() {

		target.setMinWorkerThreads(1);

		verify(args).minWorkerThreads(1);

	}

	@Test
	public void testSetMaxWorkerThreads() {

		target.setMaxWorkerThreads(1);

		verify(args).maxWorkerThreads(1);

	}

}
