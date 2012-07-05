package jp.gr.java_conf.afterthesunrise.commons.editor;

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
