package com.after_sunrise.commons.jcommander.converter;

import static org.junit.Assert.assertEquals;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Before;
import org.junit.Test;

/**
 * @author takanori.takase
 */
public class DateConverterTest {

	private DateConverter target;

	@Before
	public void setUp() throws Exception {
		target = new DateConverter();
	}

	private void check(DateFormat df, Map<String, String> map) throws Exception {

		for (Entry<String, String> entry : map.entrySet()) {

			String key = entry.getKey();

			String val = entry.getValue();

			String msg = String.format("Input[%s] Expect[%s]", key, val);

			assertEquals(msg, df.parse(key), target.convert(val));

		}

	}

	@Test
	public void testConvert() throws Exception {

		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("2013-04-05 12:34:56.789 GMT", "2013-04-05_12:34:56.789 GMT");
		map.put("2013-04-05 12:34:56.000 GMT", "2013-04-05_12:34:56 GMT");
		map.put("2013-04-05 12:34:00.000 GMT", "2013-04-05_12:34 GMT");
		map.put("2013-04-05 12:00:00.000 GMT", "2013-04-05_12 GMT");
		map.put("2013-04-05 00:00:00.000 GMT", "2013-04-05 GMT");
		map.put("2013-04-01 00:00:00.000 GMT", "2013-04 GMT");
		map.put("2013-01-01 00:00:00.000 GMT", "2013 GMT");

		check(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS z"), map);

	}

	@Test
	public void testConvert_LocalTimeZone() throws Exception {

		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("2013-04-05 12:34:56.789", "2013-04-05_12:34:56.789");
		map.put("2013-04-05 12:34:56.000", "2013-04-05_12:34:56");
		map.put("2013-04-05 12:34:00.000", "2013-04-05_12:34");
		map.put("2013-04-05 12:00:00.000", "2013-04-05_12");
		map.put("2013-04-05 00:00:00.000", "2013-04-05");
		map.put("2013-04-01 00:00:00.000", "2013-04");
		map.put("2013-01-01 00:00:00.000", "2013");

		check(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"), map);

	}

	@Test(expected = IllegalArgumentException.class)
	public void testConvert_InvalidParameter() throws Exception {
		target.convert("foo");
	}

}
