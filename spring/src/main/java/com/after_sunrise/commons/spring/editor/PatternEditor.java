package com.after_sunrise.commons.spring.editor;

import java.beans.PropertyEditorSupport;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

/**
 * @author takanori.takase
 */
public class PatternEditor extends PropertyEditorSupport {

	private static final Map<String, Pattern> PATTERNS = new ConcurrentHashMap<String, Pattern>();

	@Override
	public void setAsText(String text) throws IllegalArgumentException {

		Pattern pattern = PATTERNS.get(text);

		if (pattern == null) {

			pattern = Pattern.compile(text);

			PATTERNS.put(text, pattern);

		}

		setValue(pattern);

	}

}
