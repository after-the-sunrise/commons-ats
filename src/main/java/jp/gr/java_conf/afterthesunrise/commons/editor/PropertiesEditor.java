package jp.gr.java_conf.afterthesunrise.commons.editor;

import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import com.google.common.io.Closeables;
import com.google.common.io.Resources;

/**
 * @author takanori.takase
 */
public class PropertiesEditor extends PropertyEditorSupport {

	@Override
	public void setAsText(String text) throws IllegalArgumentException {

		InputStream in = null;

		try {

			URL url = Resources.getResource(text);

			in = url.openStream();

			Properties prop = new Properties();

			prop.load(in);

			setValue(prop);

		} catch (IOException | RuntimeException e) {
			throw new IllegalArgumentException("Failed to load : " + text, e);
		} finally {
			Closeables.closeQuietly(in);
		}

	}

}
