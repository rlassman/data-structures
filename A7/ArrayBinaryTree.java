/* Name: Rebecca Lassman
 * File: ArrayBinaryTree.java
 * Desc:
 *
 * This program defines a binary tree with an array as the underlying storage, 
 * which stores elements that are comparable.
 *
 */

import java.util.*;

public class ArrayBinaryTree <E extends Comparable<E>> implements BinaryTree<E> {
    public static final int root = 0; //root index
    private int size = 0; //number of elements in the tree
    protected ArrayList<E> data = new ArrayList<>(); //underlying storage of elements

    /** Creates an array binary tree */
    public ArrayBinaryTree() { }

    /** Returns the value of size
     * @return The size of the tree
     */
    public int size() {
	return size;
    }

    /** Checks if the tree is empty
     * @return True if the tree is empty, false otherwise
     */
    public boolean isEmpty() {
	return size == 0;
    }

    /** Returns the first element of the tree
     * @return The root element (or null if there is none)
     */
    public E getRootElement() {
	if (isEmpty())
	    return null;
	else
	    return data.get(root);
    }

    /** Computes the index of the parent from a given index
     * @param i The child index
     * @return The index of the parent, or -1 if there is none
     */
    protected int parent(int i) {
	if (i <= 0)
	    return -1;
	else
	    return (i-1)/2;
    }

    /** Computes the index of the left child from a given index
     * @param i The parent index
     * @return The index of the left child, or -1 if there is none
     */
    protected int left(int i) {
	if (i*2+1 > data.size()-1)
	    return -1;
	else
	    return i*2 + 1;
    }

    /** Computes the index of the right child from a given index
     * @param i The parent index
     * @return The index of the right child, or -1 if there is none
     */
    protected int right(int i) {
	if (i*2+2 > data.size()-1)
	    return -1;
	else
	    return i*2 + 2;
    }

    /** Swaps the values of two elements in the heap
     * @param i The index of the first element
     * @param j The index of the second element
     */
    protected void swap(int i, int j) {
	E temp = data.get(j);
	data.set(j, data.get(i));
	data.set(i, temp);
    }

    /** Finds the index of a given element
     * @param element The element being searched for
     * @return The index of the element, or -1 if it is not in the tree
     */
    protected int containsIdx(E element) {
	for(int i=0; i<data.size(); i++) {
	    if (data.get(i).compareTo(element) == 0) {
		return i;
	    }
	}
	return -1;
    }

    /** Adds an element to the end of the tree
     * @param E The element being inserted
     */
    public void insert(E element) {
	data.add(size, element);
        size ++;
    }

    /** Removes an element from the tree by swapping it with the last element
     * @param E The element being removed
     * @return True if the element was found and removed, false otherwise
     */
    public boolean remove(E element) {
	int index = containsIdx(element);
	if (index == -1)
	    return false;
	else {
	    swap(index, size-1);
	    data.remove(size-1);
	    size--;
	    return true;
	}
    }

    /** Checks if a given element is in the tree
     * @param E The element being searched for
     * @return True if the element was found, false otherwise
     */
    public boolean contains(E element) {
	int index = containsIdx(element);
	if (index == -1)
	    return false;
	else
	    return true;
    }

    /** Constructs a string of a breadth first traversal of the tree
     * @return The elements in order layer by layer
     */
    public String toStringBreadthFirst() {
	String s = "";
	for(int i=0; i<data.size(); i++) {
	    s += data.get(i) + " ";
	}
	return s;
    }

    /** Formats the elements in the tree based on an in order traversal
     * @return A string representing an in order traversal of the tree
     */
    public String toStringInOrder() {
	return toStringInOrderRec(root);
    }

    /** Builds a string using a left subtree, root, right subtree traversal
     * @return A string representing an in order traversal of the tree
     */
    private String toStringInOrderRec(int root) {
	if (root == -1)
	    return "";
	else {
	    String s = "";
	    s += toStringInOrderRec(left(root));
	    s += data.get(root) + " ";
	    s += toStringInOrderRec(right(root));
	    return s;
	}
    }

    /** Formats the elements in the tree based on a pre order traversal
     * @return A string representing a pre order traversal of the tree
     */
    public String toStringPreOrder() {
	return toStringPreOrderRec(root);
    }

    /** Builds a string using a root, left subtree, right subtree traversal
     * @return A string representing a pre order traversal of the tree
     */
    private String toStringPreOrderRec(int root) {
	if (root == -1)
	    return "";
	else {
	    String s = "";
	    s += data.get(root) + " ";
	    s += toStringPreOrderRec(left(root));
	    s += toStringPreOrderRec(right(root));
	    return s;
	}
    }

    /** Formats the elements in the tree based on a post order traversal
     * @return A string representing a post order traversal of the tree
     */
    public String toStringPostOrder() {
	return toStringPostOrderRec(root);
    }

    /** Builds a string using a left subtree, right subtree, root traversal
     * @return A string representing a post order traversal of the tree
     */
    private String toStringPostOrderRec(int root) {
	if (root == -1)
	    return "";
	else {
	    String s = "";
	    s += toStringPostOrderRec(left(root));
	    s += toStringPostOrderRec(right(root));
	    s += data.get(root) + " ";
	    return s;
	}
    }

    /** Formats how an array binary tree is printed
     * @return A string containing the tree's pre order, in order, and post order traversals
     */
    public String toString() {
	return "Tree:\nPre:\t" + toStringPreOrder() + "\nIn:\t" +
	    toStringInOrder() + "\nPost:\t" + toStringPostOrder();
    }
}
