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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.gr.java_conf.afterthesunrise.commons.csv.CsvLineHandler;
import jp.gr.java_conf.afterthesunrise.commons.csv.CsvReader;

import org.apache.commons.io.input.CloseShieldInputStream;
import org.apache.commons.lang.ObjectUtils;
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

	private static final String UNKNOWN_COLUMN = "__UNKNOWN-";

	private final Log log = LogFactory.getLog(getClass());

	private String unknownPrefix = UNKNOWN_COLUMN;

	private Charset charset = CHARSET;

	private char separator = DEFAULT_SEPARATOR;

	private char quote = DEFAULT_QUOTE_CHARACTER;

	private char escape = DEFAULT_ESCAPE_CHARACTER;

	private int skip = DEFAULT_SKIP_LINES;

	private boolean strict = DEFAULT_STRICT_QUOTES;

	public void setUnknownPrefix(String unknownPrefix) {
		this.unknownPrefix = ObjectUtils.toString(unknownPrefix);
	}

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

		InputStream in = new FileInputStream(file);

		try {
			return read(in, handler);
		} finally {
			Closeables.closeQuietly(in);
		}

	}

	@Override
	public long read(URL url, CsvLineHandler handler) throws IOException {

		InputStream in = url.openStream();

		try {
			return read(in, handler);
		} finally {
			Closeables.closeQuietly(in);
		}

	}

	@Override
	public List<Map<String, String>> read(File file) throws IOException {

		final List<Map<String, String>> lines = new ArrayList<>();

		read(file, new CsvLineHandler() {
			@Override
			public void handle(Map<String, String> map) {
				lines.add(map);
			}
		});

		return lines;

	}

	@Override
	public List<Map<String, String>> read(URL url) throws IOException {

		final List<Map<String, String>> lines = new ArrayList<>();

		read(url, new CsvLineHandler() {
			@Override
			public void handle(Map<String, String> map) {
				lines.add(map);
			}
		});

		return lines;

	}

	@Override
	public List<Map<String, String>> read(InputStream in) throws IOException {

		final List<Map<String, String>> lines = new ArrayList<>();

		read(in, new CsvLineHandler() {
			@Override
			public void handle(Map<String, String> map) {
				lines.add(map);
			}
		});

		return lines;

	}

	@Override
	public long read(InputStream in, CsvLineHandler handler) throws IOException {

		CSVReader reader = createReader(new CloseShieldInputStream(in));

		try {

			String[] headers = reader.readNext();

			return readValues(headers, reader, handler);

		} finally {
			Closeables.closeQuietly(reader);
		}

	}

	@VisibleForTesting
	CSVReader createReader(InputStream in) throws IOException {

		InputStreamReader reader = new InputStreamReader(in, charset);

		return new CSVReader(reader, separator, quote, escape, skip, strict);

	}

	private long readValues(String[] headers, CSVReader reader,
			CsvLineHandler handler) throws IOException {

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
					header = unknownPrefix + (i + 1);
				}

				line.put(header, values[i]);

			}

			handler.handle(line);

		}

		return count;

	}

}
