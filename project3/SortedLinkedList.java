package project3;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This is an implementation of a sorted doubly-linked list.
 * All elements in the list are maintained in ascending/increasing order
 * based on the natural order of the elements.
 * This list does not allow <code>null</code> elements.
 *
 * @author Joanna Klukowska
 * @author
 *
 * @param <E> the type of elements held in this list
 */
public class SortedLinkedList<E extends Comparable<E>>
        implements Iterable<E> {

    private Node head;
    private Node tail;
    private int size;

    /**
     * Constructs a new empty sorted linked list.
     */
    public SortedLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Adds the specified element to the list in ascending order.
     *
     * @param element the element to add
     * @return <code>true</code> if the element was added successfully,
     *         <code>false</code> otherwise (if <code>element==null</code>)
     */
    public boolean add(E element) {
        if (element == null)
            return false;
        Node newNode = new Node(element);

        // If the list is empty
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else if (newNode.compareTo(head) < 0) { // New head insertion
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null && current.next.compareTo(newNode) < 0) {
                current = current.next;
            }
            newNode.next = current.next;
            if (current.next != null) {
                current.next.prev = newNode;
            } else { // Inserting at the end
                tail = newNode;
            }
            newNode.prev = current;
            current.next = newNode;
        }

        size++;
        return true;
    }

    /**
     * Removes all elements from the list.
     */
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Returns <code>true</code> if the list contains the specified element,
     * <code>false</code> otherwise.
     *
     * @param o the element to search for
     * @return <code>true</code> if the element is in the list,
     *         <code>false</code> otherwise
     */
    public boolean contains(Object o) {
        if (o == null)
            return false;
        Node current = head;
        while (current != null) {
            if (current.data.equals(o))
                return true;
            current = current.next;
        }
        return false;
    }

    /**
     * Returns the element at the specified index in the list.
     *
     * @param index the index of the element to return
     * @return the element at the specified index
     * @throw IndexOutOfBoundsException if the index is out of
     *        range <code>(index < 0 || index >= size())</code>
     */
    public E get(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException();
        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.data;
    }

    /**
     * Returns the index of the first occurrence of the specified element in the
     * list,
     * or -1 if the element is not in the list.
     *
     * @param o the element to search for
     * @return the index of the first occurrence of the element,
     *         or -1 if the element is not in the list
     */
    public int indexOf(Object o) {
        if (o == null)
            return -1;
        Node current = head;
        int index = 0;
        while (current != null) {
            if (current.data.equals(o))
                return index;
            current = current.next;
            index++;
        }
        return -1;
    }

    /**
     * Returns the index of the first occurrence of the specified element in the
     * list,
     * starting at the specified <code>index</code>, i.e., in the range of indexes
     * <code>index <= i < size()</code>, or -1 if the element is not in the list
     * in the range of indexes <code>index <= i < size()</code>.
     *
     * @param o     the element to search for
     * @param index the index to start searching from
     * @return the index of the first occurrence of the element, starting at the
     *         specified index,
     *         or -1 if the element is not found
     */
    public int nextIndexOf(Object o, int index) {
        if (o == null || index < 0 || index >= size)
            return -1;
        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        while (current != null) {
            if (current.data.equals(o))
                return index;
            current = current.next;
            index++;
        }
        return -1;
    }

    /**
     * Removes the first occurence of the specified element from the list.
     *
     * @param o the element to remove
     * @return <code>true</code> if the element was removed successfully,
     *         <code>false</code> otherwise
     */
    public boolean remove(Object o) {
        if (o == null)
            return false;
        Node current = head;
        while (current != null) {
            if (current.data.equals(o)) {
                if (current.prev != null) {
                    current.prev.next = current.next;
                } else { // Removing the head
                    head = current.next;
                }
                if (current.next != null) {
                    current.next.prev = current.prev;
                } else { // Removing the tail
                    tail = current.prev;
                }
                size--;
                return true;
            }
            current = current.next;
        }
        return false;
    }

    /**
     * Returns the size of the list.
     *
     * @return the size of the list
     */
    public int size() {
        return size;
    }

    /**
     * Returns an iterator over the elements in the list.
     *
     * @return an iterator over the elements in the list
     */
    public Iterator<E> iterator() {
        return new ListIterator();
    }

    /**
     * Compares the specified object with this list for equality.
     *
     * @param o the object to compare with
     * @return <code>true</code> if the specified object is equal to this list,
     *         <code>false</code> otherwise
     */
    public boolean equals(Object o) {
        if (this == o) return true; // Check if the objects are the same instance
        if (o == null || getClass() != o.getClass()) return false; // Check for null and class type
    
        SortedLinkedList<E> other;
        try {
            other = (SortedLinkedList<E>) o; // Cast to a SortedLinkedList of the same type
        } catch (ClassCastException e) {
            return false;
        }
    
        if (this.size != other.size) return false; // Check if sizes are equal
    
        Node currentThis = this.head;
        Node currentOther = other.head;
    
        // Iterate through both lists and compare each element
        while (currentThis != null) {
            if (!currentThis.data.equals(currentOther.data)) {
                return false;
            }
            currentThis = currentThis.next;
            currentOther = currentOther.next;
        }
    
        return true; // All elements matched
    }

    /**
     * Returns a string representation of the list.
     * The string representation consists of a list of the lists's elements in
     * ascending order, enclosed in square brackets ("[]").
     * Adjacent elements are separated by the characters ", " (comma and space).
     *
     * @return a string representation of the list
     */
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node current = head;
        while (current != null) {
            sb.append(current.data);
            if (current.next != null) {
                sb.append(", ");
            }
            current = current.next;
        }
        sb.append("]");
        return sb.toString();
    }

    /* Inner class to represent nodes of this list. */
    private class Node implements Comparable<Node> {
        E data;
        Node next;
        Node prev;

        Node(E data) {
            if (data == null)
                throw new NullPointerException("does not allow null");
            this.data = data;
        }

        Node(E data, Node next, Node prev) {
            this(data);
            this.next = next;
            this.prev = prev;
        }

        public int compareTo(Node n) {
            return this.data.compareTo(n.data);
        }
    }

    /* A basic forward iterator for this list. */
    private class ListIterator implements Iterator<E> {

        Node nextToReturn = head;

        @Override
        public boolean hasNext() {
            return nextToReturn != null;
        }

        @Override
        public E next() throws NoSuchElementException {
            if (nextToReturn == null)
                throw new NoSuchElementException("the end of the list reached");
            E tmp = nextToReturn.data;
            nextToReturn = nextToReturn.next;
            return tmp;
        }

    }
}
