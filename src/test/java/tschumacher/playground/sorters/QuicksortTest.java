package tschumacher.playground.sorters;

import junit.framework.TestCase;
import org.junit.Test;
import tschumacher.playground.ArraySorter;

/**
 *
 * @author Timothy Schumacher, Ph.D. <schumact@gmail.com>
 */
public class QuicksortTest extends TestCase {
	
	public QuicksortTest(String testName) {
		super(testName);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test of sort method, of class Quicksort.
	 */
	public void testSort() {
		ArraySorter as = new TimedSorter(new Quicksort());
		SortTestUtils.testSort(as);
	}

	@Test
	public void testNullArray() {
		ArraySorter as = new Quicksort();
		as.sort(null);
	}
	
	@Test
	public void testEmptyArray() {
		int[] arr = new int[0];
		ArraySorter as = new Quicksort();
		as.sort(arr);
		assertTrue(SortTestUtils.isSorted(arr));
	}

	@Test
	public void testOneElement() {
		int[] arr = {2};
		ArraySorter as = new Quicksort();
		as.sort(arr);
		assertTrue(SortTestUtils.isSorted(arr));
	}

	@Test
	public void testTenElements() {
		int[] arr = {1,2,3,4,5,6,7,8,9,10};
		int[] prm = SortTestUtils.permute(arr);
		ArraySorter as = new Quicksort();
		System.out.println("arr: " + SortTestUtils.dumpArray(arr));
		System.out.println("prm: " + SortTestUtils.dumpArray(prm));
		as.sort(prm);
		System.out.println("prm: " + SortTestUtils.dumpArray(prm));
		assertTrue(SortTestUtils.isSorted(arr));
		assertTrue(SortTestUtils.compareArrays(arr, prm));
	}

	final static int MAX_SIZE = 100000;
}
