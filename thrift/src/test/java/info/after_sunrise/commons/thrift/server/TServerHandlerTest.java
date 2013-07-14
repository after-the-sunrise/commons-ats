package info.after_sunrise.commons.thrift.server;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import info.after_sunrise.commons.base.object.IOs;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.thrift.server.TServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 * @author takanori.takase
 */
public class TServerHandlerTest {

	private TServerHandler target;

	private TServer server;

	private CountDownLatch latch;

	@Before
	public void setUp() throws Exception {

		server = mock(TServer.class);

		target = new TServerHandler(server);

		latch = new CountDownLatch(1);

		doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				latch.await();
				return null;
			}
		}).when(server).serve();

		doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				latch.countDown();
				return null;
			}
		}).when(server).stop();

	}

	@After
	public void tearDown() throws Exception {

		while (latch != null && latch.getCount() > 0) {
			latch.countDown();
		}

		IOs.closeQuietly(target);

	}

	@Test(timeout = 5000L)
	public void testSequence() throws Exception {

		target.initialize();

		try {

			// Second invocation should fail
			target.initialize();

			fail();

		} catch (IOException e) {
			// Success
		}

		target.close();

		// Check server shutdown
		verify(server).stop();

		// Second invocation should be ignored
		target.close();

	}

	@Test(timeout = 5000L, expected = IOException.class)
	public void testInterrupted() throws Exception {

		// Interrupted thread
		Thread.currentThread().interrupt();

		// Keep server in hang state
		doNothing().when(server).stop();

		target.initialize();

		target.close();

	}

	@Test(timeout = 5000L, expected = IOException.class)
	public void testTimeout() throws Exception {

		// Set short timeout for testing
		target = new TServerHandler(server, true, 0L, 1L);

		// Keep server in hang state
		doNothing().when(server).stop();

		target.initialize();

		target.close();

	}

}
