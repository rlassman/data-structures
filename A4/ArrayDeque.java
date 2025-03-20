/* Name: Rebecca Lassman
 * File: ArrayDeque.java
 * Desc:
 *
 * This class defines an ArrayDeque, which is a queue that can have elements
 * added/removed from both ends of the queue. The elements are stored in an
 * underlying array in a circular fashion.
 *
 */

public class ArrayDeque<E> implements Deque<E> {
    
    public static final int CAPACITY = 1000; //default length of underlying array
    
    private E[] data; //generic array used for storage
    private int f = 0; //index of the first element in the queue
    private int sz = 0; //number of elements in queue

    /** Constructs an empty queue using the default array capacity. */
    public ArrayDeque() {
	this(CAPACITY);
    }

     /**
     * Constructs an empty queue with the given stack capacity.
     * @param cap  length of the underlying stacks
     */
    public ArrayDeque(int cap) {
	data = (E[]) new Object[cap];
    }

     /**
     * Returns the number of elements in the queue.
     * @return number of elements in the queue
     */
    public int size() {
	return sz;
    }

    /**
     * Tests whether the queue is empty.
     * @return true if the queue is empty, false otherwise
     */
    public boolean isEmpty() {
	return sz == 0;
    }

     /**
     * Returns, but does not remove, the first element of the queue.
     * @return the first element of the queue (or null if empty)
     */
    public E first() {
	if (isEmpty())
	    return null;
	else
	    return data[f];
    }

     /**
     * Returns, but does not remove, the last element of the queue.
     * @return the last element of the queue (or null if empty)
     */
    public E last() {
	if (isEmpty())
	    return null;
	else
	    return data[(f+sz-1) % data.length];
    }

    /**
     * Inserts an element at the front of the queue.
     * @param e  the element to be inserted
     */
    public void addFirst(E e) throws IllegalStateException {
	if(sz == data.length)
	    throw new IllegalStateException("Queue is full");
	else {
	    data[(f-1+data.length) % data.length] = e; //data.length added so index is not negative
	    f = (f-1+data.length) % data.length;
	    sz++;
	}
    }

    /**
     * Inserts an element at the end of the queue.
     * @param e  the element to be inserted
     */
    public void addLast(E e) {
	if(sz == data.length)
	    throw new IllegalStateException("Queue is full");
	else {
	    data[(f+sz) % data.length] = e;
	    sz++;
	}
    }

     /**
      * Removes and returns the first element of the queue.
      * @return element removed (or null if empty)
      */
    public E removeFirst() {
	if (isEmpty())
	    return null;
	E target = data[f];
	data[f] = null;
	f = (f+1)%data.length;
	sz--;
	return target;
    }

    /**
      * Removes and returns the last element of the queue.
      * @return element removed (or null if empty)
      */
    public E removeLast() {
	if (isEmpty())
	    return null;
	E target = this.last();
	data[(f+sz-1) % data.length] = null;
	sz--;
	return target;
    }

    /**
      * Produces a string representation of the contents of the queue.
      * (ordered from first to last).
      *
      * @return textual representation of the queue
      */
    public String toString() {
	StringBuilder sb = new StringBuilder("(");
	for (int i=0; i<sz; i++) { 
	    sb.append(data[(f+i)%data.length]);
	    if (i<sz-1)
		sb.append(", ");
       	}
	sb.append(")");
	return sb.toString();
    }
}
