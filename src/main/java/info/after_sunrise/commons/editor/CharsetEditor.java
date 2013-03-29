package info.after_sunrise.commons.editor;

import java.beans.PropertyEditorSupport;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.google.common.base.Charsets;

/**
 * @author takanori.takase
 */
public class CharsetEditor extends PropertyEditorSupport {

	private static final Map<String, Charset> CHARSETS = new ConcurrentHashMap<String, Charset>();

	private static final void add(Charset charset) {
		CHARSETS.put(charset.name(), charset);
	}

	static {
		add(Charsets.ISO_8859_1);
		add(Charsets.US_ASCII);
		add(Charsets.UTF_16);
		add(Charsets.UTF_16BE);
		add(Charsets.UTF_16LE);
		add(Charsets.UTF_8);
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
