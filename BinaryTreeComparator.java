import java.util.Comparator;

/**
 * A comparator for Binary Trees.
 */
public class BinaryTreeComparator<E extends Comparable<E>> implements Comparator<BinaryTree<E>> {

	/**
	 * Compares two binary trees with the given root nodes.
	 *
	 * Two nodes are compared by their left childs, their values, then their right
	 * childs, in that order. A null is less than a non-null, and equal to another
	 * null.
	 *
	 * @param tree1 root of the first binary tree, may be null.
	 * @param tree2 root of the second binary tree, may be null.
	 * @return -1, 0, +1 if tree1 is less than, equal to, or greater than tree2,
	 *         respectively.
	 */
	@Override
	public int compare(BinaryTree<E> tree1, BinaryTree<E> tree2) {
		// TODO: implement question 3 here
		int x = inorderCheck(tree1, tree2);
		return x;
	}
// in worst case we need to visit each node once and both are the asme trees, so
	// it is O(n) because of visiting all the nodes,
	// space complexity is , at most we gonna use the memory of the nodes value of height 
	// of the tree. if h1 is the longest height of the first binary tree and h2 is 
	//the longest height of the second binary tree where h1<h2 then worest case of 
	// space complexity is log(h1).
	int inorderCheck(BinaryTree<E> tree1, BinaryTree<E> tree2) {

		int x;
		if (tree1 == null && tree2 == null) {
			return 0;
		}
		if (tree1 == null)
			return -1;

		if (tree2 == null)
			return 1;
		
		if (tree1.isLeaf() && tree1.isLeaf()) {
			if (tree1.getValue().compareTo(tree2.getValue()) < 0) {
				return -1;
			}
			if (tree1.getValue().compareTo(tree2.getValue()) == 0) {
				return 0;
			}
			if (tree1.getValue().compareTo(tree2.getValue()) > 0) {
				return 1;
			}
		}

		/* first recur on left child */
		x = inorderCheck(tree1.getLeft(), tree2.getLeft());
		if(x != 0 ) {
			return x;
		}
		/* then print the data of node */
		//System.out.println(tree1.getValue() + " value of counter part " + tree2.getValue());
		if (tree1.getValue().compareTo(tree2.getValue()) > 0) {
			x = 1;
			//System.out.println(" should retuen 1 and halt the rest");
			return x;
		} else if (tree1.getValue().compareTo(tree2.getValue()) < 0) {
			x = -1;
			//System.out.println(" should retuen -1 and halt the rest");
			return x;
		}

		return inorderCheck(tree1.getRight(), tree2.getRight());

		/* now recur on right child */

	}

}
