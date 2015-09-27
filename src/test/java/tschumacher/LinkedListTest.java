package tschumacher;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

import org.junit.Test;

public class LinkedListTest {

	/**
	 * <p>Helper to quickly make a list with specified elements for tests.</p>
	 * @param args
	 * @return
	 */
	public static List<String> makelist(String... args) {
		//List<String> result = new java.util.ArrayList<String>();
		//List<String> result = new java.util.LinkedList<String>();
		List<String> result = new tschumacher.LinkedList<String>();
		
		for (String arg : args) {
			result.add(arg);
		}
		return result;
	}
	
	public static String str(Object o) {
		if (o == null) return "null";
		return o.toString();
	}
	
	@Test
	public void testToArray() {
		List<String> list = makelist();
		
		Object values[] = list.toArray();
		assertThat(values.length, is(0));
		
		list.add("foo");
		values = list.toArray();
		assertThat(values.length, is(1));
		assertThat(str(values[0]), is("foo"));
		
		list.add("foo");
		values = list.toArray();
		assertThat(values.length, is(2));
		assertThat(str(values[0]), is("foo"));
		assertThat(str(values[1]), is("foo"));
			
		list.add("bar");
		values = list.toArray();
		assertThat(values.length, is(3));
		assertThat(str(values[0]), is("foo"));
		assertThat(str(values[1]), is("foo"));
		assertThat(str(values[2]), is("bar"));
	}
	
	@Test
	public void basicFunctionalityTest() {
		List<String> list = makelist();
		assertThat(list.toString(), is("[]"));
		assertThat(list.size(), is(0));
		
		list.add("foo");
		assertThat(list.size(), is(1));
		assertThat(list.toString(), is("[foo]"));
		
		list.add("bar");
		assertThat(list.size(), is(2));
		assertThat(list.toString(), is("[foo, bar]"));
		
		list.add("baz");
		assertThat(list.size(), is(3));
		assertThat(list.toString(), is("[foo, bar, baz]"));
		
		assertThat(list.remove(1), is("bar"));
		assertThat(list.size(), is(2));
		assertThat(list.toString(), is("[foo, baz]"));
		
		list.add("foo");
		assertThat(list.size(), is(3));
		assertThat(list.toString(), is("[foo, baz, foo]"));

		assertTrue(list.remove("foo"));
		assertThat(list.size(), is(2));
		assertThat(list.toString(), is("[baz, foo]"));
		
		assertTrue(list.remove("foo"));
		assertThat(list.size(), is(1));
		assertThat(list.toString(), is("[baz]"));
		
		assertFalse(list.remove("foo"));
		assertThat(list.size(), is(1));
		assertThat(list.toString(), is("[baz]"));
		
		assertTrue(list.remove("baz"));
		assertThat(list.size(), is(0));
		assertThat(list.toString(), is("[]"));
	}
	
	@Test
	public void testSet() {
		List<String> list = makelist("foo", "bar", "baz");
		
		assertThat(list.set(0, "new"), is("foo"));
		assertThat(list.toString(), is("[new, bar, baz]"));
		
		assertThat(list.set(2, "new2"), is("baz"));
		assertThat(list.toString(), is("[new, bar, new2]"));
		
		assertNull(list.set(10, "nonexistent"));
		assertThat(list.toString(), is("[new, bar, new2]"));
		
		assertNull(list.set(-1, "nonexistent"));
		assertThat(list.toString(), is("[new, bar, new2]"));
	}
	
	@Test
	public void testIndexOfWithEmptyList() {
		List<String> list = makelist();
		
		assertThat(list.indexOf(null), is(-1));
		assertThat(list.indexOf("test"), is(-1));		
	}
	
	@Test
	public void testRemoveHeadValue() {
		List<String> list = makelist("foo", "bar", "baz");
		list.remove("foo");
		assertThat(list.size(), is(2));
		assertThat(list.toString(), is("[bar, baz]"));
	}
	
	@Test
	public void testRemoveMiddleValue() {
		List<String> list = makelist("foo", "bar", "baz");
		list.remove("bar");
		assertThat(list.size(), is(2));
		assertThat(list.toString(), is("[foo, baz]"));
	}
	
