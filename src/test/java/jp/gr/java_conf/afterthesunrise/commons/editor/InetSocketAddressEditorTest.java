package jp.gr.java_conf.afterthesunrise.commons.editor;

import static org.junit.Assert.assertEquals;

import java.net.InetSocketAddress;

import org.junit.Before;
import org.junit.Test;

/**
 * @author takanori.takase
 */
public class InetSocketAddressEditorTest {

	private InetSocketAddressEditor target;

	@Before
	public void setUp() throws Exception {
		target = new InetSocketAddressEditor();
	}

	@Test
	public void testSetAsText() {

		target.setAsText("localhost:80");

		InetSocketAddress address = (InetSocketAddress) target.getValue();

		assertEquals("localhost", address.getHostString());

		assertEquals(80, address.getPort());

	}

	@Test
	public void testSetAsText_PortOnly() {

		target.setAsText("80");

		InetSocketAddress address = (InetSocketAddress) target.getValue();

		assertEquals("0.0.0.0", address.getHostString());

		assertEquals(80, address.getPort());

	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetAsText_Invalid() {

		target.setAsText("localhost:hoge");

	}

}
