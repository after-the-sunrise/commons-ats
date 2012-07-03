package jp.gr.java_conf.afterthesunrise.commons.listener;

/**
 * @author takanori.takase
 */
public interface ListenerPredicate<L> {

	void process(L listener);

}
