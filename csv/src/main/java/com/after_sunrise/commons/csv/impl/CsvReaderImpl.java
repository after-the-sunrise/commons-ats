package com.after_sunrise.commons.csv.impl;

import static au.com.bytecode.opencsv.CSVParser.DEFAULT_ESCAPE_CHARACTER;
import static au.com.bytecode.opencsv.CSVParser.DEFAULT_QUOTE_CHARACTER;
import static au.com.bytecode.opencsv.CSVParser.DEFAULT_SEPARATOR;
import static au.com.bytecode.opencsv.CSVParser.DEFAULT_STRICT_QUOTES;
import static au.com.bytecode.opencsv.CSVReader.DEFAULT_SKIP_LINES;
import static com.after_sunrise.commons.base.object.Validations.checkNotNull;

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

import au.com.bytecode.opencsv.CSVReader;

import com.after_sunrise.commons.base.object.IOs;
import com.after_sunrise.commons.csv.CsvLineHandler;
import com.after_sunrise.commons.csv.CsvReader;

/**
 * @author takanori.takase
 */
public class CsvReaderImpl implements CsvReader {

	private static final Charset CHARSET = Charset.forName("UTF-8");

	private Charset charset = CHARSET;

	private char separator = DEFAULT_SEPARATOR;

	private char quote = DEFAULT_QUOTE_CHARACTER;

	private char escape = DEFAULT_ESCAPE_CHARACTER;

	private int skip = DEFAULT_SKIP_LINES;

	private boolean strict = DEFAULT_STRICT_QUOTES;

	public void setCharset(Charset charset) {
		this.charset = checkNotNull(charset);
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
			IOs.closeQuietly(in);
		}

	}

	@Override
	public long read(URL url, CsvLineHandler handler) throws IOException {

		InputStream in = url.openStream();

		try {
			return read(in, handler);
		} finally {
			IOs.closeQuietly(in);
		}

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

	@Override
	public List<Map<String, String>> read(URL url) throws IOException {

		final List<Map<String, String>> lines = new ArrayList<Map<String, String>>();

		read(url, new CsvLineHandler() {
			@Override
			public void handle(Map<String, String> map) {
				lines.add(map);
			}
		});

		return lines;

	}

	@Override
	public List<String> readHeader(File file) throws IOException {

		InputStream in = new FileInputStream(file);

		try {
			return readHeader(in);
		} finally {
			IOs.closeQuietly(in);
		}

	}

	@Override
	public List<String> readHeader(URL url) throws IOException {

		InputStream in = url.openStream();

		try {
			return readHeader(in);
		} finally {
			IOs.closeQuietly(in);
		}

	}

	@Override
	public List<Map<String, String>> read(InputStream in) throws IOException {

		final List<Map<String, String>> lines = new ArrayList<Map<String, String>>();

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

		CSVReader reader = createReader(in);

		String[] headers = reader.readNext();

		return readValues(headers, reader, handler);

	}

	@Override
	public List<String> readHeader(InputStream in) throws IOException {

		CSVReader reader = createReader(in);

		String[] headers = reader.readNext();

		return Arrays.asList(headers);

	}

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

			Map<String, String> line = new HashMap<String, String>();

			if (headers.length != values.length) {

				// Include header line
				long num = count + 1;

				String msg = "Column mismatch with header : [%d] %s";

				String val = Arrays.toString(values);

				throw new IOException(String.format(msg, num, val));

			}

			for (int i = 0; i < values.length; i++) {
				line.put(headers[i], values[i]);
			}

			handler.handle(line);

		}

		return count;

	}

}
