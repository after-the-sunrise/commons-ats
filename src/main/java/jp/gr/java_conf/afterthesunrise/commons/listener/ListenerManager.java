package jp.gr.java_conf.afterthesunrise.commons.listener;

/**
 * @author takanori.takase
 */
public interface ListenerManager<L> {

	void addListener(L listener);

	void removeListener(L listener);

}
