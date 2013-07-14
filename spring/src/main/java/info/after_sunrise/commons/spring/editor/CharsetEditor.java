package info.after_sunrise.commons.spring.editor;

import java.beans.PropertyEditorSupport;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author takanori.takase
 */
public class CharsetEditor extends PropertyEditorSupport {

	private static final Map<String, Charset> CHARSETS = new ConcurrentHashMap<String, Charset>();

	private static final void add(Charset charset) {
		CHARSETS.put(charset.name(), charset);
	}

	static {
		add(Charset.forName("UTF-8"));
		add(Charset.forName("UTF-16"));
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {

		Charset charset = CHARSETS.get(text);

		if (charset == null) {

			charset = Charset.forName(text);

			CHARSETS.put(text, charset);

		}

		setValue(charset);

	}

}
