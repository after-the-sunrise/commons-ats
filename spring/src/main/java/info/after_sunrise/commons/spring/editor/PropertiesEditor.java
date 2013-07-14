package info.after_sunrise.commons.spring.editor;

import info.after_sunrise.commons.base.object.IOs;

import java.beans.PropertyEditorSupport;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * @author takanori.takase
 */
public class PropertiesEditor extends PropertyEditorSupport {

	@Override
	public void setAsText(String text) throws IllegalArgumentException {

		InputStream in = null;

		try {

			URL url = getClass().getClassLoader().getResource(text);

			in = url.openStream();

			Properties prop = new Properties();

			prop.load(in);

			setValue(prop);

		} catch (Exception e) {
			throw new IllegalArgumentException("Failed to load : " + text, e);
		} finally {
			IOs.closeQuietly(in);
		}

	}

}
