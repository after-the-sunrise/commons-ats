package jp.gr.java_conf.afterthesunrise.commons.csv.impl;

import static au.com.bytecode.opencsv.CSVParser.DEFAULT_ESCAPE_CHARACTER;
import static au.com.bytecode.opencsv.CSVParser.DEFAULT_QUOTE_CHARACTER;
import static au.com.bytecode.opencsv.CSVParser.DEFAULT_SEPARATOR;
import static au.com.bytecode.opencsv.CSVParser.DEFAULT_STRICT_QUOTES;
import static au.com.bytecode.opencsv.CSVReader.DEFAULT_SKIP_LINES;
import static jp.gr.java_conf.afterthesunrise.commons.log.Logs.logWarn;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.gr.java_conf.afterthesunrise.commons.csv.CsvLineHandler;
import jp.gr.java_conf.afterthesunrise.commons.csv.CsvReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import au.com.bytecode.opencsv.CSVReader;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Charsets;
import com.google.common.base.Objects;
import com.google.common.io.Closeables;

/**
 * @author takanori.takase
 */
@Component
public class CsvReaderImpl implements CsvReader {

	private static final Charset CHARSET = Charsets.UTF_8;

	private final Log log = LogFactory.getLog(getClass());

	private Charset charset = CHARSET;

	private char separator = DEFAULT_SEPARATOR;

	private char quote = DEFAULT_QUOTE_CHARACTER;

	private char escape = DEFAULT_ESCAPE_CHARACTER;

	private int skip = DEFAULT_SKIP_LINES;

	private boolean strict = DEFAULT_STRICT_QUOTES;

	public void setCharset(Charset charset) {
		this.charset = Objects.firstNonNull(charset, CHARSET);
	}

	public void setSeparator(char separator) {
		this.separator = separator;
	}

	public void setQuote(char quote) {
		this.quote = quote;
	}

	public void setEscape(char escape) {
		this.escape = escape;
	}

	public void setSkip(int skip) {
		this.skip = skip;
	}

	public void setStrict(boolean strict) {
		this.strict = strict;
	}

	@Override
	public long read(File file, CsvLineHandler handler) throws IOException {

		CSVReader reader = createReader(file);

		try {

			String[] headers = readHeader(reader);

			return readValues(headers, reader, handler);

		} finally {
			Closeables.closeQuietly(reader);
		}

	}

	CSVReader createReader(File file) throws IOException {

		if (file == null) {
			throw new IOException("File is null.");
		}

		FileInputStream in = new FileInputStream(file);

		InputStreamReader reader = new InputStreamReader(in, charset);

		return generateReader(reader);

	}

	@VisibleForTesting
	CSVReader generateReader(Reader reader) {
		return new CSVReader(reader, separator, quote, escape, skip, strict);
	}

	@VisibleForTesting
	String[] readHeader(CSVReader reader) throws IOException {
		return reader.readNext();
	}

	@VisibleForTesting
	long readValues(String[] headers, CSVReader reader, CsvLineHandler handler)
			throws IOException {

		long count = 0L;

		String[] values;

		while ((values = reader.readNext()) != null) {

			count++;

			Map<String, String> line = new HashMap<>();

			if (headers.length != values.length) {

				// Include header line
				long num = count + 1;

				String val = Arrays.toString(values);

				logWarn(log, "Column mismatch with header : [%d] %s", num, val);

			}

			for (int i = 0; i < values.length; i++) {

				String header;

				if (headers.length > i) {
					header = headers[i];
				} else {
					header = UNKNOWN_COLUMN + (i + 1);
				}

				line.put(header, values[i]);

			}

			handler.handle(line);

		}

		return count;

	}

	@Override
	public List<Map<String, String>> read(File file) throws IOException {

		final List<Map<String, String>> lines = new ArrayList<Map<String, String>>();

		read(file, new CsvLineHandler() {
			@Override
			public void handle(Map<String, String> map) {
				lines.add(map);
			}
		});

		return lines;

	}

}
