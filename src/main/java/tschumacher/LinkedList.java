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

		public Iterator(LinkedList<T> list, Node<T> head) {
			this.list = list;
			this.node = head;
			this.previous = null;
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
			previous = node;
			node = node.next;
			return result;
		}

		public void remove() {
			list.remove(this.previous);
		}
		
		/**
		 * <p>The node that is the next read pointer.</p>
		 */
		Node<T> node;
		Node<T> previous;
		
		/**
		 * <p>Pointer to the list this iterator is iterating over.</p>
		 */
		final LinkedList<T> list;
	}  // class Iterator<T>
	
	public int size() {
		return this.size;
	}

	public boolean isEmpty() {
		return (this.size() == 0);
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
		return new Iterator<T>(this, this.head);
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


	public boolean containsAll(Collection<?> c) {
		for (Object o : c) {
			if (indexOf(o) < 0) {
				return false;
			}
		}
		return true;
	}

	public boolean addAll(Collection<? extends T> c) {
		for (T t : c) {
			this.add(t);
		}
		return !c.isEmpty();
	}

	public boolean addAll(int index, Collection c) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean removeAll(Collection c) {
		return false;
	}

	public boolean retainAll(Collection c) {
		// TODO Auto-generated method stub
		return false;
	}

	public void clear() {
		this.size = 0;
		this.head = null;
		this.tail = null;
	}

	/**
	 * <p>Look up a node by index. This is a linear time search through the nodes.</p>
	 * @param index
	 * @return
	 */
	protected Node<T> getnode(int index) {
		int i = 0;
		Node<T> node = head;
		while (i++ < index && node != null) {
			node = node.next;
		}
		return node;
	}
	
	protected Node<T> getnodebyvalue(Object value) {
		Node<T> node = head;
		while (node != null && !value.equals(node.value)) {
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
		if (index < 0) return null;
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
			if (node == head) this.head = next;
			if (node == tail) this.tail = previous;
			return node.value;
		}
	}
	
	public T remove(int index) {
		return remove(getnode(index));
	}

	public boolean remove(Object o) {
		Node<T> node = getnodebyvalue(o);
		if (node == null)
			return false;
		remove(node);
		return true;
	}

	public int indexOf(Object o) {
		int i = 0;
		for (Node<T> node = head; node != null; node = node.next, ++i) {
			if (o != null && o.equals(node.value)) {
				return i;
			} else if (o == null && node.value == null) {
				return i;
			}
		}
		return -1;
	}

	public int lastIndexOf(Object o) {
		int result = -1;
		int index = 0;
		for (Node<T> node = head; node != null; node = node.next, ++index) {
			if (o != null && o.equals(node.value)) {
				result = index;
			} else if (o == null && node.value == null) {
				result = index;
			}
		}
		return result;
	}

	public ListIterator listIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	public ListIterator listIterator(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<T> subList(int fromIndex, int toIndex) {
		LinkedList<T> result = new LinkedList<T>();
		Node<T> node = getnode(fromIndex);
		while (node != null && fromIndex++ < toIndex) {
			result.add(node.value);
			node = node.next;
		}
		return result;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (Iterator<T> it = this.iterator();  it.hasNext();) {
			T value = it.next();
			sb.append(value.toString());
			if (it.hasNext()) sb.append(", ");
		}
		sb.append("]");
		return sb.toString();
	}
	
	private Node<T> head;
	private Node<T> tail;
	private int size;
}
