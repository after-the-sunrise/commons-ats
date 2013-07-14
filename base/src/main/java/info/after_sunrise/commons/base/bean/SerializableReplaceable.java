package info.after_sunrise.commons.base.bean;

import java.io.ObjectStreamException;

/**
 * @author takanori.takase
 */
public interface SerializableReplaceable {

	Object writeReplace() throws ObjectStreamException;

}
