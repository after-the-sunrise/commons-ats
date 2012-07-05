package jp.gr.java_conf.afterthesunrise.commons.csv.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;

import au.com.bytecode.opencsv.CSVWriter;

import com.google.common.base.Charsets;

/**
 * @author takanori.takase
 */
public class CsvWriterImplTest {

	private CsvWriterImpl target;

	@Before
	public void setUp() throws Exception {
		target = new CsvWriterImpl();
	}

	@Test(expected = NullPointerException.class)
	public void testGenerateOutputStream() throws FileNotFoundException {
		target.generateOutputStream(null);
	}

	@Test
	public void testSetParameters() throws IOException {

		target.setCharset(Charsets.UTF_16);
		target.setQuote('\'');
		target.setEscape('\\');
		target.setLineEnd("|EOL|");

		ByteArrayOutputStream out = new ByteArrayOutputStream();

		CSVWriter writer = target.createWriter(out);
		writer.writeNext(new String[] { "col1", "col2", "col3" });
		writer.writeNext(new String[] { "r1c1", "r1'c2", "r1c3" });
		writer.close();

		String result = new String(out.toByteArray(), Charsets.UTF_16);

		assertEquals("'col1','col2','col3'|EOL|'r1c1','r1\\'c2','r1c3'|EOL|",
				result);

	}

	@Test
	public void testWrite() throws IOException {

		ByteArrayOutputStream out = new ByteArrayOutputStream();

		target = spy(target);
		target.setQuote('\'');

		doReturn(out).when(target).generateOutputStream(any(File.class));

		List<String> headers = new ArrayList<>();
		headers.add("col1");
		headers.add("col2");

		List<List<String>> values = new ArrayList<>();
		values.add(Arrays.asList("r1c1", "r1c2"));
		values.add(Arrays.asList("r2c1", "r2c2", "r2c3"));
		values.add(Arrays.asList("r3c1"));
		values.add(null);
		values.add(Arrays.asList("r5c1", "r5c2"));

		target.write(null, headers, values);

		String result = new String(out.toByteArray(), Charsets.UTF_8);

		String[] arr = StringUtils.splitPreserveAllTokens(result, "\n");
		assertEquals(7, arr.length);
		assertEquals("'col1','col2'", arr[0]);
		assertEquals("'r1c1','r1c2'", arr[1]);
		assertEquals("'r2c1','r2c2','r2c3'", arr[2]);
		assertEquals("'r3c1',", arr[3]);
		assertEquals(",", arr[4]);
		assertEquals("'r5c1','r5c2'", arr[5]);
		assertEquals("", arr[6]);

	}

}
