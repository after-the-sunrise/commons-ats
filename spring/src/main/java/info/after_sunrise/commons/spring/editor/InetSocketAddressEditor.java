package info.after_sunrise.commons.spring.editor;

import java.beans.PropertyEditorSupport;
import java.net.InetSocketAddress;

/**
 * @author takanori.takase
 */
public class InetSocketAddressEditor extends PropertyEditorSupport {

	private static final String DELIMITER = ":";

	@Override
	public void setAsText(String text) throws IllegalArgumentException {

		try {

			int idx = text.lastIndexOf(DELIMITER);

			if (idx < 0) {

				int port = Integer.parseInt(text);

				setValue(new InetSocketAddress(port));

			} else {

				String host = text.substring(0, idx);

				String portStr = text.substring(idx + 1, text.length());

				int port = Integer.parseInt(portStr);

				setValue(new InetSocketAddress(host, port));

			}

		} catch (Exception e) {
			throw new IllegalArgumentException(text, e);
		}

	}

}
