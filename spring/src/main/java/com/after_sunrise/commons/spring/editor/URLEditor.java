package com.after_sunrise.commons.spring.editor;

import java.beans.PropertyEditorSupport;
import java.net.URL;

/**
 * @author takanori.takase
 */
public class URLEditor extends PropertyEditorSupport {

	@Override
	public void setAsText(String text) throws IllegalArgumentException {

		try {

			URL url = getClass().getClassLoader().getResource(text);

			setValue(url);

		} catch (RuntimeException e) {
			throw new IllegalArgumentException("URL not found : " + text, e);
		}

	}

}
