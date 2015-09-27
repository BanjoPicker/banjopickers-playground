package tschumacher;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class LinkedList<T> implements List<T> {

	/**
	 * <p>Nodes in the linked list.</p>
	 * @author schumact
	 *
	 * @param <T> The type of elements contained in the list.
	 */
	public static class Node<T> {
		public Node(T value) {
			this.value = value;
			this.next = null;
			this.previous = null;
		}
		
		public T value;
		public Node<T> next;
		public Node<T> previous;
	}  // class Node<T>
	
	public static class Iterator<T> implements java.util.Iterator<T> {

		public Iterator(Node<T> head) {
			this.node = head;
		}
		
		/**
		 * <p>Do we have a next item?</p>
		 */
		public boolean hasNext() {
			return node != null;
		}

		/**
		 * <p>Return the value and increment the node.</p>
		 */
		public T next() {
			T result = node.value;
			node = node.next;
			return result;
		}

		public void remove() {
			Node<T> next = node.next;
			Node<T> previous = node.previous;
			if (previous != null) {
				previous.next = node.next;
				if (node.next != null) {
					node.next.previous = previous;
				}
			}
			node.previous = null;
			node.next = null;
			node = next;
		}
		
		/**
		 * <p>The node that is the next read pointer.</p>
		 */
		Node<T> node;
	}  // class Iterator<T>
	
	public int size() {
		return this.size;
	}

	public boolean isEmpty() {
		return (head == null);
	}

	public boolean contains(Object o) {
		for (T value : this) {
			if (o.equals(value)) {
				return true;
			}
		}
		return false;
	}

	public Iterator<T> iterator() {
		return new Iterator<T>(this.head);
	}

	public Object[] toArray() {
		Object result[]= new Object[size()];
		int i = 0;
		for (T value : this) {
			result[i++] = value;
		}
		return result;
	}

	public Object[] toArray(Object[] a) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean add(T value) {
		Node<T> node = new Node<T>(value);
		size++;
		if (head == null) {
			head = node;
			tail = node;
			return true;
		} else {
			node.previous = tail;
			tail.next = node;
			tail = node;
			return true;
		}
	}

	public boolean remove(Object o) {
		int index = indexOf(o);
		if (index < 0)
			return false;
		remove(index);
		return true;
	}

	public boolean containsAll(Collection c) {
		return false;
	}

	public boolean addAll(Collection c) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean addAll(int index, Collection c) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean removeAll(Collection c) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean retainAll(Collection c) {
		// TODO Auto-generated method stub
		return false;
	}

	public void clear() {
		this.size = 0;
		this.head = null;
	}

	/**
	 * <p>Look up a node by index. This is a linear time search through the nodes.</p>
	 * @param index
	 * @return
	 */
	private Node<T> getnode(int index) {
		int i = 0;
		Node<T> node = head;
		while (i++ < index && node != null) {
			node = node.next;
		}
		return node;
	}
	
	public T get(int i) {
		Node<T> node = getnode(i);
		if (node != null) {
			return node.value;
		}
		return null;
	}

	public T set(int index, T element) {
		Node<T> node = getnode(index);
		T result = null;
		if (node != null) {
			result = node.value;
			node.value = element;
		}
		return result;
	}

	public void add(int index, Object element) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * <p>Remove the given node from the list.</p>
	 * @param node
	 * @return
	 */
	private T remove(Node<T> node) {
		if (node == null) {
			return null;
		} else {
			final Node<T> next = node.next;
			final Node<T> previous = node.previous;
			if (next != null) next.previous = previous;
			if (previous != null) previous.next = next;

			node.previous = null;
			node.next = null;
			size--;
			if (head == node) head = next;
			if (tail == node) tail = previous;
			return node.value;
		}
	}
	
	public T remove(int index) {
		return remove(getnode(index));
	}

	public int indexOf(Object o) {
		int i = 0;
		for (Node<T> node = head; node != null; ++i) {
			if (o.equals(node.value)) { return i; }
		}
		return -1;
	}

	public int lastIndexOf(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

	public ListIterator listIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	public ListIterator listIterator(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> subList(int fromIndex, int toIndex) {
		Node<T> node = getnode(fromIndex);
		if (node == null) {
			return new LinkedList<T>();	
		}
		return new LinkedList<T>();
		
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (Iterator<T> it = this.iterator();  it.hasNext();) {
			T value = it.next();
			sb.append(value.toString());
			if (it.hasNext()) sb.append(",");
		}
		sb.append("]");
		return sb.toString();
	}
	
	private Node<T> head;
	private Node<T> tail;
	private int size;
}
