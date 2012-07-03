package jp.gr.java_conf.afterthesunrise.commons.listener;

/**
 * @author takanori.takase
 */
public interface ProxyListenerManager<L> extends ListenerManager<L> {

	L proxy(Class<L> clazz);

}
