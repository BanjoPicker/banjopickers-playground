package tschumacher;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;

public class Graph<T> {
	public static class Node<T> implements Comparable<Node<T>> {
		public T value;
		public int status;
		private final Graph<T> graph;
		private final List<Node<T>> ancestors;
		private final List<Node<T>> children;

		public Node(final Graph<T> graph, T t) {
			Objects.requireNonNull(t, "Node::Node() null t");
			this.graph = graph;
			this.value = t;
			this.status = 0;
			this.ancestors = new ArrayList<Node<T>>();
			this.children = new ArrayList<Node<T>>();
		}

		@Override
		public int hashCode() {
			return Objects.hashCode(value);
		}

		@Override
		public boolean equals(Object other) {
			if (other == null)
				return false;
			if (other == this)
				return true;
			if (other instanceof Graph.Node) {
				Graph.Node<?> n = (Graph.Node<?>) other;
				if (Objects.equals(value, n.value))
					return true;
			}
			return false;
		}

		public int compareTo(Node<T> o) {
			Objects.requireNonNull(o, "Node::compareTo()");
			try {
				return ((Comparable<T>) value).compareTo(o.value);
			} catch (Throwable t) {
				return this.hashCode() - o.hashCode();
			}
		}
		public String toString() {
			return value.toString();
		}
	};

	public static class Edge<T> {
		public Node<T> from;
		public Node<T> to;
		//public double weight;
		//public int status;

		public Edge(Node<T> from, Node<T> to) {
			Objects.requireNonNull(from);
			Objects.requireNonNull(to);
			this.to = to;
			this.from = from;
			//this.status = 0;
			//this.weight = 0.0;
		}

		@Override
		public int hashCode() {
			return Objects.hashCode(from) ^ Objects.hashCode(to);
		}

		@Override
		public boolean equals(Object other) {
			if (other == null)
				return false;
			if (other == this)
				return true;
			if (other instanceof Graph.Edge) {
				Graph.Edge<?> e = (Graph.Edge<?>) other;
				if (Objects.equals(to, e.to) && Objects.equals(from, e.from))
					return true;
			}
			return false;
		}
	}

	public Graph() {
		edges = new HashSet<Edge<T>>();
		nodes = new HashSet<Node<T>>();
		nodemap = new HashMap<T, Node<T>>();
	}

	/**
	 * @return Number of nodes in this graph.
	 */
	public synchronized int size() {
		return nodes.size();
	}

	/**
	 * <p>
	 * Add a node to the graph if it was not already present.
	 * </p>
	 */
	public synchronized void maybeAddNode(T t) {
		if (t != null) {
			Node<T> node = nodemap.get(t);
			if (node == null) {
				node = new Node<T>(this, t);
				nodes.add(node);
				nodemap.put(t, node);
			}
		}
	}

	public synchronized Node<T> getNode(T t) {
		return nodemap.get(t);
	}

	/**
	 * <p>
	 * Add an edge, and also the nodes if not already present.
	 * </p>
	 */
	public synchronized void AddEdge(T from, T to) {
		if (from == null || to == null || to.equals(from))
			return;
		maybeAddNode(to);
		maybeAddNode(from);
		Edge<T> edge = new Edge<T>(getNode(from), getNode(to));
		getNode(from).children.add(getNode(to));
		edges.add(edge);
	}

	public synchronized void AddEdges(T from, T... to) {
		if (from == null || to == null || to.length == 0) {
			return;
		}
		for (T child : to) {
			AddEdge(from, child);
		}
	}

	/**
	 * <p>
	 * Produce a topological sort, if possible.
	 * </p>
	 * 
	 * @return
	 */
	public synchronized List<T> TopologicalSort() {
		reset(UNKNOWN);  // O(|V|+|E|)
		List<T> result = new LinkedList<T>();
		Deque<Node<T>> stack = new ArrayDeque<Graph.Node<T>>();
		stack.addAll(nodes);
		while (!stack.isEmpty()) {
			Node<T> node = stack.peek();
			if (node.status == VISITED) { stack.pop(); continue; }
			node.status = DISCOVERED;
			boolean children_discovered = true;
			for (Node<T> child : node.children) {
				if (child.status == UNKNOWN) {
					child.status = DISCOVERED;
					stack.push(child);
					children_discovered = false;
				}
			}
			if (children_discovered) {
				node.status = VISITED;
				stack.pop();
				result.add(0, node.value);
			}
		}
		return result;
	}

