package info.after_sunrise.commons.comparator;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

/**
 * @author takanori.takase
 */
public class NullSafeComparatorTest {

	@Test
	public void testCompare() {

		List<String> list = new ArrayList<String>();
		list.add("1");
		list.add(null);
		list.add("2");
		list.add("2");
		list.add(null);
		list.add(null);
		list.add("3");

		int size = list.size();

		Collections.shuffle(list);

		Collections.sort(list, NullSafeComparator.get());

		Iterator<String> itr = list.iterator();
		assertEquals(size, list.size());
		assertEquals(null, itr.next());
		assertEquals(null, itr.next());
		assertEquals(null, itr.next());
		assertEquals("1", itr.next());
		assertEquals("2", itr.next());
		assertEquals("2", itr.next());
		assertEquals("3", itr.next());

	}

}
