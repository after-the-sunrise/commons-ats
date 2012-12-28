package jp.gr.java_conf.afterthesunrise.commons.editor;

import java.beans.PropertyEditorSupport;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author takanori.takase
 */
public class PathEditor extends PropertyEditorSupport {

	@Override
	public void setAsText(String text) throws IllegalArgumentException {

		Path path = Paths.get(text);

		setValue(path);

	}

}
