package info.after_sunrise.commons.bean;

import java.io.ObjectStreamException;

/**
 * @author takanori.takase
 */
public interface SerializableResolvable {

	Object readResolve() throws ObjectStreamException;

}