	/**
	 * <p>
	 * Find all "reachable" nodes from the set of nodes.
	 * </p>
	 * 
	 * @param nodes
	 * @return A list of all nodes that are reachable from nodes.
	 */
	public synchronized Collection<T> TransitiveClosure(Collection<T> nodes) {
		Objects.requireNonNull(nodes);
		reset(UNKNOWN);  // O(|V|+|E|)
		List<T> result = new ArrayList<T>(nodes.size() * 3);
		Queue<Node<T>> q = new LinkedList<Node<T>>();
		for (T t : nodes) {
			Objects.requireNonNull(t);
			Node<T> n = nodemap.get(t);
			q.offer(Objects.requireNonNull(n));
		}
		while (!q.isEmpty()) {
			Node<T> node = q.poll();
			result.add(node.value);
			node.status = VISITED; // mark as visited
			for (Node<T> child : node.children) {
				if (child.status == UNKNOWN) {
					child.status = DISCOVERED; // mark as discovered
					q.offer(child);
				}
			}
		}
		return result;
	}

	private synchronized T unwrap(Node<T> node) {
		return node.value;
	}

	protected synchronized Node<T> wrap(T t) {
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
			result.add(nodemap.get(t));
		}
		return result;
	}

	/**
	 * <p>
	 * Return the coffman-graham ordering of the nodes in this graph.
	 * </p>
	 * 
	 * @return A list of sets, or levels. Level 0 is the set of elements with no
	 *         dependencies, while level 1 is the set of elements with
	 *         dependencies only from level 0 and in general, level N consists
	 *         of elements whose dependencies are contained in the union of the
	 *         previous levels.
	 */
	public synchronized List<? extends Collection<T>> CoffmanGraham() {
		reset(0);  // reset all nodes and edges to 0
		List<? extends Collection<T>> result = new ArrayList<HashSet<T>>();
		Collection<Node<T>> q = new ArrayList<Node<T>>(nodes);
		return result;
	}

	/**
	 * <p>
	 * Helper to join a collection with a sep.
	 * </p>
	 * 
	 * @param collection
	 *            A collection.
	 * @param sep
	 *            The separator string.
	 * @return A string.
	 */
	public static <T> String Join(Collection<T> collection, String sep) {
		StringBuffer sb = new StringBuffer();
		if (collection == null || collection.isEmpty())
			return sb.toString();
		for (T element : collection) {
			sb.append(element.toString());
			sb.append(sep);
		}
		return sb.substring(0, sb.length() - sep.length());
	}

	@Override
	public String toString() {
		StringBuffer result = new StringBuffer();
		synchronized (this) {
			for (Edge<T> edge : edges) {
				result.append(edge.from.value);
				result.append(",");
				result.append(edge.to.value);
				result.append("\n");
			}
		}
		return result.toString();
	}
	
	public void prettyString() {
		for (Node<T> node : nodes) {
			System.out.print(node.value);
			System.out.print(" -> ");
			Collections.sort(node.children);
			for (Node<T> child : node.children) {
				System.out.print(child.value);
				System.out.print(" ");
			}
			System.out.println(" ");
		}
	}

	/**
	 * <p>Reset the node stats to proper state.</p>
	 * @param i
	 */
	private synchronized void reset(int i) {
		for (Graph.Node<T> node : nodes) {
			node.status = i;
			node.ancestors.clear();
			node.children.clear();
		}
		for (Edge<T> edge : edges) {
			//edge.status = i;
			edge.from.children.add(edge.to);
			edge.to.ancestors.add(edge.from);
		}
	}
	
	private static final int UNKNOWN = 0;
	private static final int DISCOVERED = 1;
	private static final int VISITED = 2;

	protected Set<Edge<T>> edges;
	protected Set<Node<T>> nodes;
	protected Map<T, Node<T>> nodemap;
}; // class Graph
