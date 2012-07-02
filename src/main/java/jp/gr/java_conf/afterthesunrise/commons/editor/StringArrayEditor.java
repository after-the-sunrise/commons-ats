package jp.gr.java_conf.afterthesunrise.commons.editor;

import java.beans.PropertyEditorSupport;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

/**
 * @author takanori.takase
 */
public class StringArrayEditor extends PropertyEditorSupport {

	private static final String DELIMITER = ",";

	@Override
	public void setAsText(String text) throws IllegalArgumentException {

		String[] arr = ArrayUtils.EMPTY_STRING_ARRAY;

		if (StringUtils.isNotBlank(text)) {

			arr = StringUtils.splitPreserveAllTokens(text, DELIMITER);

		}

		setValue(arr);

	}

}
