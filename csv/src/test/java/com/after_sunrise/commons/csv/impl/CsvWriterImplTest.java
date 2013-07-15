package com.after_sunrise.commons.csv.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import au.com.bytecode.opencsv.CSVWriter;

/**
 * @author takanori.takase
 */
public class CsvWriterImplTest {

	private static Charset UTF_8 = Charset.forName("UTF-8");

	private static Charset UTF_16 = Charset.forName("UTF-16");

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

		target.setCharset(UTF_16);
		target.setSeparator(':');
		target.setQuote('\'');
		target.setEscape('\\');
		target.setLineEnd("|EOL|");

		ByteArrayOutputStream out = new ByteArrayOutputStream();

		CSVWriter writer = target.createWriter(out);
		writer.writeNext(new String[] { "col1", "col2" });
		writer.writeNext(new String[] { "r1c1", "r1'c2" });
		writer.close();

		String result = new String(out.toByteArray(), UTF_16);

		assertEquals("'col1':'col2'|EOL|'r1c1':'r1\\'c2'|EOL|", result);

	}

	@Test
	public void testWrite() throws IOException {

		ByteArrayOutputStream out = new ByteArrayOutputStream();

		target = spy(target);
		target.setQuote('\'');

		doReturn(out).when(target).generateOutputStream(any(File.class));

		List<String> headers = new ArrayList<String>();
		headers.add("col1");
		headers.add("col2");

		List<Collection<String>> values = new ArrayList<Collection<String>>();
		values.add(Arrays.asList("r1c1", "r1c2"));
		values.add(Arrays.asList("r2c1", "r2c2", "r2c3"));
		values.add(Arrays.asList("r3c1"));
		values.add(null);
		values.add(Arrays.asList("r5c1", "r5c2"));

		target.write((File) null, headers, values);

		String result = new String(out.toByteArray(), UTF_8);

		String[] arr = result.split("\\r?\\n");
		assertEquals(6, arr.length);
		assertEquals("'col1','col2'", arr[0]);
		assertEquals("'r1c1','r1c2'", arr[1]);
		assertEquals("'r2c1','r2c2','r2c3'", arr[2]);
		assertEquals("'r3c1',", arr[3]);
		assertEquals(",", arr[4]);
		assertEquals("'r5c1','r5c2'", arr[5]);

	}

	@Test
	public void testWriteMap() throws IOException {

		ByteArrayOutputStream out = new ByteArrayOutputStream();

		target = spy(target);
		target.setQuote('\'');

		doReturn(out).when(target).generateOutputStream(any(File.class));

		List<String> headers = new ArrayList<String>();
		headers.add("col1");
		headers.add("col2");

		Map<String, String> map1 = new HashMap<String, String>();
		map1.put("col1", "r1c1");
		map1.put("col2", "r1c2");
		Map<String, String> map2 = new HashMap<String, String>();
		map2.put("col1", "r2c1");
		map2.put("col2", "r2c2");
		map2.put("col3", "r2c3");
		Map<String, String> map3 = new HashMap<String, String>();
		map3.put("col1", "r3c1");
		Map<String, String> map4 = null;
		Map<String, String> map5 = new HashMap<String, String>();
		map5.put("col1", "r5c1");
		map5.put("col2", "r5c2");

		List<Map<String, String>> values = new ArrayList<Map<String, String>>();
		values.add(map1);
		values.add(map2);
		values.add(map3);
		values.add(map4);
		values.add(map5);

		target.writeMap((File) null, headers, values);

		String result = new String(out.toByteArray(), UTF_8);

		String[] arr = result.split("\n");
		assertEquals(6, arr.length);
		assertEquals("'col1','col2'", arr[0]);
		assertEquals("'r1c1','r1c2'", arr[1]);
		assertEquals("'r2c1','r2c2'", arr[2]);
		assertEquals("'r3c1',", arr[3]);
		assertEquals(",", arr[4]);
		assertEquals("'r5c1','r5c2'", arr[5]);

	}

	@Test
	public void testWriteMap_WithoutExplicitHeader() throws IOException {

		ByteArrayOutputStream out = new ByteArrayOutputStream();

		target = spy(target);
		target.setQuote('\'');

		doReturn(out).when(target).generateOutputStream(any(File.class));

		Map<String, String> map1 = new HashMap<String, String>();
		map1.put("col1", "r1c1");
		map1.put("col2", "r1c2");
		Map<String, String> map2 = new HashMap<String, String>();
		map2.put("col1", "r2c1");
		map2.put("col2", "r2c2");
		map2.put("col3", "r2c3");
		Map<String, String> map3 = new HashMap<String, String>();
		map3.put("col1", "r3c1");
		Map<String, String> map4 = null;
		Map<String, String> map5 = new HashMap<String, String>();
		map5.put("col1", "r5c1");
		map5.put("col2", "r5c2");

		List<Map<String, String>> values = new ArrayList<Map<String, String>>();
		values.add(map1);
		values.add(map2);
		values.add(map3);
		values.add(map4);
		values.add(map5);

		target.writeMap((File) null, values);

		String result = new String(out.toByteArray(), UTF_8);

		String[] arr = result.split("\n");
		assertEquals(6, arr.length);
		assertEquals("'col1','col2','col3'", arr[0]);
		assertEquals("'r1c1','r1c2',", arr[1]);
		assertEquals("'r2c1','r2c2','r2c3'", arr[2]);
		assertEquals("'r3c1',,", arr[3]);
		assertEquals(",,", arr[4]);
		assertEquals("'r5c1','r5c2',", arr[5]);

	}
}
