package com.after_sunrise.commons.spring.editor;

import java.beans.PropertyEditorSupport;

/**
 * @author takanori.takase
 */
public class StringArrayEditor extends PropertyEditorSupport {

	private static final String DELIMITER = ",";

	@Override
	public void setAsText(String text) throws IllegalArgumentException {

		String[] arr;

		if (text != null && !text.isEmpty()) {

			arr = text.split(DELIMITER);

		} else {

			arr = new String[0];

		}

		setValue(arr);

	}

}
