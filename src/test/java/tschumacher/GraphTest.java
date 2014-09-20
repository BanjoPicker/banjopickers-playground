package tschumacher;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class GraphTest {

	@Test
	public void TransitiveClosureWithNullReturnsEmptySet() {
		Graph<String> graph = new Graph<String>();
		graph.AddEdge("a", "b");
		graph.AddEdge("a", "c");
		graph.AddEdge("b", "c");
		graph.AddEdge("c", "d");
		
		System.out.println(graph);
		
		Set<String> s = new HashSet<String>();
		Collection<String> c = graph.TransitiveClosure(s);
		assertNotNull(c);
		assertEquals(0, c.size());
	}

	@Test
	public void TransitiveClosureBasicFunctionalityTest() {
		Graph<String> graph = new Graph<String>();
		graph.AddEdge("a", "b");
		graph.AddEdge("a", "c");
		graph.AddEdge("b", "c");
		graph.AddEdge("c", "d");
		graph.AddEdge("e", "a");
		graph.AddEdge("d", "a");
		Set<String> s = new HashSet<String>();
		s.add("a");
		Collection<String> c = graph.TransitiveClosure(s);
		System.out.println(Graph.Join(c, ","));
		assertNotNull(c);
		assertEquals(4, c.size());
	}
}
