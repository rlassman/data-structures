/* Name: Rebecca Lassman
 * File: ArrayHeap.java
 * Desc:
 *
 * This program defines an array heap, which stores elements that are comparable.
 * It is a min heap, but Candidate's compareTo implementation causes it to function
 * as a max heap for candidates.
 *
 */

import java.util.*;

public class ArrayHeap<E extends Comparable<E>> extends ArrayBinaryTree<E> implements PriorityQueue<E> {
    public static final int BASE = 2; //used to compute heap's layers for printing

    /** Creates an array heap */
    public ArrayHeap() { }

    /** Inserts an element into the heap while maintaining heap order, or updates
     * the element if it is already in the heap
     * @param element The element to be inserted
     */
    public void insert(E element) {
	int i = containsIdx(element); //index of element
	if (i != -1) { //element is already in the heap
	    updateKey(i, element);
	} else { //element is not in the heap
	    super.insert(element);
	    upHeap(element);
	}
    }

    /** Updates the value of an index while maintaining heap order
     * @param i The index of the element being updated
     * @param element The replacement element
     */
    protected void updateKey(int i, E element) {
	data.set(i, element);
	if (parent(i) != -1 && element.compareTo(data.get(parent(i))) < 0)
	    upHeap(element);
	else if (left(i) != -1 && element.compareTo(data.get(left(i))) > 0)
	    downHeap(element);
	else if (right(i) != -1 && element.compareTo(data.get(right(i))) > 0)
	    downHeap(element);
    }

    /** Swaps an element with its parent if it is larger than its parent
     * @param element The element being upheaped
     */
    protected void upHeap(E element) {
        int i = containsIdx(element); //index of element
	int parentIdx = parent(i); //index of i's parent
	while (parentIdx != -1 && data.get(i).compareTo(data.get(parentIdx)) < 0) {
	    swap(i, parentIdx);
	    i = parentIdx;
	    parentIdx = parent(i);
	}
    }

    /** Maintains heap order if an element is smaller than its children
     * @param element The element being downheaped
     */
    protected void downHeap(E element) {
	int i = containsIdx(element); //index of element
	downHeapRec(i);
    }

    /** Recursively swaps an element with its larger child if it is smaller than its children
     * @param i The index of the element being downheaped
     */
    private void downHeapRec(int i) {
	int left = left(i); //index of i's left child
	int right = right(i); //index of i's right child
	if (left != -1 && right != -1) { //i is not leaf
	    if (left == -1) { //i only has a right child
		if (data.get(i).compareTo(data.get(right(i))) > 0)
		    swap(i, right);
	    } else if (right == -1) { //i only has left child
		if (data.get(i).compareTo(data.get(left(i))) > 0)
		    swap(i, left);
	    } else { //i has two children
		int largerChild;
		if (data.get(left).compareTo(data.get(right)) < 0)
		    largerChild = left;
		else
		    largerChild = right;
		if (data.get(i).compareTo(data.get(largerChild)) > 0) {
		    swap(i, largerChild);
		    downHeapRec(largerChild);
		}
	    }
	}
    }

    /** Removes an element from the heap while maintaing heap order
     * @param E The element being removed
     * @return True if the element was found and remove, false otherwise
     */
    public boolean remove(E element) {
	int i = containsIdx(element); //index of element before removal
	boolean removed = super.remove(element);
	if (removed && !isEmpty()) {
	    E target = data.get(i); //previous last element that was swapped into i
	    if (parent(i) != -1 && target.compareTo(data.get(parent(i))) < 0)
		upHeap(target);
	    else if (left(i) != -1 && target.compareTo(data.get(left(i))) > 0)
		downHeap(target);
	    else if (right(i) != -1 && target.compareTo(data.get(right(i))) > 0)
		downHeap(target);
	}
        return removed;
    }

    /** Builds a string that is a breadth first traversal of the heap with
     * new lines after each layer
     * @returns The elements in breadth first traversal order
     */
    public String toString() {
	String s = "";
	int size = 0; //number of elements in a layer
	int power = 0; //corresponds to current heap layer
	for(int i=0; i<data.size(); i++) {
	    s += data.get(i) + " ";
	    size++;
	    //adds a new line after each layer
	    if (Math.pow(BASE, power) == size && i<data.size()-1) {
		s += "\n";
		size = 0;
		power++;
	    }
	}
	return s;
    }

    /** Returns the root element of the heap
     * @return The first element in the heap
     */
    public E peek() {
	return getRootElement();
    }

    /** Removes the root element of the heap
     * @return The first element in the heap
     */
    public E poll() {
	E target = getRootElement();
	remove(target);
	return target;
    }

    /** Finds the top n elements in the heap
     * @param n The number of elements being returned
     * @return An arraylist of the top n elements
     */
    public ArrayList<E> peekTopN(int n) {
	ArrayList<E> heapElements = new ArrayList<E>(); //copy of data
	for (int i=0; i<data.size(); i++) {
	    heapElements.add(data.get(i));
	}
	heapElements.sort(null);
	ArrayList<E> topN = new ArrayList<E>(); //stores top n elements to be returned
	int j = 0; //2nd index to account for ties
	for (int i=0; i<n; i++) {
	    if (j>=heapElements.size()-1) { //avoids out of bounds exception
		break;
	    }
	    topN.add(heapElements.get(j));
	    //if there is a tie
	    while (j<heapElements.size()-1 && heapElements.get(j).compareTo(heapElements.get(j+1)) == 2) {
		topN.add(heapElements.get(j+1));
		j++;
	    }
	    j++;
	}
	return topN;
    }
}
