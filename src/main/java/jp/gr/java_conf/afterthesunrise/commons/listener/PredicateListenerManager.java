package jp.gr.java_conf.afterthesunrise.commons.listener;

/**
 * @author takanori.takase
 */
public interface PredicateListenerManager<L> extends ListenerManager<L> {

	void process(ListenerPredicate<L> predicate);

}
