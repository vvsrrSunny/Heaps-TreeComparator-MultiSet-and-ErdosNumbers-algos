import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class StrongHeap {
	/**
	 * 
	 * Determines whether the binary tree with the given root node is a "strong
	 * binary heap", as described in the assignment task sheet.
	 *
	 * A strong binary heap is a binary tree which is: - a complete binary tree, AND
	 * - its values satisfy the strong heap property.
	 *
	 * @param root root of a binary tree, cannot be null.
	 * @return true if the tree is a strong heap, otherwise false.
	 */
	
	/**
	 * worst case time complexity o(n)+O(n) = O(n)
	 * worst case space compeity , one of quesue, two for array list,  O(3n) = O(n)
	 * */
	public static boolean isStrongHeap(BinaryTree<Integer> root) {

		int index = 0;
		// System.out.println("isCompleteBT" +isCompleteBT(root));
		if (isCompleteBinaryTree(root)) {
			
			return true;
		}
		return false;
		// return isComplete(root, index, count);
		// return false; // TODO: implement question 2

	}

	// worst case time complexity is
	// we are iterating through every node so O(n)
	// worst case space complexity is O(n)+O(n)
	static boolean isCompleteBinaryTree(BinaryTree<Integer> root) {
		
		// Base case, tree with root node null can be considered as complete Binary Tree
		if (root == null)
			return true;

		// Create an empty queue
		Queue<BinaryTree<Integer>> queue = new LinkedList<>();
		ArrayList<Integer> arr = new ArrayList<>();
		arr.add(root.getValue());
		// Create a flag variable which will be set true
		// when a non full node is seen
		boolean flag = false;

		
		// with the help of the Queue, we are going to do traversing, that is level order traversal
		queue.add(root);
		while (!queue.isEmpty()) {
			BinaryTree<Integer> temp_node = queue.remove();

			// condition to verify the existence of the left child  
			if (temp_node.getLeft() != null) {
				
				// when we come across a nonfull node. However, it has the left child, 
				// then we can conclude that the tree is not a complete binary tree.
				if (flag == true)
					return false;

				// enqueue child on the left
				queue.add(temp_node.getLeft());
				arr.add(temp_node.getLeft().getValue());
			}
			// if this a non-full node, we can go a head and set the flag to be true
			else
				flag = true;

			// condition to verify the existence of the right child  
			if (temp_node.getRight() != null) {
				// when we come across a nonfull node. However, it has the right child, 
				// then we can conclude that the tree is not a complete binary tree.
				if (flag == true)
					return false;

				// enqueue child on the right
				queue.add(temp_node.getRight());
				arr.add(temp_node.getRight().getValue());

			}
			// if this a non-full node, we can go a head and set the flag to be true
			else
				flag = true;
		}
		//  coming here with out any encountring any of the return statement means this 
		// tree is a complete binary tree.
		boolean b = checkForStrongBinary(arr);
		return b;
	}

	//IN worst case we are going to iterate through half of all the the elements. time complexity is O(n/2)
	// worst case space complexity is O(n)
	private static boolean checkForStrongBinary(ArrayList<Integer> arr) {
		// TODO Auto-generated method stub
		int n = arr.size();
		

		boolean flag = true;
		for (int i = n / 2 - 1; i >= 0; i--) {
			int iIndex = i;

			int lc = 2 * i + 1; // left = 2*i + 1
			int rc = 2 * i + 2; // right = 2*i + 2

			// If left child is smaller than root
			// below are the continuous 6 condition to see the whether it is strong heap tree
			if (arr.get(lc) < arr.get(iIndex)) {

			} else {


				flag = false;
				// break;
			}

			// If right child is smaller than iIndex so far
			try {
				if (arr.get(rc) < arr.get(iIndex)) {

				} else {
					flag = false;
					// break;
				}
			} catch (Exception e) {

			}

			int lgc = 2 * lc + 1; // left = 2*i + 1
			int rgc = 2 * lc + 2; // right = 2*i + 2

			if (lgc < n) {
				if ((arr.get(lgc) + arr.get(lc)) < arr.get(iIndex)) {

				} else {
					flag = false;
					// break;
				}
			}
			if (rgc < n) {
				if ((arr.get(rgc) + arr.get(lc)) < arr.get(iIndex)) {

				} else {
					flag = false;
					// break;
				}
			}

			lgc = 2 * rc + 1; // left = 2*i + 1
			rgc = 2 * rc + 2; // right = 2*i + 2
			// If iIndex is not root

			if (lgc < n) {
				if ((arr.get(lgc) + arr.get(rc) < arr.get(iIndex))) {

				} else {
					flag = false;
					// break;
				}
			}
			if (rgc < n) {
				if ((arr.get(rgc) + arr.get(rc)) < arr.get(iIndex)) {

				} else {
					flag = false;
					// break;
				}
			}
			
		}

		return flag;
	}

	

	

}
