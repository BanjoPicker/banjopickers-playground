package tschumacher;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.List;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;

public class Graph<T> {
  public static class Node<T> {
    public T value;
    public int status;
    
    public Node(T t) {
    	this.value = t;
    	this.status = 0;
    }
    
	@Override
	public int hashCode() {
		return Objects.hash(value);
	}
	
	@Override
	public boolean equals(Object other) {
        if (other == null)
        	return false;
		if (other == this)
            return true;
        if (other instanceof Graph.Node) {
            Graph.Node<?> n = (Graph.Node<?>)other;
            if (Objects.equals(value, n.value))
                return true;
        }
        return false;
	}
  };

  public static class Edge<T> {
    public Node<T> from;
    public Node<T> to;
    public double weight;
    public int status;
    
    public Edge(Node<T> from, Node<T> to) {
    	this.to = to;
    	this.from = from;
    	this.status = 0;
    	this.weight = 0.0;
    }
    
    @Override
    public int hashCode() {
      return Objects.hash(from) ^ Objects.hash(to);
    }
    
    @Override
	public boolean equals(Object other) {
      if (other == null)
        return false;
	  if (other == this)
        return true;
      if (other instanceof Graph.Edge) {
        Graph.Edge<?> e = (Graph.Edge<?>)other;
        if (Objects.equals(to, e.to) && Objects.equals(from, e.from))
          return true;
      }
      return false;
	}

  }

  public Graph() {
    edges = new HashSet<Edge<T>>();
    nodes = new HashSet<Node<T>>();
  }
 
  /**
   * @return Number of nodes in this graph.
   */
  public synchronized int size() {
    return nodes.size();
  }
  
  /**
   * <p>Add a node to the graph if it was not already present.</p>
   */
  public synchronized void maybeAddNode(T t) {
	if (t != null) {
	  Node<T> node = new Node<T>(t);
	  nodes.add(node);  // set remains unchanged if already present
	}
  }
  
  public synchronized Node<T> getNode(T t) {
    for (Node<T> node : nodes) {
      if (node.value.equals(t)) {
        return node;
      }
    }
    return null;
  }

  /**
   * <p>Add an edge, and also the nodes if not already present.</p>
   */
  public synchronized void AddEdge(T from, T to) {
    if (from == null || to == null) return;
	maybeAddNode(to);
	maybeAddNode(from);
	Edge<T> edge = new Edge<T>(getNode(from),
			                   getNode(to));
	edges.add(edge);
  }
  
  /**
   * <p>Produce a topological sort, if possible.</p>
   * @return
   */
  public synchronized List<T> topologicalSort() {
    List<T> result = new LinkedList<T>();
    return result;
  }
  
  public synchronized Collection<Node<T>> getChildren(Node<T> node) {
    List<Node<T>> children = new LinkedList<Node<T>>();
	for (Edge<T> edge : edges) {
      if (edge.from.equals(node))
	    children.add(edge.to);
	  }
	return children;
  }
  
  /**
   * <p>Find all "reachable" nodes from the set of nodes.</p>
   * @param nodes
   * @return A list of all nodes that are reachable from nodes.
   */
  public synchronized Collection<T> TransitiveClosure(Collection<T> nodes) {
	final int kUnknown = 0;
	final int kDiscovered = 1;
	final int kVisited = 2;
	reset(kUnknown);  // make sure all statuses are 0 again
    Set<T> result = new HashSet<T>();
    List<Node<T>> queue = new LinkedList<Node<T>>();
    queue.addAll(wrap(nodes));
    while (!queue.isEmpty()) {
    	Node<T> node = queue.remove(0);
    	node.status = kVisited;  // mark as visited
    	for (Node<T> child : getChildren(node)) {
    		if (child.status == kUnknown) {
    			child.status = kDiscovered;  // mark as discovered    		
    			queue.add(child);
    		}
    	}	
    }
    return result;
  }
  
  private synchronized T unwrap(Node<T> node) {
	  return node.value;
  }
  private synchronized Node<T> wrap(T t) {
	  return getNode(t);
  }
  private synchronized Collection<T> unwrap(Collection<Node<T>> c) {
	  LinkedList<T> result = new LinkedList<T>();
	  for (Node<T> node : c) {
		  result.add(unwrap(node));
	  }
	  return result;
  }
  
  private synchronized Collection<Node<T>> wrap(Collection<T> coll) {
	  LinkedList<Node<T>> result = new LinkedList<Node<T>>();
	  for (T t : coll) {
		  result.add(wrap(t));
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
    for (Edge<T> edge : edges) {
      result.append(edge.from.value);
      result.append(",");
      result.append(edge.to.value);
      result.append("\n");
    }
	return result.toString();
  }

  // Internal helper to reset status to i.
  private synchronized void reset(int i) {
    for (Graph.Node<T> node : nodes) {
      node.status = i;
    }
    for (Edge<T> edge : edges) {
      edge.status = i;
    }
  }

  private Set<Edge<T>> edges;
  private Set<Node<T>> nodes;
  
  // convenience to help us do faster lookups.
  //private Map<T, Node<T>> nodemap;
};  // class Graph
