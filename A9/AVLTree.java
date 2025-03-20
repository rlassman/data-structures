/* Name: Rebecca Lassman
 * File: AVLTree.java
 * Desc:
 *
 * This program defines an AVL Tree, which stores elements that are comparable.
 *
 */

public class AVLTree<E extends Comparable<E>> extends LinkedBinaryTree<E> {

    /** Formats how an AVLTree is printed
     * @return A String containing all the nodes in sorted order
     */
    public String toString() {
	return toStringInOrder();
    }
}
