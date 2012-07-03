package jp.gr.java_conf.afterthesunrise.commons.csv;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author takanori.takase
 */
public interface CsvWriter {

	void write(File file, List<String> headers, Iterable<List<String>> iterable)
			throws IOException;

}
