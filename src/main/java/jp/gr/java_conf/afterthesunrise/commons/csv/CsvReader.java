package jp.gr.java_conf.afterthesunrise.commons.csv;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * @author takanori.takase
 */
public interface CsvReader {

	long read(URL url, CsvLineHandler handler) throws IOException;

	long read(File file, CsvLineHandler handler) throws IOException;

	List<Map<String, String>> read(URL url) throws IOException;

	List<Map<String, String>> read(File file) throws IOException;

}
