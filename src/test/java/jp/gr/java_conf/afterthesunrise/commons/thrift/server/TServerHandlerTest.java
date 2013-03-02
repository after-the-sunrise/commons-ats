package jp.gr.java_conf.afterthesunrise.commons.thrift.server;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.thrift.server.TServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.google.common.io.Closeables;

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

		latch = new CountDownLatch(3);

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
	public void tearDown() {

		while (latch != null && latch.getCount() > 0) {
			latch.countDown();
		}

		Closeables.closeQuietly(target);

	}

	@Test(timeout = 10000L)
	public void testSequence() throws Exception {

		target.initialize();

		try {

			target.initialize();

			fail();

		} catch (IOException e) {
			// Success
		}

		target.close();

		target.close();

		latch.await();

	}

	@Test(timeout = 10000L, expected = IOException.class)
	public void testInterrupted() throws Exception {

		target.initialize();

		Thread.currentThread().interrupt();

		target.close();

	}

	@Test(timeout = 10000L, expected = IOException.class)
	public void testTimeout() throws Exception {

		target.close();

		target = new TServerHandler(server, true, 100L);

		latch = new CountDownLatch(100);

		target.initialize();

		target.close();

	}

}
