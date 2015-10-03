package tschumacher;

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

public class RBTree<T> {
  public static class Node<T> {
    public T value;
    public int color;
    public int status;
    public Node<T> left;
    public Node<T> right;
    
    public Node(T t) {
      value = t;
      left = null;
      right = null;
      status = 0;
    }
    
    @Override
    public boolean equals(Object other) {
      if (other == null) {
        return false;
      }
      if (other == this) {
        return true;
      }
      if (other instanceof RBTree.Node) {
        RBTree.Node<?> n = (RBTree.Node<?>)other;
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

    protected static final int RED = 0;
    protected static final int BLACK = 1;

    protected static final int UNKNOWN = 0;
    protected static final int DISCOVERED = 1;
    protected static final int VISITED = 2;
  };

  public static interface NodeVisitor<T> {
    public void visit(Node<T> value);
  };

  public RBTree() {
    root = null;
  }

  public void bfs(NodeVisitor visitor) {
    if (root == null) return;
    List<Node<T>> queue = new LinkedList<Node<T>>();
    queue.add(root);
    while (!queue.isEmpty()) {
      Node<T> node = queue.remove(0);
      visitor.visit(node);
      if (node.left != null) queue.add(node.left);
      if (node.right != null) queue.add(node.right);
    }
  }

  public Graph<T> AsGraph() {
    Graph<T> result = new Graph<T>();
    bfs(node -> {
      result.maybeAddNode((T) node.value);
      if (node.left != null) result.AddEdge((T) node.value, (T) node.left.value);
      if (node.right != null) result.AddEdge((T) node.value, (T) node.right.value);
    });
    return result;
  }

  public void preorder(NodeVisitor visitor) {
    if (root == null) return;
    Node<T> node = root;
    Stack<Node<T>> stack = new Stack<Node<T>>();
    while (!stack.isEmpty() || node != null) {
      if (node == null) {
        node = stack.pop();
      } else {
        visitor.visit(node);
        if (node.right != null) stack.push(node.right);
        node = node.left;
      }
    }
  }

  public void inorder(NodeVisitor visitor) {
    if (root == null) return;
    Node<T> node = root;
    Stack<Node<T>> stack = new Stack<Node<T>>();
    while (!stack.isEmpty() || node != null) {
      if (node == null) {
        node = stack.pop();
        visitor.visit(node);
        node = node.right;
      } else {
        stack.push(node);
        node = node.left;
      }
    }
  }

  private void postorder(Node<T> node, NodeVisitor visitor) {
    if (node == null) return;
    postorder(node.left, visitor);
    postorder(node.right, visitor);
    visitor.visit(node);
  }

  public void postorder(NodeVisitor visitor) {
    postorder(root, visitor);
  }

  private int compare(T x, T y) {
    return x.toString().compareTo(y.toString());
  }

  public synchronized boolean add(T value) {
    if (root == null) {
      root = new Node<T>(nullcheck(value));
      return true;
    }

    Node<T> node = root;
    while (!Objects.equals(node.value, value)) {
      if (compare(value, node.value) < 0 && node.left != null) { node = node.left; continue; }
      if (compare(value, node.value) > 0 && node.right != null) { node = node.right; continue; }
      if (compare(value, node.value) < 0 && node.left == null) { node.left = new Node<T>(value); return true; }
      if (compare(value, node.value) > 0 && node.right == null) { node.right = new Node<T>(value); return true; }
    }
    return false;
  }

  private T unwrap(Node<T> node) {
    return node.value;
  }

  private Node<T> wrap(T t) {
    return null;
  }

  @Override
  public String toString() {
    StringBuffer result = new StringBuffer();
    List<Node<T>> queue = new LinkedList<Node<T>>();
    inorder(x -> result.append(x.toString() + "\n"));
    return result.toString();
  }

  private Node<T> root;

  public static <S> S nullcheck(S s) {
    if (s == null) throw new NullPointerException();
    return s;
  }

  public static void main(String args[]) {
    RBTree<String> tree = new RBTree<String>();
    tree.add("b");
    tree.add("a");
    tree.add("c");
    tree.add("d");
    tree.add("e");
    tree.add("a");
    tree.add("g");
    tree.add("f");
    tree.add("g");
    tree.bfs(x -> System.out.println(x));
    System.out.println("");
    tree.preorder(x -> System.out.println(x));

    System.out.println("");
    tree.inorder(x -> System.out.println(x));
    System.out.println("");
    tree.postorder(x -> System.out.println(x));
    System.out.println("");
    Graph<String> g = new Graph<String>();
    // tree.inorder(node -> {g.AddEdge(node.value, node.left.value); g.AddEdge(node.value, node.right.value); });

    for (Collection<String> level : tree.AsGraph().CoffmanGraham()) {
      System.out.println(level);
    }
  }
};
