package tschumacher;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;

public class LinkedListTest {

	public static String str(Object o) {
		if (o == null) return "null";
		return o.toString();
	}
	
	@Test
	public void testToArray() {
		tschumacher.LinkedList<String> list = new LinkedList<String>();
		
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
		tschumacher.LinkedList<String> list = new LinkedList<String>();
		assertThat(list.toString(), is("[]"));
		
		list.add("foo");
		assertThat(list.toString(), is("[foo]"));
		
		list.add("bar");
		assertThat(list.toString(), is("[foo,bar]"));
		
		list.add("baz");
		assertThat(list.toString(), is("[foo,bar,baz]"));
		
		list.remove(1);
		assertThat(list.toString(), is("[foo,baz]"));
		
		list.add("foo");
		assertThat(list.toString(), is("[foo,baz,foo]"));

		list.remove("foo");
		assertThat(list.toString(), is("[baz,foo]"));
	}
	
	@Test
	public void testIndexOf() {
		LinkedList<String> list = new LinkedList<String>();
		
		assertTrue(list.indexOf(null) == -1);
		assertTrue(list.indexOf("test") == -1);
		
		list.add("foo");
		assertTrue(list.indexOf("foo") == 0);
		
		list.add("foo");
		assertTrue(list.indexOf("foo") == 0);
		
		list.add("bar");
		assertTrue(list.indexOf("bar") == 1);
	}
}
