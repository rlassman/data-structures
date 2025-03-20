/* Name: Rebecca Lassman
 * File: LinkedBinaryTree.java
 * Desc:
 *
 * This program defines a linked binary tree, which stores elements that are
 * comparable. It maintains the order where left children are smaller than
 * their parent and right children are larger.
 *
 */

public class LinkedBinaryTree<E extends Comparable<E>> implements BinaryTree<E> {
    
    private class Node<E> {
	private E element; //the element stored in the node
	private Node<E> left; //reference to the left child
	private Node<E> right; //reference to the right child

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
	
	/** Sets the value of element
	 * @param e The element to be stored
	 */
	public void setElement(E e) {
	    element = e;
	}
	/** Sets the value of left node
	 * @param e The new left node
	 */
	public void setLeft(Node<E> l) {
	    left = l;
	}
	/** Sets the value of right node
	 * @param e The new right node
	 */
	public void setRight(Node<E> r) {
	    right = r;
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
	return root.getElement();
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
	} else {
	    root.setRight(insertRec(root.getRight(), element));
	}
	return root;
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

    /** Returns the element from the tree that equals the given element
     * @param element The element being searched for
     * @return The element that equals the given element
     */
    public E findElement(E element) {
	Node<E> newest = findElementRec(root, element);
	if (newest == null)
	    return null;
	else
	    return newest.getElement();
    }

    private Node<E> findElementRec(Node<E> root, E element) {
	if (root == null)
	    return null;
	int result = element.compareTo(root.getElement());
	if (result == 0)
	    return root;
	else if (result < 0)
	    return findElementRec(root.getLeft(), element);
	else
	    return findElementRec(root.getRight(), element);
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
	} else if (result > 0) {
	    root.setRight(removeRec(root.getRight(), element));
        } else { //the element was found
	    //root has one child
	    if (root.getLeft() == null)
		return root.getRight();
	    else if (root.getRight() == null)
		return root.getLeft();
	    //root has two children
	    else {
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

    /** Formats the elements in the tree in order based on how they are comparable
     * @return A string representing an in order traversal of the tree
     */
    public String toStringInOrder() {
	String s = toStringInOrderRec(root);
	s = s.substring(0, s.length()-1); //removes the last space
	return s;
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
	    s += root.getElement() + " ";
	    s += toStringInOrderRec(root.getRight());
	    return s;
	}
    }

     /** Formats the elements in the tree based on a pre order traversal
     * @return A string representing a pre order traversal of the tree
     */
    public String toStringPreOrder() {
	String s = toStringPreOrderRec(root);
	s = s.substring(0, s.length()-1); //removes the last space
	return s;
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
	String s = toStringPostOrderRec(root);
	s = s.substring(0, s.length()-1); //removes the last space
	return s;
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
