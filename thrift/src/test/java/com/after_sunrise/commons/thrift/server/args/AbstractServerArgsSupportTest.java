package com.after_sunrise.commons.thrift.server.args;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.apache.thrift.server.TServer.AbstractServerArgs;
import org.junit.Before;
import org.junit.Test;

/**
 * @author takanori.takase
 */
public class AbstractServerArgsSupportTest {

	private AbstractServerArgsSupport<?> target;

	private AbstractServerArgs<?> args;

	@Before
	public void setUp() throws Exception {

		args = mock(AbstractServerArgs.class);

		target = new AbstractServerArgsSupport<AbstractServerArgs<?>>(args);

	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructor() {
		target = new AbstractServerArgsSupport<AbstractServerArgs<?>>(null);
	}

	@Test
	public void testSetProcessorFactory() {

		target.setProcessorFactory(null);

		verify(args).processorFactory(null);

	}

	@Test
	public void testSetProcessor() {

		target.setProcessor(null);

		verify(args).processor(null);

	}

	@Test
	public void testSetTransportFactory() {

		target.setTransportFactory(null);

		verify(args).transportFactory(null);

	}

	@Test
	public void testSetInputTransportFactory() {

		target.setInputTransportFactory(null);

		verify(args).inputTransportFactory(null);

	}

	@Test
	public void testSetOutputTransportFactory() {

		target.setOutputTransportFactory(null);

		verify(args).outputTransportFactory(null);

	}

	@Test
	public void testSetProtocolFactory() {

		target.setProtocolFactory(null);

		verify(args).protocolFactory(null);

	}

	@Test
	public void testSetInputProtocolFactory() {

		target.setInputProtocolFactory(null);

		verify(args).inputProtocolFactory(null);

	}

	@Test
	public void testSetOutputProtocolFactory() {

		target.setOutputProtocolFactory(null);

		verify(args).outputProtocolFactory(null);

	}

}
