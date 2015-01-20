package tschumacher;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;

public class Graph<T> {
  public static class Node<T> {
    public T value;
    public int status;
    protected int level;
    
    public Node(T t) {
    	this.value = t;
    	this.status = 0;
        this.level = 0;
    }
    
	@Override
	public int hashCode() {
		return Objects.hash(value);
	}
	
	@Override
	public boolean equals(Object other) {
        if (other == null) {
        	return false;
        }
        if (other == this) {
            return true;
        }
        if (other instanceof Graph.Node) {
            Graph.Node<?> n = (Graph.Node<?>)other;
            if (Objects.equals(value, n.value)) {
                return true;
            }
        }
        return false;
	}

    @Override
    public String toString() {
        return String.format("Node[%s,%d,%d]",
                             this.value.toString(),
                             this.status,
                             this.level);
    }
  };

  public static class Edge<T> {
    public Node<T> from;
    public Node<T> to;
    
    public Edge(Node<T> from, Node<T> to) {
    	this.to = to;
    	this.from = from;
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

    @Override
    public String toString() {
        return String.format("Edge[%s,%s]",
                             from.toString(), to.toString())
    }
  };

  public Graph() {
    edges = new HashSet<Edge<T>>();
    nodes = new HashSet<Node<T>>();
  }

  public synchronized boolean AddNode(T t) {
	return nodes.add(new Node<T>(nullcheck(t)));
  }

  public synchronized boolean AddEdge(T from, T to) {
    final boolean tonew = nodes.add(new Node<T>(nullcheck(to)));
    final boolean fromnew = nodes.add(new Node<T>(nullcheck(from)));
    final Node<T> fromNode = nullcheck(GetNode(from));
    final Node<T> toNode = nullcheck(GetNode(to));
    if (edges.add(new Edge<T>(fromNode, toNode))) {
      if (fromnew && tonew) {
        fromNode.level = 0; toNode.level = 1;
      } else if (fromnew && !tonew) {
        fromNode.level = Math.min(0, toNode.level - 1);
      } else if (!fromnew && tonew) {
        toNode.level = fromNode.level+1;
      } else {
        toNode.level = Math.max(toNode.level, fromNode.level + 1);
      }
      return true;
    }
    return false;
  }
  
  public synchronized List<Node<T>> TopologicalSort() {
    List<Node<T>> result = new LinkedList<Node<T>>();
    result.addAll(nodes);  // linear in |nodes|
    Collections.sort(result, (Node<T> a, Node<T> b) -> {
        if (a.level == b.level)
            return a.value.toString().compareTo(b.value.toString());
        else
            return a.level - b.level;
    });  // n log n in |nodes|
    return result;
  }

  public synchronized List<? extends Set<T>> CoffmanGraham() {
    LinkedList<HashSet<T>> result = new LinkedList<HashSet<T>>();
    List<Node<T>> nodes = this.TopologicalSort();
    int level = nodes.get(0).level;
    HashSet<T> s = new HashSet<T>();
    for (Node<T> node : nodes) {
        if (level != node.level) {
            level = node.level;
            result.add(s);
            s = new HashSet<T>();
        }
        s.add(node.value);
    }
    result.add(s);
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

  public synchronized int size() {
    return nodes.size();
  }

  public synchronized Node<T> GetNode(T t) {
    for (Node<T> node : nodes) {
      if (node.value.equals(t)) {
        return node;
      }
    }
    return null;
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
  
  private T unwrap(Node<T> node) {
	  return node.value;
  }

  private Node<T> wrap(T t) {
	  return GetNode(t);
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
  
  @Override
  public String toString() {
    StringBuffer result = new StringBuffer();
    for (Node<T> node : nodes) {
      result.append(node.toString());
      result.append("\n");
    }
    for (Edge<T> edge : edges) {
      result.append(edge.toString());
      result.append("\n");
    }
    return result.toString();
  }

  private synchronized void reset(int i) {
    for (Graph.Node<T> node : nodes) {
      node.status = i;
    }
    for (Edge<T> edge : edges) {
      edge.status = i;
    }
  }

  private final Set<Edge<T>> edges;
  private final Set<Node<T>> nodes;

  /**
   * <p>Helper to do null checks.</p>
   */
  public static <T> T nullcheck(T o) {
      if (o == null) throw new NullPointerException();
      return o;
  }
};
