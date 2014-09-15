package tschumacher;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.List;
import java.util.LinkedList;
import java.util.Set;

public class Graph<T> {
  
  public Graph() {
    data = new HashMap<T, Set<T>>();
  }
 
  /**
   * @return Number of nodes in this graph.
   */
  public synchronized int size() {
	  return data.size();
  }
  
  /**
   * <p>Add a node to the graph if it was not already present.</p>
   */
  public synchronized void maybeAddNode(T node) {
	if (node == null) return;
    if (!data.containsKey(node)) {
      data.put(node, new HashSet<T>());
    }
  }

  /**
   * <p>Add an edge, and also the nodes if not already present.</p>
   */
  public synchronized void AddEdge(T from, T to) {
    if (from == null || to == null) return;
	maybeAddNode(to);
	maybeAddNode(from);
    data.get(from).add(to);
  }
  
  /**
   * <p>Find all nodes that do not have any outgoing edges.</p>
   */
  public List<T> roots() {
	List<T> l = new LinkedList<T>();
	for (T node : data.keySet()) {
		if (data.get(node).isEmpty()) l.add(node);
	}
	return l; 
  }
  
  /**
   * <p>Produce a topological sort, if possible.</p>
   * @return
   */
  public synchronized List<T> topologicalSort() {
    List<T> result = new LinkedList<T>();
    return result;
  }
  
  /**
   * <p>Find all "reachable" nodes from the set of nodes.</p>
   * @param nodes
   * @return A list of all nodes that are reachable from nodes.
   */
  public synchronized Set<T> TransitiveClosure(Set<T> nodes) {
    Set<T> result = new HashSet<T>();
    List<T> items = new LinkedList<T>();
    if (nodes == null) return result;
    items.addAll(nodes);
    while (!items.isEmpty()) {
      T node = items.remove(0);
      if (result.contains(node)) continue;
      for (T t : data.get(node)) items.add(t);
      result.add(node);
    }
    return result;
  }
  
  /**
   * <p>Return the coffman-graham ordering of the nodes in this graph.</p>
   * @return A list of sets, or levels. Level 0 is the set of elements with no dependencies,
   *         while level 1 is the set of elements with dependencies only from level 0 and in
   *         general, level N consists of elements whose dependencies are contained in the
   *         union of the previous levels. 
   */
  public synchronized List<? extends Set<T>> CoffmanGraham() {
    LinkedList<HashSet<T>> result = new LinkedList<HashSet<T>>();
    return result;
  }
  
  /**
   * <p>Helper to join a collection with a sep.</p>
   * @param collection A collection.
   * @param sep The separator string.
   * @return A string.
   */
  public static <T> String Join(Collection<T> collection, String sep) {
    StringBuffer sb = new StringBuffer();
    if (collection == null || collection.isEmpty()) return sb.toString();
    for (T element : collection) {
    	sb.append(element.toString());
    	sb.append(sep);
    }
    return sb.substring(0, sb.length() - sep.length());
  }
  
  @Override
  public String toString() {
    StringBuffer result = new StringBuffer();
    for (T node : data.keySet()) {
      result.append(node.toString()).append(":");
      result.append("[");
      result.append(Graph.Join(data.get(node), ","));
      result.append("]");
      result.append("\n");
    }
	return result.toString();
  }

  /* map from a node to it's set of adjacent nodes */
  private Map<T, Set<T>> data;

};  // class Graph
