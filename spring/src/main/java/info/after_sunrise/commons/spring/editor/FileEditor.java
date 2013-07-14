package info.after_sunrise.commons.spring.editor;

import java.beans.PropertyEditorSupport;
import java.io.File;

/**
 * @author takanori.takase
 */
public class FileEditor extends PropertyEditorSupport {

	@Override
	public void setAsText(String text) throws IllegalArgumentException {

		File file = new File(text);

		setValue(file);

	}

}
