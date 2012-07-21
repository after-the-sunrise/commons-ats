package jp.gr.java_conf.afterthesunrise.commons.csv.impl;

import static au.com.bytecode.opencsv.CSVWriter.DEFAULT_ESCAPE_CHARACTER;
import static au.com.bytecode.opencsv.CSVWriter.DEFAULT_LINE_END;
import static au.com.bytecode.opencsv.CSVWriter.DEFAULT_QUOTE_CHARACTER;
import static au.com.bytecode.opencsv.CSVWriter.DEFAULT_SEPARATOR;
import static jp.gr.java_conf.afterthesunrise.commons.log.Logs.logWarn;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.List;

import jp.gr.java_conf.afterthesunrise.commons.csv.CsvWriter;

import org.apache.commons.collections.CollectionUtils;
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
	public void write(File file, List<String> headers,
			Iterable<List<String>> iterable) throws IOException {

		OutputStream out = generateOutputStream(file);

		try {

			CSVWriter writer = createWriter(out);

			try {

				String[] line = headers.toArray(new String[headers.size()]);

				writer.writeNext(line);

				writeValues(writer, line.length, iterable);

			} finally {
				Closeables.closeQuietly(writer);
			}

		} finally {
			Closeables.closeQuietly(out);
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

	private void writeValues(CSVWriter writer, int columns,
			Iterable<List<String>> itr) {

		long row = 0L;

		for (List<String> value : itr) {

			row++;

			String[] arr = new String[columns];

			if (CollectionUtils.isEmpty(value)) {

				writer.writeNext(arr);

				continue;

			}

			arr = value.toArray(arr);

			if (arr.length != columns) {

				String msg = "Column mismtach at row [%s] expected [%s] actual[%s] : %s";

				logWarn(log, msg, row, columns, arr.length, value);

			}

			writer.writeNext(arr);

		}

	}

}
