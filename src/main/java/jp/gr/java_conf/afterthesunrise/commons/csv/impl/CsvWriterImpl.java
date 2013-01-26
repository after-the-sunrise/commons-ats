package jp.gr.java_conf.afterthesunrise.commons.csv.impl;

import static au.com.bytecode.opencsv.CSVWriter.DEFAULT_ESCAPE_CHARACTER;
import static au.com.bytecode.opencsv.CSVWriter.DEFAULT_LINE_END;
import static au.com.bytecode.opencsv.CSVWriter.DEFAULT_QUOTE_CHARACTER;
import static au.com.bytecode.opencsv.CSVWriter.DEFAULT_SEPARATOR;
import static jp.gr.java_conf.afterthesunrise.commons.object.Logs.logWarn;

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

import jp.gr.java_conf.afterthesunrise.commons.comparator.NullSafeComparator;
import jp.gr.java_conf.afterthesunrise.commons.csv.CsvWriter;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.output.CloseShieldOutputStream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import au.com.bytecode.opencsv.CSVWriter;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Charsets;
import com.google.common.base.Objects;
import com.google.common.io.Closeables;

/**
 * @author takanori.takase
 */
@Component
public class CsvWriterImpl implements CsvWriter {

	private static final Charset CHARSET = Charsets.UTF_8;

	private final Log log = LogFactory.getLog(getClass());

	private Charset charset = CHARSET;

	private char separator = DEFAULT_SEPARATOR;

	private char quote = DEFAULT_QUOTE_CHARACTER;

	private char escape = DEFAULT_ESCAPE_CHARACTER;

	private String lineEnd = DEFAULT_LINE_END;

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
			Closeables.closeQuietly(out);
		}

	}

	@Override
	public void writeMap(File file, Collection<String> headers,
			Iterable<Map<String, String>> iterable) throws IOException {

		OutputStream out = generateOutputStream(file);

		try {
			writeMap(out, headers, iterable);
		} finally {
			Closeables.closeQuietly(out);
		}

	}

	@Override
	public void writeMap(File file, Iterable<Map<String, String>> iterable)
			throws IOException {

		OutputStream out = generateOutputStream(file);

		try {
			writeMap(out, iterable);
		} finally {
			Closeables.closeQuietly(out);
		}

	}

	@Override
	public void write(OutputStream out, Collection<String> headers,
			Iterable<Collection<String>> iterable) throws IOException {

		CSVWriter writer = createWriter(new CloseShieldOutputStream(out));

		try {

			String[] line = headers.toArray(new String[headers.size()]);

			writer.writeNext(line);

			writeValues(writer, line.length, iterable);

		} finally {
			Closeables.closeQuietly(writer);
		}

	}

	@Override
	public void writeMap(OutputStream out, Collection<String> headers,
			Iterable<Map<String, String>> iterable) throws IOException {

		CSVWriter writer = createWriter(new CloseShieldOutputStream(out));

		try {

			String[] line = headers.toArray(new String[headers.size()]);

			writer.writeNext(line);

			writeValues(writer, line, iterable);

		} finally {
			Closeables.closeQuietly(writer);
		}

	}

	@Override
	public void writeMap(OutputStream out,
			Iterable<Map<String, String>> iterable) throws IOException {

		Set<String> headers = new TreeSet<String>(NullSafeComparator.get());

		for (Map<String, String> value : iterable) {

			if (MapUtils.isEmpty(value)) {
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

			if (CollectionUtils.isEmpty(value)) {

				writer.writeNext(arr);

				continue;

			}

			if (columns != value.size()) {
				logWarn(log, "Column mismatch : %s", value);
			}

			arr = value.toArray(arr);

			writer.writeNext(arr);

		}

	}

	private void writeValues(CSVWriter writer, String[] headers,
			Iterable<Map<String, String>> iterable) {

		for (Map<String, String> value : iterable) {

			String[] arr = new String[headers.length];

			if (MapUtils.isEmpty(value)) {

				writer.writeNext(arr);

				continue;

			}

			if (headers.length != value.size()) {
				logWarn(log, "Column mismatch : %s", value);
			}

			for (int i = 0; i < headers.length; i++) {

				String key = headers[i];

				arr[i] = value.get(key);

			}

			writer.writeNext(arr);

		}

	}

	@VisibleForTesting
	OutputStream generateOutputStream(File file) throws FileNotFoundException {
		return new FileOutputStream(file, false);
	}

	@VisibleForTesting
	CSVWriter createWriter(OutputStream out) throws IOException {

		OutputStreamWriter writer = new OutputStreamWriter(out, charset);

		return new CSVWriter(writer, separator, quote, escape, lineEnd);

	}

}
