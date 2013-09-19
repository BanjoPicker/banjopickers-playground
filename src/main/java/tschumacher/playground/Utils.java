package tschumacher.playground;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 *
 * @author Timothy Schumacher, Ph.D. <schumact@gmail.com>
 */
public final class Utils {

	public static <T> Collection<T> filter(Collection<T> collection, Predicate<T> predicate) {
		Collection<T> result = new ArrayList<T>();
		for(T t : collection) {
			if(predicate.accept(t)) {
				result.add(t);
			}
		}		
		return result;
	}

	/**
	 * 	<p>Apply f:A-->B to a collection and return the collection that results.</p>
	 */
	public static <A,B> Collection<B> map(Collection<A> collection, Mapper<A,B> mapper) {
		ArrayList<B> result = new ArrayList<B>();
		for(A a : collection) {
			result.add(mapper.apply(a));
		}
		return result;
	}

	/**
	 * 	<p>Convert an array to a string format, with a specified separator.</p>
	 * 	@param <T>
	 * 	@param separator
	 * 	@param values
	 * 	@return 
	 */
	public static <T> String join(String separator, T... values) {
		if(values.length == 0) {
			return "";
		} 

		StringBuilder sb = new StringBuilder(values[0].toString());
		for(int i=1;i<values.length;i++) {
			sb.append(separator).append(values[i].toString());
		}
		return sb.toString();
	}

	public static <T> String join(String separator, Collection<T> collection) {
		if(collection == null || collection.isEmpty()) {
			return "";
		}

		Iterator<T> iter = collection.iterator();
		StringBuilder sb = new StringBuilder(iter.next().toString());
		while(iter.hasNext()) {
			sb.append(separator).append(iter.next().toString());
		}
		return sb.toString();
	}

	private Utils() {/* no instances */}
}
