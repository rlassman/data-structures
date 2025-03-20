/* Name: Rebecca Lassman
 * File: TwoStacksQueue.java
 * Desc:
 *
 * The main driver program for Assignment 3.
 *
 * This class defines a TwoStacksQueue, which is a queue implemented
 * using two stacks.
 *
 */

public class TwoStacksQueue<E> implements Queue<E> {

    private ArrayStack<E> stack1; //main stack for storing the elements
    private ArrayStack<E> stack2; //stores elements in reverse for dequeue, first, and toString

    /** Constructs an empty queue using the default stack capacity. */
    public TwoStacksQueue() {
	this(ArrayStack.CAPACITY);
    }

    /**
     * Constructs an empty queue with the given stack capacity.
     * @param cap  length of the underlying stacks
     */
    public TwoStacksQueue(int cap) {
	stack1 = new ArrayStack<>(cap);
	stack2 = new ArrayStack<>(cap);
    }

    /**
     * Returns the number of elements in the queue.
     * @return number of elements in the queue
     */
    public int size() {
	return stack1.size(); //ArrayStack's size method
    }

    /**
     * Tests whether the queue is empty.
     * @return true if the queue is empty, false otherwise
     */
    public boolean isEmpty() {
	return stack1.isEmpty(); //ArrayStack's isEmpty method
    }

    /**
     * Inserts an element at the end of the queue.
     * @param e  the element to be inserted
     */
    public void enqueue(E e) {
	stack1.push(e);
    }

    /**
     * Returns, but does not remove, the first element of the queue.
     * @return the first element of the queue (or null if empty)
     */
    public E first() {
	int sz = stack1.size(); //total number of elements in stack
	//pushes all of the elements onto stack2
	for (int i=0; i<sz; i++) {
	    E newest = stack1.pop();
	    stack2.push(newest);
	}
	E target = stack2.top(); //first element added to queue
	//pushes all of the elements back onto stack1
	for (int i=0; i<sz; i++) {
	    E newest = stack2.pop();
	    stack1.push(newest);
	}
	return target;
    }

     /**
      * Removes and returns the first element of the queue.
      * @return element removed (or null if empty)
      */
    public E dequeue() {
	int sz = stack1.size(); //total number of elements in stack
	//pushes all of the elements onto stack2
	for (int i=0; i<sz; i++) {
	    E newest = stack1.pop();
	    stack2.push(newest);
	}
	E target = stack2.pop(); //first element added to queue
	sz = stack2.size(); //number of elements once first element is removed
	//pushes all of the elements back onto stack1
	for (int i=0; i<sz; i++) {
	    E newest = stack2.pop();
	    stack1.push(newest);
	}
	return target;
    }

     /**
      * Produces a string representation of the contents of the queue.
      * (ordered from first to last).
      *
      * @return textual representation of the queue
      */
    public String toString() {
	int sz = stack1.size();
	for (int i=0; i<sz; i++) {
	    E newest = stack1.pop();
	    stack2.push(newest);
	}
	StringBuilder sb = new StringBuilder("(");
	for (int i=0; i<sz; i++) {
	    E newest = stack2.pop();
	    sb.append(newest);
	    if (i < sz-1)
		sb.append(", ");
	    stack1.push(newest);
	}
	sb.append(")");
	return sb.toString();
    } 
}
