package jp.gr.java_conf.afterthesunrise.commons.csv;

import java.util.Map;

/**
 * @author takanori.takase
 */
public interface CsvLineHandler {

	void handle(Map<String, String> map);

}
