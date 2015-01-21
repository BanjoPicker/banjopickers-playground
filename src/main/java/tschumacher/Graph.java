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
    protected final Set<Node<T>> children;
    
    public Node(T t) {
      children = new HashSet<Node<T>>();
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

    protected static final int UNKNOWN = 0;
    protected static final int DISCOVERED = 1;
    protected static final int VISITED = 2;
  };

  public static interface NodeVisitor<T> {
    public void Visit(Node<T> node);
  };

  public Graph() {
    nodes = new HashSet<Node<T>>();
  }

  public synchronized boolean AddNode(T t) {
    return nodes.add(new Node<T>(nullcheck(t)));
  }

  public void dfs(Node<T> root, NodeVisitor visitor) {
    reset(Node.UNKNOWN);
    Stack<Node<T>> stack = new Stack<Node<T>>();
    stack.push(nullcheck(root));
    while (!stack.isEmpty()) {
      Node<T> node = stack.pop();
      if (node.status != Node.VISITED) {
        node.status = Node.VISITED;
        visitor.Visit(node);
        for (Node<T> child : node.children) {
          stack.push(child);
        }
      }
    }
  }

  public void dfs(T root, NodeVisitor visitor) {
    dfs(wrap(root), visitor);
  }

  public void bfs(Node<T> root, NodeVisitor visitor) {
    reset(Node.UNKNOWN);
    List<Node<T>> queue = new LinkedList<Node<T>>();
    queue.add(nullcheck(root));
    while (!queue.isEmpty()) {
      Node<T> node = queue.remove(0);
      visitor.Visit(node);
      node.status = Node.VISITED;
      for (Node<T> child : node.children) {
        if (child.status == Node.UNKNOWN) {
          child.status = Node.DISCOVERED;
          queue.add(child);
        }
      }
    }
  }

  public void bfs(T root, NodeVisitor visitor) {
    bfs(wrap(root), visitor);
  }

  public synchronized boolean AddEdge(T from, T to) {
    nodes.add(new Node<T>(nullcheck(from)));
    nodes.add(new Node<T>(nullcheck(to)));
    final Node<T> fromNode = nullcheck(GetNode(from));
    final Node<T> toNode = nullcheck(GetNode(to));
    return fromNode.children.add(toNode);
  }

  public synchronized List<Node<T>> TopologicalSort() {
    reset(0);
    for (Node<T> node : nodes)
      for (Node<T> child : node.children)
        child.status++;  // incoming edge counter 

    List<Node<T>> ready_nodes = new LinkedList<Node<T>>();
    for (Node<T> node : nodes)
      if (node.status == 0)
        ready_nodes.add(node);  // no prerequisites

    List<Node<T>> result = new LinkedList<Node<T>>();
    while (!ready_nodes.isEmpty()) {
      Node<T> ready_node = ready_nodes.remove(0);
      result.add(ready_node);
      for (Node<T> child : ready_node.children) {
        child.status--;
        if (child.status == 0) {
          ready_nodes.add(child);
        }
      }
    }
    
    for (Node<T> node : nodes)
      if (node.status > 0)
        return null;  // means loop present

    return result;
  }

  public synchronized List<? extends Set<T>> CoffmanGraham() {
    LinkedList<HashSet<T>> result = new LinkedList<HashSet<T>>();
    return result;
  }
  
  public synchronized Collection<Node<T>> getAncestors(Node<T> child) {
    List<Node<T>> ancestors = new LinkedList<Node<T>>();
    for (Node<T> node : nodes) {
      if (node.children.contains(child)) {
        ancestors.add(node);
      }
    }
    return ancestors;
  }

  public synchronized int size() {
    return nodes.size();
  }

  public synchronized Node<T> GetNode(T t) {
    // TODO need to figure out how to use hash(t) to find it
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
    reset(Node.UNKNOWN);
    Set<T> result = new HashSet<T>();
    List<Node<T>> queue = new LinkedList<Node<T>>();
    queue.addAll(wrap(nodes));
    while (!queue.isEmpty()) {
      Node<T> node = queue.remove(0);
      node.status = Node.VISITED;
      for (Node<T> child : node.children) {
        if (child.status == Node.UNKNOWN) {
          child.status = Node.DISCOVERED;
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
      for (Node<T> child : node.children) {
        result.append(child.toString());
      }  
      result.append("\n");
    }
    return result.toString();
  }

  private synchronized void reset(int i) {
    for (Graph.Node<T> node : nodes) {
      node.status = i;
    }
  }

  private final Set<Node<T>> nodes;

  public static <S> S nullcheck(S s) {
    if (s == null) throw new NullPointerException();
    return s;
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
