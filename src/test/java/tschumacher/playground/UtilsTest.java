/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tschumacher.playground;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
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
}
