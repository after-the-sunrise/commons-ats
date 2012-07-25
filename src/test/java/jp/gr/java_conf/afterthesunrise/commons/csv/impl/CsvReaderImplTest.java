package jp.gr.java_conf.afterthesunrise.commons.csv.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import au.com.bytecode.opencsv.CSVReader;

import com.google.common.base.Charsets;
import com.google.common.io.Closeables;
import com.google.common.io.Resources;

/**
 * @author takanori.takase
 */
public class CsvReaderImplTest {

	private CsvReaderImpl target;

	@Before
	public void setUp() throws Exception {
		target = new CsvReaderImpl();
	}

	@Test
	public void testSetParameters() throws IOException {

		target.setUnknownPrefix("ERROR-");
		target.setCharset(Charsets.UTF_16);
		target.setSeparator('|');
		target.setQuote('\'');
		target.setEscape('\\');
		target.setSkip(1);
		target.setStrict(false);

		StringBuilder sb = new StringBuilder();
		sb.append("r1c1|'r1\\|c2'|r1c3\n");
		sb.append("r2c1|'r2\\|c2'|r2c3\n");
		sb.append("r3c1|'r3\\|c2'|r3c3\n");
		byte[] bytes = sb.toString().getBytes(Charsets.UTF_16);

		InputStream in = new ByteArrayInputStream(bytes);

		CSVReader reader = target.createReader(in);

		String[] val1 = reader.readNext();
		String[] val2 = reader.readNext();
		String[] val3 = reader.readNext();
		String[] val4 = reader.readNext();

		assertEquals("[r2c1, r2|c2, r2c3]", Arrays.toString(val1));
		assertEquals("[r3c1, r3|c2, r3c3]", Arrays.toString(val2));
		assertNull(val3);
		assertNull(val4);

	}

	private void checkContent(List<Map<String, String>> result) {

		assertEquals(7, result.size());

		Iterator<Map<String, String>> itr = result.iterator();

		Map<String, String> map = itr.next();
		assertEquals(3, map.size());
		assertEquals("r1c1", map.get("col1"));
		assertEquals("r1c2", map.get("col2"));
		assertEquals("r1c3", map.get("col3"));

		map = itr.next();
		assertEquals(3, map.size());
		assertEquals("r2c1", map.get("col1"));
		assertEquals("r2c2", map.get("col2"));
		assertEquals("r2c3", map.get("col3"));

		map = itr.next();
		assertEquals(4, map.size());
		assertEquals("r3c1", map.get("col1"));
		assertEquals("r3c2", map.get("col2"));
		assertEquals("r3c3", map.get("col3"));
		assertEquals("r3c4", map.get("__UNKNOWN-4"));

		map = itr.next();
		assertEquals(2, map.size());
		assertEquals("r4c1", map.get("col1"));
		assertEquals("r4c2", map.get("col2"));

		map = itr.next();
		assertEquals(3, map.size());
		assertEquals("", map.get("col1"));
		assertEquals("", map.get("col2"));
		assertEquals("", map.get("col3"));

		map = itr.next();
		assertEquals(1, map.size());
		assertEquals("", map.get("col1"));

		map = itr.next();
		assertEquals(3, map.size());
		assertEquals("r7c1", map.get("col1"));
		assertEquals("r7c2", map.get("col2"));
		assertEquals("r7c3", map.get("col3"));

		assertFalse(itr.hasNext());

	}

	@Test
	public void testRead_File() throws IOException {

		File file = new File("src/test/resources/CsvReaderImplTest.csv");

		checkContent(target.read(file));

	}

	@Test
	public void testRead_URL() throws IOException {

		URL url = Resources.getResource("CsvReaderImplTest.csv");

		checkContent(target.read(url));

	}

	@Test
	public void testRead_Stream() throws IOException {

		URL url = Resources.getResource("CsvReaderImplTest.csv");

		InputStream in = url.openStream();

		try {
			checkContent(target.read(in));
		} finally {
			Closeables.closeQuietly(in);
		}

	}

	private void checkHeader(List<String> result) {
		assertEquals(3, result.size());
		assertEquals("col1", result.get(0));
		assertEquals("col2", result.get(1));
		assertEquals("col3", result.get(2));
	}

	@Test
	public void testReadHeader_File() throws IOException {

		File file = new File("src/test/resources/CsvReaderImplTest.csv");

		checkHeader(target.readHeader(file));

	}

	@Test
	public void testReadHeader_URL() throws IOException {

		URL url = Resources.getResource("CsvReaderImplTest.csv");

		checkHeader(target.readHeader(url));

	}

	@Test
	public void testReadHeader_Stream() throws IOException {

		URL url = Resources.getResource("CsvReaderImplTest.csv");

		InputStream in = url.openStream();

		try {
			checkHeader(target.readHeader(in));
		} finally {
			Closeables.closeQuietly(in);
		}

	}

}
