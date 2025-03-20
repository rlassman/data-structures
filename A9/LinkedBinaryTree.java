/* Name: Rebecca Lassman
 * File: LinkedBinaryTree.java
 * Desc:
 *
 * This program defines a balanced BST Tree, which stores elements that are comparable.
 *
 */

import java.lang.Math;

public class LinkedBinaryTree<E extends Comparable<E>> implements BinaryTree<E> {
    
    private class Node<E> {
	private E element; //the element stored in the node
	private Node<E> left; //reference to the left child
	private Node<E> right; //reference to the right child
	private Node<E> parent;
	private int height = 0;

	/** Creates a node with the given element, left child, and right child
	  * @param e the element to be stored
	  * @param l reference to the left child's node
	  * @param r reference to the right child's node
	  */
	public Node(E e, Node<E> l, Node<E> r) {
	    element = e;
	    left = l;
	    right = r;
	}

	/** Returns the value of the element stored in the node
	 * @return The value of element
	 */
	public E getElement() {
	    return element;
	}
	/** Returns the value of the left child's node
	 * @return The value of left
	 */
	public Node<E> getLeft() {
	    return left;
	}
	/** Returns the value of the right child's node
	 * @return The value of right
	 */
	public Node<E> getRight() {
	    return right;
	}
	/** Returns the value of the parent's node
	 * @return The value of parent
	 */
	public Node<E> getParent() {
	    return parent;
	}
	/** Returns the value of the Node's height
	 * @return The value of height
	 */
	public int getHeight() {
	    return height;
	}
	
	/** Sets the value of element
	 * @param e The element to be stored
	 */
	public void setElement(E e) {
	    element = e;
	}
	/** Sets the value of left node
	 * @param l The new left node
	 */
	public void setLeft(Node<E> l) {
	    left = l;
	}
	/** Sets the value of right node
	 * @param r The new right node
	 */
	public void setRight(Node<E> r) {
	    right = r;
	}
	/** Sets the value of parent node
	 * @param p The new parent node
	 */
	public void setParent(Node<E> p) {
	    parent = p;
	}
	/** Sets the value of height
	 * @param h The new height
	 */
	public void setHeight(int h) {
	    height = h;
	}

	/** Formats how a Node is printed
	 * @return The Node's element and its height
	 */
	public String toString() {
	    return element + "(" + height + ")";
	}
    }

    private int size = 0; //the number of nodes in the tree
    private Node<E> root = null; //the first node

    /** Creates a linked binary tree */
    public LinkedBinaryTree() { }

    /** Returns the value of the root element
     * @return The value of the root node's element
     */
    public E getRootElement() {
	if (root != null)
	    return root.getElement();
	else
	    return null;
    }

     /** Returns the value of the height of the entire tree
     * @return The value of the root's height
     */
    public int height() {
	if (root == null)
	    return 0;
	else
	    return root.getHeight();
    }

    /** Returns the value of size
     * @return The size of the tree
     */
    public int size() {
	return size;
    }

    /** Returns if the tree is empty or not
     * @return True if the tree is empty, false otherwise
     */
    public boolean isEmpty() {
	return size==0;
    }

    /** Inserts a new node into the tree in the correct ordered location
     * @param element The element to be inserted
     */
    public void insert(E element) {
        Node<E> newest = insertRec(root, element); //the node that has been inserted
	if (isEmpty()) {
	    root = newest; //updates the root
	}
	//rebalances the root
	updateHeight(root);
        Node<E> p = rebalance(root);
	root.setParent(p);
	root = p;
	size++;
    }

    /** Recursively compares the elements in the tree to find the correct insertion location
     * @param root The current root node element is compared with
     * @param element The element to be inserted
     * @return The node being inserted
     */
    private Node<E> insertRec(Node<E> root, E element) {
	if (root == null) { //reached the correct position in the tree
	    return new Node<E>(element, null, null); //node is inserted as a leaf
	}
	if (root.getElement().compareTo(element) > 0) {
	    root.setLeft(insertRec(root.getLeft(), element));
	    root.getLeft().setParent(root);
	    updateHeight(root.getLeft());
	    Node<E> p = rebalance(root.getLeft());
	    p.setParent(root);
	    root.setLeft(p);
	} else {
	    root.setRight(insertRec(root.getRight(), element));
	    root.getRight().setParent(root);
	    updateHeight(root.getRight());
	    Node<E> p = rebalance(root.getRight());
	    p.setParent(root);
	    root.setRight(p);
	}
	return root;
    }