	@Test
	public void testRemoveTailValue() {
		List<String> list = makelist("foo", "bar", "baz");
		list.remove("baz");
		assertThat(list.size(), is(2));
		assertThat(list.toString(), is("[foo, bar]"));
	}

	@Test
	public void testRemoveByIndexHeadValue() {
		List<String> list = makelist("foo", "bar", "baz");
		list.remove(0);
		assertThat(list.size(), is(2));
		assertThat(list.toString(), is("[bar, baz]"));
	}
	
	@Test
	public void testRemoveByIndexMiddleValue() {
		List<String> list = makelist("foo", "bar", "baz");
		list.remove(1);
		assertThat(list.size(), is(2));
		assertThat(list.toString(), is("[foo, baz]"));
	}
	
	@Test
	public void testRemoveByIndexTailValue() {
		List<String> list = makelist("foo", "bar", "baz");
		list.remove(2);
		assertThat(list.size(), is(2));
		assertThat(list.toString(), is("[foo, bar]"));
	}

	@Test
	public void testRemoveIterator() {
		List<String> list = makelist("foo", "bar", "baz");
		for (Iterator<String> it = list.iterator(); it.hasNext();) {
			if (it.next().equals("bar")) {
				it.remove();
			}
		}
		assertThat(list.size(), is(2));
		assertThat(list.toString(), is("[foo, baz]"));
	}
	
	@Test
	public void testRemoveHeadWithIterator() {
		List<String> list = makelist("foo", "bar", "baz");
		for (Iterator<String> it = list.iterator(); it.hasNext();) {
			if (it.next().equals("foo")) {
				it.remove();
			}
		}
		assertThat(list.size(), is(2));
		assertThat(list.toString(), is("[bar, baz]"));
	}
	
	@Test
	public void testRemoveTailWithIterator() {
		List<String> list = makelist("foo", "bar", "baz");
		for (Iterator<String> it = list.iterator(); it.hasNext();) {
			if (it.next().equals("baz")) {
				it.remove();
			}
		}
		assertThat(list.size(), is(2));
		assertThat(list.toString(), is("[foo, bar]"));
	}
	
	@Test
	public void testRemoveMultipleWithIterator() {
		List<String> list = makelist("foo", "bar", "bar", "baz");
		for (Iterator<String> it = list.iterator(); it.hasNext();) {
			String s = it.next();
			if (s.equals("bar") || s.equals("baz")) {
				it.remove();
			}
		}
		assertThat(list.size(), is(1));
		assertThat(list.toString(), is("[foo]"));
	}
	
	@Test
	public void testSubList() {
		List<String> list = makelist("foo", "bar", "bar", "hello", "baz");
		List<String> sublist = list.subList(2,4);
		
		assertThat(sublist.size(), is(2));
		assertThat(sublist.toString(), is("[bar, hello]"));
	}
	
	@Test
	public void testLastIndexOf() {
		List<String> list = makelist("foo", "bar", "bar", "hello", "baz");
		
		assertThat(list.lastIndexOf("foo"), is(0));
		assertThat(list.lastIndexOf("bar"), is(2));
		assertThat(list.lastIndexOf("hello"), is(3));
		assertThat(list.lastIndexOf("baz"), is(4));
		
		assertThat(list.lastIndexOf("nonexistent"), is(-1));
		assertThat(list.lastIndexOf(null), is(-1));
	}

	@Test
	public void testIndexOf() {
		List<String> list = makelist("foo", "bar", "bar", "hello", "baz");
		
		assertThat(list.indexOf("foo"), is(0));
		assertThat(list.indexOf("bar"), is(1));
		assertThat(list.indexOf("hello"), is(3));
		assertThat(list.indexOf("baz"), is(4));
		
		assertThat(list.indexOf("nonexistent"), is(-1));
		assertThat(list.indexOf(null), is(-1));
	}

	
	@Test
	public void testGet() {
		List<String> list = makelist("foo", "bar", "bar", "hello", "baz");
		
		assertThat(list.get(0), is("foo"));
		assertThat(list.get(1), is("bar"));
		assertThat(list.get(2), is("bar"));
		assertThat(list.get(3), is("hello"));
		assertThat(list.get(4), is("baz"));
			
	}
}
