package info.after_sunrise.commons.csv;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Map;

/**
 * @author takanori.takase
 */
public interface CsvWriter {

	void write(File file, Collection<String> headers,
			Iterable<Collection<String>> iterable) throws IOException;

	void writeMap(File file, Collection<String> headers,
			Iterable<Map<String, String>> iterable) throws IOException;

	void writeMap(File file, Iterable<Map<String, String>> iterable)
			throws IOException;

	void write(OutputStream out, Collection<String> headers,
			Iterable<Collection<String>> iterable) throws IOException;

	void writeMap(OutputStream out, Collection<String> headers,
			Iterable<Map<String, String>> iterable) throws IOException;

	void writeMap(OutputStream out, Iterable<Map<String, String>> iterable)
			throws IOException;

}
