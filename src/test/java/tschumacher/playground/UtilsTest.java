/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tschumacher.playground;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static tschumacher.playground.Utils.join;
import static org.junit.Assert.*;

/**
 *
 * @author Timothy Schumacher, Ph.D. <schumact@gmail.com>
 */
public class UtilsTest {
	
	public UtilsTest() {
	}
	
	@BeforeClass
	public static void setUpClass() {
	}
	
	@AfterClass
	public static void tearDownClass() {
	}
	
	@Before
	public void setUp() {
	}
	
	@After
	public void tearDown() {
	}

	/**
	 * Test of filter method, of class Utils.
	 */
	@Test
	public void testFilter() {
		List<Integer> list = new ArrayList<Integer>();
		
		for(int i=0;i<10;i++) {
			list.add(i);
		}
		Collection<Integer> filteredList = Utils.filter(list, new Predicate<Integer>(){ public boolean accept(Integer t) { return((t%2)==0); } });
		for(Integer i : filteredList) {
			System.out.println(i);
		}	
	}
	
	/**
	 * Test of map method, of class Utils.
	 */
	@Test
	public void mapFilter() {
		List<Integer> list = new ArrayList<Integer>();
		
		for(int i=0;i<10;i++) {
			list.add(i);
		}
		Collection<String> mappedList = Utils.map(list, new Mapper<Integer, String>() {
			public String apply(Integer a) { return String.format("0x%08x", a); }
		});
		for(String s : mappedList) {
			System.out.println(s);
		}	
	}

	@Test
	public void joinTest() {
		System.out.println( join(",", 5,10,15) );
		System.out.println( join(",", "foo", "bar", "baz") );
		
		List<Integer> asList = Arrays.asList(5,6,7,8,9);
		System.out.println( "[" + join(",", asList) + "]");
	}
}
