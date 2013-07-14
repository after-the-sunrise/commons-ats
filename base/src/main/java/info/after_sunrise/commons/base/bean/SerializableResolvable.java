package info.after_sunrise.commons.base.bean;

import java.io.ObjectStreamException;

/**
 * @author takanori.takase
 */
public interface SerializableResolvable {

	Object readResolve() throws ObjectStreamException;

}