    /** Ensures the tree is balanced by making sure the Node's children's heights differ
     * by no more than one
     * @param n The node being rebalanced
     * @return The new root node of the subtree, or n if the tree is already balanced
     */
    private Node<E> rebalance(Node<E> n) {
	updateHeight(n);
	//computes left and right child heights
	int lh = 0, rh = 0;
	if (n.getLeft() != null)
	    lh = n.getLeft().getHeight();
	if (n.getRight() != null)
	    rh = n.getRight().getHeight();

	if (lh > rh+1) { //unbalanced on the left
	    int llh = 0, lrh = 0; //heights of left child's left and right children
	    if (n.getLeft().getLeft() != null)
		llh = n.getLeft().getLeft().getHeight();
	    if (n.getLeft().getRight() != null)
		lrh = n.getLeft().getRight().getHeight();
	    if (llh >= lrh)
		return rotateRight(n);
	    else
		return rotateLeftRight(n);
	} else if (rh > lh+1) { //unbalanced on the right
	    int rlh = 0, rrh = 0; //heights of right child's left and right children
	    if (n.getRight().getLeft() != null)
		rlh = n.getRight().getLeft().getHeight();
	    if (n.getRight().getRight() != null)
		rrh = n.getRight().getRight().getHeight();
	    if (rrh >= rlh)
		return rotateLeft(n);
	    else
		return rotateRightLeft(n);
	}
	return n; //node was already balanced
    }

    /** Rotates the given subtree to the right
     * @param r The critical node that is unbalanced
     * @return The new root node of the subtree
     */
    private Node<E> rotateRight(Node<E> r) {
	Node<E> p = r.getLeft(); //will become the new root
	r.setLeft(p.getRight());
	p.setRight(r);
	updateHeight(r);
	updateHeight(p);
	return p;	
    }

    /** Rotates the given subtree to the left
     * @param l The critical node that is unbalanced
     * @return The new root node of the subtree
     */
    private Node<E> rotateLeft(Node<E> l) {
	Node<E> p = l.getRight(); //will become the new root
	l.setRight(p.getLeft());
	p.setLeft(l);
	updateHeight(l);
	updateHeight(p);
	return p;	
    }

    /** Rotates the given subtree to the left then right
     * @param r The critical node that is unbalanced
     * @return The new root node of the subtree
     */
    private Node<E> rotateLeftRight(Node<E> r) {
	r.setLeft(rotateLeft(r.getLeft()));
        return rotateRight(r);
    }

    /** Rotates the given subtree to the right then left
     * @param r The critical node that is unbalanced
     * @return The new root node of the subtree
     */
    private Node<E> rotateRightLeft(Node<E> l) {
	l.setRight(rotateRight(l.getRight()));
        return rotateLeft(l);
    }

    /** Computes the height of a given node
     * @param n The node
     */
    private void updateHeight(Node<E> n) {
	int lh = 0, rh = 0; //left and right child's heights
	if (n.getLeft() != null)
	    lh = n.getLeft().getHeight();
	if (n.getRight() != null)
	    rh = n.getRight().getHeight();
	int height = 1 + Math.max(lh, rh); //taller child's height + one for parent
	n.setHeight(height);
    }

   /** Checks if an element is in the tree
     * @param element The element being checked for
     * @return True if the element is in the tree, false otherwise
     */
    public boolean contains(E element) {
	return containsRec(root, element);
    }

    /** Recursively searches the tree for an element
     * @param root The current root node element is compared with
     * @param element The element being checked for
     * @return True if the element is in the tree, false otherwise
     */
    private boolean containsRec(Node<E> root, E element) {
	if (root == null) //the element was not found
	    return false;
	int result = element.compareTo(root.getElement()); //comparison between the current root and element
	if (result == 0)
	    return true;
	else if (result < 0)
	    return containsRec(root.getLeft(), element);
	else
	    return containsRec(root.getRight(), element);
    }

    /** Removes a node from the tree
     * @param element The element to be removed
     * @return True if the element was found and removed, false otherwise
     */
    public boolean remove(E element) {
	if (contains(element)) {
	    Node<E> newest = removeRec(root, element);
	    if (contains(element)) //case where tree's root has one child
		root = newest; //updates root to its child
	    size--;
	    if (!isEmpty()) {
		Node<E> p = rebalance(root);
		root.setParent(p);
		root = p;
	    }
	    return true;
	}
	else
	    return false;
    }

