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
import java.util.Stack;

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
      return String.format("Edge[%s,%s]", from.toString(), to.toString());
    }
  };

  public static interface NodeVisitor<T> {
    public void Visit(Node<T> node);
  };

  public Graph() {
    edges = new HashSet<Edge<T>>();
    nodes = new HashSet<Node<T>>();
  }

  public synchronized boolean AddNode(T t) {
    return nodes.add(new Node<T>(nullcheck(t)));
  }

  public void dfs(Node<T> root, NodeVisitor visitor) {
    final int kUnknown = 0;
    final int kDiscovered = 1;
    final int kVisited = 2;
    reset(kUnknown);
    Stack<Node<T>> stack = new Stack<Node<T>>();
    stack.push(nullcheck(root));
    while (!stack.isEmpty()) {
      Node<T> node = stack.pop();
      if (node.status != kVisited) {
        node.status = kVisited;
        visitor.Visit(node);
        for (Node<T> child : getChildren(node)) {
          stack.push(child);
        }
      }
    }
  }

  public void dfs(T root, NodeVisitor visitor) {
    dfs(wrap(root), visitor);
  }

  public void bfs(Node<T> root, NodeVisitor visitor) {
    final int kUnknown = 0;
    final int kDiscovered = 1;
    final int kVisited = 2;
    reset(kUnknown);
    List<Node<T>> queue = new LinkedList<Node<T>>();
    queue.add(nullcheck(root));
    while (!queue.isEmpty()) {
      Node<T> node = queue.remove(0);
      visitor.Visit(node);
      node.status = kVisited;
      for (Node<T> child : getChildren(node)) {
        if (child.status == kUnknown) {
          child.status = kDiscovered;
          queue.add(child);
        }
      }
    }
  }

  public void bfs(T root, NodeVisitor visitor) {
    bfs(wrap(root), visitor);
  }

  public synchronized boolean AddEdge(T from, T to) {
    final boolean fromnew = nodes.add(new Node<T>(nullcheck(from)));
    final boolean tonew = nodes.add(new Node<T>(nullcheck(to)));
    final Node<T> fromNode = nullcheck(GetNode(from));
    final Node<T> toNode = nullcheck(GetNode(to));
    if (edges.add(new Edge<T>(fromNode, toNode))) {
      if (tonew) {
        toNode.level = fromNode.level + 1;
      } else {
        if (toNode.level <= fromNode.level) {
          // note this isn't excatly right, the number of levels is not optimal
          int incr = fromNode.level - toNode.level + 1; 
          bfs(toNode, node -> node.level += incr);
        }
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
    Set<Node<T>> children = new HashSet<Node<T>>();
    for (Edge<T> edge : edges) {
      if (edge.from.equals(node))
        children.add(edge.to);
      }
    return children;
  }

  public synchronized Collection<Node<T>> getAncestors(Node<T> node) {
    Set<Node<T>> ancestors = new HashSet<Node<T>>();
    for (Edge<T> edge : edges) {
      if (edge.to.equals(node))
        ancestors.add(edge.from);
      }
    return ancestors;
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

  public static void main(String args[]) {
    Graph<Integer> graph = new Graph<Integer>();

graph.AddEdge(9,11);
graph.AddEdge(13,15);
graph.AddEdge(12,15);
graph.AddEdge(2,5);
graph.AddEdge(5,8);
graph.AddEdge(10,14);
graph.AddEdge(5,9);
graph.AddEdge(1,4);
graph.AddEdge(8,11);
graph.AddEdge(8,13);
graph.AddEdge(9,12);
graph.AddEdge(14,15);
graph.AddEdge(11,15);
graph.AddEdge(3,7);
graph.AddEdge(6,8);
graph.AddEdge(6,10);
graph.AddEdge(7,10);
graph.AddEdge(4,8);
graph.AddEdge(3,6);
graph.AddEdge(6,9);
graph.AddEdge(10,13);

    System.out.println(graph.TopologicalSort());
    System.out.println(graph.CoffmanGraham());

    graph.bfs(6, node -> System.out.println("6 " + node));
    graph.bfs(7, node -> System.out.println("7 " + node));
    graph.dfs(6, node -> System.out.println("6 dfs " + node));

    System.out.println(graph);
  }
};
