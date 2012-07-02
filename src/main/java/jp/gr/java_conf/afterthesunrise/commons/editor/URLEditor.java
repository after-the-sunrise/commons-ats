package jp.gr.java_conf.afterthesunrise.commons.editor;

import java.beans.PropertyEditorSupport;
import java.net.URL;

import com.google.common.io.Resources;

/**
 * @author takanori.takase
 */
public class URLEditor extends PropertyEditorSupport {

	@Override
	public void setAsText(String text) throws IllegalArgumentException {

		try {

			URL url = Resources.getResource(text);

			setValue(url);

		} catch (RuntimeException e) {
			throw new IllegalArgumentException("URL not found : " + text, e);
		}

	}

}
