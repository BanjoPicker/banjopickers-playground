package tschumacher;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.junit.Test;

public class GraphTest {
	public static Graph<Integer> RandomDAG(int size, int outgoingmax) {
		Graph<Integer> graph = new Graph<Integer>();
		Random random = new Random();
		random.setSeed(1977);
		for (int i = 0; i < size; ++i) {
			final int outgoing = 1 + random.nextInt(outgoingmax);
			for (int k = 0; k < outgoing; k++) {
				int j = Math.min(size, i + 1 + random.nextInt(size - i));
				graph.AddEdge(i, j);
				if (i > 10) {
					graph.AddEdge(random.nextInt(i), i);
				}
			}
		}
		System.out.println(graph.nodes.size());
		System.out.println(graph.edges.size());
		//graph.prettyString();
		return graph;
	}
	
	@Test
	public void GraphConstructor() {
		Graph<Integer> graph = new Graph<Integer>();
		assertEquals(0, graph.edges.size());
		assertEquals(0, graph.nodes.size());
		assertEquals(0, graph.nodemap.size());
	}
	
	@Test
	public void AddEdgeTest() {
		Graph<Integer> graph = new Graph<Integer>();
		graph.AddEdge(1, 2);
		assertEquals(1, graph.edges.size());
		assertEquals(2, graph.nodes.size());
		assertEquals(2, graph.nodemap.size());
		
		graph.AddEdge(1, 2);
		assertEquals(1, graph.edges.size());
		assertEquals(2, graph.nodes.size());
		assertEquals(2, graph.nodemap.size());
		
		graph.AddEdge(0, 2);
		assertEquals(2, graph.edges.size());
		assertEquals(3, graph.nodes.size());
		assertEquals(3, graph.nodemap.size());
	}
	
	@Test
	public void NodeEqualsTest() {
		Graph.Node<Integer> n1 = new Graph.Node<Integer>(null, 1);
		Graph.Node<Integer> n2 = new Graph.Node<Integer>(null, 1);
		assertEquals(n1, n2);
	}
	
	@Test
	public void EdgeEqualsTest() {
		Graph<Integer> graph = new Graph<Integer>();
		Graph.Node<Integer> n1 = new Graph.Node<Integer>(graph, 1);
		Graph.Node<Integer> n2 = new Graph.Node<Integer>(graph, 2);
		assertNotEquals(n1, n2);
		
		Graph.Node<Integer> n3 = new Graph.Node<Integer>(graph, 1);
		Graph.Node<Integer> n4 = new Graph.Node<Integer>(graph, 2);
		Graph.Edge<Integer> edge1 = new Graph.Edge<Integer>(n1, n2);
		Graph.Edge<Integer> edge2 = new Graph.Edge<Integer>(n3, n4);
		assertEquals(edge1, edge2);
		
		Graph.Edge<Integer> edge3 = new Graph.Edge<Integer>(n2, n1);
		assertNotEquals(edge1, edge3);
	}
	
	@Test
	public void RandomGraph() {
		System.out.println("Rand: 10\n" + RandomDAG(10, 3).TopologicalSort());
		System.out.println("Rand: 100\n" + RandomDAG(100, 5).TopologicalSort());
		/*
		System.out.println("Rand: 1000\n" + RandomDAG(1000, 20).TopologicalSort());
		System.out.println("Rand: 2000\n" + RandomDAG(2000, 40).TopologicalSort());
		System.out.println("Rand: 3000\n" + RandomDAG(3000, 50).TopologicalSort());
		System.out.println("Rand: 10000\n" + RandomDAG(10000, 100).TopologicalSort());
		 * 
		 */
		//System.out.println("Rand: 100000");
		//Graph<Integer> graph = RandomDAG(500000, 55);
		//System.out.println("before " + System.nanoTime());
		//int c = graph.TopologicalSort().size();
		//System.out.println("after " + System.nanoTime());
		//System.out.println("Done " + c);
		//System.out.println("Rand: 1000000\n" + RandomDAG(1000000, 100).TopologicalSort());
	}

	@Test
	public void TransitiveClosureWithNullReturnsEmptySet() {
		Graph<String> graph = new Graph<String>();
		graph.AddEdge("a", "b");
		graph.AddEdge("a", "c");
		graph.AddEdge("b", "c");
		graph.AddEdge("c", "d");
		graph.AddEdge("d", "a");
		
		System.out.println(graph);
		Graph.Node<String> node = graph.getNode("a");
		assertNotNull(node);
		
		Set<String> s = new HashSet<String>();
		Collection<String> c = graph.TransitiveClosure(s);
		assertNotNull(c);
		assertEquals(0, c.size());
		graph.prettyString();
	}
	
	@Test
	public void TestNumberGraph() {
		Graph<Integer> graph = new Graph<Integer>();
		graph.AddEdges(1, 3, 7, 8 , 9, 11, 12);
		graph.AddEdges(3, 4, 5, 7, 9, 12);
		graph.AddEdges(4, 1, 7, 8, 9, 11);
		
		graph.prettyString();
		System.out.println("Numbers: " + graph);
	}
	
	@Test
	public void TopologicalSortTest() {
		System.out.println("TIM");
		Graph<Integer> graph = new Graph<Integer>();
		graph.AddEdges(3, 8, 10);
		graph.AddEdges(5, 11);
		graph.AddEdges(7, 8, 11);
		graph.AddEdges(8, 9);
		graph.AddEdges(11, 2, 9, 10);
		List<Integer> list = graph.TopologicalSort();
		graph.prettyString();
		System.out.println(list);
		//System.out.println(graph.TransitiveClosure(Arrays.asList(4)));
	}

	@Test
	public void TransitiveClosureBasicFunctionalityTest() {
		Graph<String> graph = new Graph<String>();
		graph.AddEdges("a", "b", "c");  // a -> b, a -> c
		graph.AddEdges("b", "c");
		graph.AddEdges("c", "d");
		graph.AddEdges("e", "a");
		Set<String> s = new HashSet<String>();
		s.add("a");
		Collection<String> c = graph.TransitiveClosure(s);
		System.out.println(graph);
		System.out.println("Transitive Closure: " + Graph.Join(c, ","));
		assertNotNull(c);
		assertEquals(4, c.size());
	}
}
