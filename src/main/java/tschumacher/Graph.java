package tschumacher;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.Random;
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
      return String.format("Node[%s]", this.value.toString());
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

  public synchronized List<? extends Set<Node<T>>> CoffmanGraham() {
    reset(0);
    for (Node<T> node : nodes)
      for (Node<T> child : node.children)
        child.status++;  // incoming edge counter 

    List<Node<T>> ready_nodes = new LinkedList<Node<T>>();
    for (Node<T> node : nodes)
      if (node.status == 0)
        ready_nodes.add(node);  // no prerequisites

    List<HashSet<Node<T>>> result = new LinkedList<HashSet<Node<T>>>();
    while (!ready_nodes.isEmpty()) {
      HashSet<Node<T>> level = new HashSet<Node<T>>();
      level.addAll(ready_nodes);
      result.add(level);
      ready_nodes.clear();
      for (Node<T> node : level) {
        for (Node<T> child : node.children) {
          child.status--;
          if (child.status == 0)
            ready_nodes.add(child);
        }
      }
    }

    for (Node<T> node : nodes)
      if (node.status > 0)
        return null;  // means loop present

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
      result.append(" ");
      result.append(node.children);
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
    Graph<String> graph = new Graph<String>();
    try {
    BufferedReader reader = new BufferedReader(new FileReader(args[0]));
    String line;
    while ((line = reader.readLine()) != null) {
      String[] tokens = line.split(" ");
      if (tokens.length == 1) graph.AddNode(tokens[0]);
      if (tokens.length == 2) graph.AddEdge(tokens[0], tokens[1]);
    }
    } catch (Exception e) { System.out.println("oops " + e); }
    System.out.println("");
    System.out.println(graph.TopologicalSort());
    System.out.println("");

    for (Set<Node<String>> level : graph.CoffmanGraham()) {
      System.out.println(level);
    }
    System.out.println("");
    System.out.println(graph);
  }
};
