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

	private Utils() {/* no instances */}
}
