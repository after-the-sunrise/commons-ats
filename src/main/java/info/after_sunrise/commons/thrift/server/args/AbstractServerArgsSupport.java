package info.after_sunrise.commons.thrift.server.args;

import static com.google.common.base.Preconditions.checkNotNull;

import org.apache.thrift.TProcessor;
import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.server.TServer.AbstractServerArgs;
import org.apache.thrift.transport.TTransportFactory;

/**
 * @author takanori.takase
 */
public class AbstractServerArgsSupport<A extends AbstractServerArgs<?>> {

	protected final A args;

	public AbstractServerArgsSupport(A args) {
		this.args = checkNotNull(args);
	}

	public void setProcessorFactory(TProcessorFactory factory) {
		args.processorFactory(factory);
	}

	public void setProcessor(TProcessor processor) {
		args.processor(processor);
	}

	public void setTransportFactory(TTransportFactory factory) {
		args.transportFactory(factory);
	}

	public void setInputTransportFactory(TTransportFactory factory) {
		args.inputTransportFactory(factory);
	}

	public void setOutputTransportFactory(TTransportFactory factory) {
		args.outputTransportFactory(factory);
	}

	public void setProtocolFactory(TProtocolFactory factory) {
		args.protocolFactory(factory);
	}

	public void setInputProtocolFactory(TProtocolFactory factory) {
		args.inputProtocolFactory(factory);
	}

	public void setOutputProtocolFactory(TProtocolFactory factory) {
		args.outputProtocolFactory(factory);
	}

}
