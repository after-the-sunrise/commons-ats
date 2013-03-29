package info.after_sunrise.commons.listener;

/**
 * @author takanori.takase
 */
public interface ProxyListenerManager<L> extends ListenerManager<L> {

	L proxy(Class<L> clazz);

}
