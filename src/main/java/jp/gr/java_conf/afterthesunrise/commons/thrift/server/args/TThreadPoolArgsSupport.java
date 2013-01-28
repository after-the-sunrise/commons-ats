package jp.gr.java_conf.afterthesunrise.commons.thrift.server.args;

import org.apache.thrift.server.TThreadPoolServer.Args;

/**
 * @author takanori.takase
 */
public class TThreadPoolArgsSupport extends AbstractServerArgsSupport<Args> {

	public TThreadPoolArgsSupport(Args args) {
		super(args);
	}

	public void setMinWorkerThreads(int n) {
		args.minWorkerThreads(n);
	}

	public void setMaxWorkerThreads(int n) {
		args.maxWorkerThreads(n);
	}

}
