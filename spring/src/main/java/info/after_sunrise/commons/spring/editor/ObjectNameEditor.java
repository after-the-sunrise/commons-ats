package info.after_sunrise.commons.spring.editor;

import java.beans.PropertyEditorSupport;

import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

/**
 * @author takanori.takase
 */
public class ObjectNameEditor extends PropertyEditorSupport {

	@Override
	public void setAsText(String text) throws IllegalArgumentException {

		try {

			ObjectName objectName = new ObjectName(text);

			setValue(objectName);

		} catch (MalformedObjectNameException e) {

			throw new IllegalArgumentException(text, e);

		}

	}

}
