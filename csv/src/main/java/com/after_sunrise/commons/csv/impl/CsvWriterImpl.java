package com.after_sunrise.commons.csv.impl;

import static au.com.bytecode.opencsv.CSVWriter.DEFAULT_ESCAPE_CHARACTER;
import static au.com.bytecode.opencsv.CSVWriter.DEFAULT_LINE_END;
import static au.com.bytecode.opencsv.CSVWriter.DEFAULT_QUOTE_CHARACTER;
import static au.com.bytecode.opencsv.CSVWriter.DEFAULT_SEPARATOR;
import static com.after_sunrise.commons.base.object.Validations.checkNotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import au.com.bytecode.opencsv.CSVWriter;

import com.after_sunrise.commons.base.comparator.NullSafeComparator;
import com.after_sunrise.commons.base.object.IOs;
import com.after_sunrise.commons.base.object.Streams;
import com.after_sunrise.commons.csv.CsvWriter;

/**
 * @author takanori.takase
 */
public class CsvWriterImpl implements CsvWriter {

	private static final Charset CHARSET = Charset.forName("UTF-8");

	private Charset charset = CHARSET;

	private char separator = DEFAULT_SEPARATOR;

	private char quote = DEFAULT_QUOTE_CHARACTER;

	private char escape = DEFAULT_ESCAPE_CHARACTER;

	private String lineEnd = DEFAULT_LINE_END;

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

	public void setLineEnd(String lineEnd) {
		this.lineEnd = lineEnd;
	}

	@Override
	public void write(File file, Collection<String> headers,
			Iterable<Collection<String>> iterable) throws IOException {

		OutputStream out = generateOutputStream(file);

		try {
			write(out, headers, iterable);
		} finally {
			IOs.closeQuietly(out);
		}

	}

	@Override
	public void writeMap(File file, Collection<String> headers,
			Iterable<Map<String, String>> iterable) throws IOException {

		OutputStream out = generateOutputStream(file);

		try {
			writeMap(out, headers, iterable);
		} finally {
			IOs.closeQuietly(out);
		}

	}

	@Override
	public void writeMap(File file, Iterable<Map<String, String>> iterable)
			throws IOException {

		OutputStream out = generateOutputStream(file);

		try {
			writeMap(out, iterable);
		} finally {
			IOs.closeQuietly(out);
		}

	}

	@Override
	public void write(OutputStream out, Collection<String> headers,
			Iterable<Collection<String>> iterable) throws IOException {

		CSVWriter writer = createWriter(out);

		try {

			String[] line = headers.toArray(new String[headers.size()]);

			writer.writeNext(line);

			writeValues(writer, line.length, iterable);

		} finally {
			IOs.closeQuietly(writer);
		}

	}

	@Override
	public void writeMap(OutputStream out, Collection<String> headers,
			Iterable<Map<String, String>> iterable) throws IOException {

		CSVWriter writer = createWriter(out);

		try {

			String[] line = headers.toArray(new String[headers.size()]);

			writer.writeNext(line);

			writeValues(writer, line, iterable);

		} finally {
			IOs.closeQuietly(writer);
		}

	}

	@Override
	public void writeMap(OutputStream out,
			Iterable<Map<String, String>> iterable) throws IOException {

		Set<String> headers = new TreeSet<String>(NullSafeComparator.get());

		for (Map<String, String> value : iterable) {

			if (value == null || value.isEmpty()) {
				continue;
			}

			headers.addAll(value.keySet());

		}

		writeMap(out, headers, iterable);

	}

	private void writeValues(CSVWriter writer, int columns,
			Iterable<Collection<String>> itr) {

		for (Collection<String> value : itr) {

			String[] arr = new String[columns];

			if (value == null || value.isEmpty()) {

				writer.writeNext(arr);

				continue;

			}

			arr = value.toArray(arr);

			writer.writeNext(arr);

		}

	}

	private void writeValues(CSVWriter writer, String[] headers,
			Iterable<Map<String, String>> iterable) {

		for (Map<String, String> value : iterable) {

			String[] arr = new String[headers.length];

			if (value == null || value.isEmpty()) {

				writer.writeNext(arr);

				continue;

			}

			for (int i = 0; i < headers.length; i++) {

				String key = headers[i];

				arr[i] = value.get(key);

			}

			writer.writeNext(arr);

		}

	}

	OutputStream generateOutputStream(File file) throws FileNotFoundException {
		return new FileOutputStream(file, false);
	}

	CSVWriter createWriter(OutputStream out) throws IOException {

		OutputStream uncloseable = Streams.wrapUncloseable(out);

		OutputStreamWriter writer = new OutputStreamWriter(uncloseable, charset);

		return new CSVWriter(writer, separator, quote, escape, lineEnd);

	}

}
