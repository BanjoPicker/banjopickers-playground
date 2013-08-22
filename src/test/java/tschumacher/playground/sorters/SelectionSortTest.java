package tschumacher.playground.sorters;

import junit.framework.TestCase;
import org.junit.Test;
import tschumacher.playground.ArraySorter;

/**
 *
 * @author Timothy Schumacher, Ph.D. <schumact@gmail.com>
 */
public class SelectionSortTest extends TestCase {

	ArraySorter as = new TimedSorter(new SelectionSort());
	
	public SelectionSortTest(String testName) {
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
	 * Test of sort method, of class SelectionSort.
	 */
	public void testSort() {
		SortTestUtils.testSort(as);
	}

	@Test
	public void testBestCase() {
		System.out.println("testBestCase");
		int[] arr = new int[MAX_SIZE];
		for(int i = 0; i < arr.length; i++) {
			arr[i] = i;
		}
		as.sort(arr);
		assertTrue(SortTestUtils.isSorted(arr));
	}

	@Test
	public void testWorstCase() {
		System.out.println("testWorstCase");
		int[] arr = new int[MAX_SIZE];
		for(int i = 0; i < arr.length; i++) {
			arr[i] = arr.length - i;
		}
		as.sort(arr);
		assertTrue(SortTestUtils.isSorted(arr));
	}

	@Test
	public void testNullArray() {
		as.sort(null);
	}
	
	@Test
	public void testEmptyArray() {
		int[] arr = new int[0];
		as.sort(arr);
		assertTrue(SortTestUtils.isSorted(arr));
	}

	@Test
	public void testOneElement() {
		int[] arr = {2};
		as.sort(arr);
		assertTrue(SortTestUtils.isSorted(arr));
	}

	final static int MAX_SIZE = 100000;
}
