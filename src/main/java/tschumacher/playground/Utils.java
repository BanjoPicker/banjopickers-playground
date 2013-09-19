package tschumacher.playground;

import java.util.ArrayList;
import java.util.Collection;

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

	private Utils() {/* no instances */}
}
