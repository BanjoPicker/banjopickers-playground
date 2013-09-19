package tschumacher.playground;

/**
 *
 * @author Timothy Schumacher, Ph.D. <schumact@gmail.com>
 */
public interface Mapper<A,B> {

	public B apply(A a);

}
