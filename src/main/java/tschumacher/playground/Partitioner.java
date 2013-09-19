/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tschumacher.playground;

import java.util.Collection;

/**
 *
 * @author Timothy Schumacher, Ph.D. <schumact@gmail.com>
 */
public interface Partitioner<T> {

	public Collection<Collection<T>> partition(Collection<T> collection);
	
}