    /** Recursively searches for an element and removes it if found
     * @param element The element to be removed
     * @return The root node 
     */
    private Node<E> removeRec(Node<E> root, E element) {
        int result = element.compareTo(root.getElement()); //comparison between the current root and element
	//adjusts the links to remove the element
	if (result < 0) {
	    root.setLeft(removeRec(root.getLeft(), element));
	    updateHeight(root);
	    if (root.getLeft() != null) {
		 Node<E> p = rebalance(root.getLeft());
		 p.setParent(root);
		 root.setLeft(p);
	    }
	} else if (result > 0) {
	    root.setRight(removeRec(root.getRight(), element));
	    updateHeight(root);
	    if (root.getRight() != null) {
		 Node<E> p = rebalance(root.getRight());
		 p.setParent(root);
		 root.setRight(p);
	    }
        } else { //the element was found
	    //root has one child
	    if (root.getLeft() == null) {
	        if (root.getRight() != null)
		    root.getRight().setParent(root.getParent());
		return root.getRight();
	    } else if (root.getRight() == null) {
		if (root.getLeft() != null)
		    root.getLeft().setParent(root.getParent());
		return root.getLeft();
	    //root has two children
	    } else {
		//replaces root with the smallest element in its right subtree
		root.setElement(minKey(root.getRight()));
		root.setRight(removeRec(root.getRight(), root.getElement()));
	    }
	}
	return root;		    
    }

    /** Finds the leftmost element starting from a given node
     * @param root The current root node being checked
     * @return The smallest element starting from root
     */
    private E minKey(Node<E> root) {
	if (root.getLeft() == null)
	    return root.getElement();
	else
	    return minKey(root.getLeft());
    }

    /** Searches the tree for a given element
     * @param element The element being looked for
     * @return The element that equals the given element, or null
     */
    public E search(E element) {
	Node found = searchRec(root, element); //node containing element
	if (found != null) {
	    E e = (E) found.getElement(); //element of found node
	    return e;
	} else
	    return null;
    }

    /** Recursively checks each element in the tree for a given element
     * @param root The starting node
     * @param element The element being looked for
     * @return The node containing the element that equals the given element, or null
     */
    private Node<E> searchRec(Node<E> root, E element) {
	if (root == null) { //the element was not found
	    return null;
	}
	int result = element.compareTo(root.getElement()); //comparison between the current root and element
	if (result == 0)
	    return root;
	else if (result < 0)
	    return searchRec(root.getLeft(), element);
	else
	    return searchRec(root.getRight(), element);
    }

    /** Replaces the element in a given node
     * @param element The element that will replace the old element
     */
    public void updateKey(E element) {
	Node found = searchRec(root, element); //node with an equal element
	if (found != null) {
	    found.setElement(element);
	}
    }

    /** Formats the elements in the tree in order based on how they are comparable
     * @return A string representing an in order traversal of the tree
     */
    public String toStringInOrder() {
	return toStringInOrderRec(root);
    }

    /** Builds a string using a left subtree, root, right subtree traversal
     * @return A string representing an in order traversal of the tree
     */
    private String toStringInOrderRec(Node<E> root) {
	if (root == null) { //base case, the traversal has reached the left/rightmost node
	    return "";
	} else {
	    String s = "";
	    s += toStringInOrderRec(root.getLeft());
	    s += root.toString() + " ";
	    s += toStringInOrderRec(root.getRight());
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
    private String toStringPreOrderRec(Node<E> root) {
	if (root == null) { //base case, the traversal has reached the left/rightmost node
	    return "";
	} else {
	    String s = "";
	    s += root.getElement() + " ";
	    s += toStringPreOrderRec(root.getLeft());
	    s += toStringPreOrderRec(root.getRight());
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
    private String toStringPostOrderRec(Node<E> root) {
	if (root == null) { //base case, the traversal has reached the left/rightmost node
	    return "";
	} else {
	    String s = "";
	    s += toStringPostOrderRec(root.getLeft());
	    s += toStringPostOrderRec(root.getRight());
	    s += root.getElement() + " ";
	    return s;
	}
    }

    /** Formats how a linked binary tree is printed
     * @return A string containing the tree's pre order, in order, and post order traversals
     */
    public String toString() {
        return "Tree:\nPre:\t" + toStringPreOrder() + "\nIn:\t" +
	    toStringInOrder() + "\nPost:\t" + toStringPostOrder();
    }
}
