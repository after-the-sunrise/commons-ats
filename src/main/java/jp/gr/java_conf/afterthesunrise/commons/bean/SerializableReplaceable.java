package jp.gr.java_conf.afterthesunrise.commons.bean;

import java.io.ObjectStreamException;

/**
 * @author takanori.takase
 */
public interface SerializableReplaceable {

	Object writeReplace() throws ObjectStreamException;

}
