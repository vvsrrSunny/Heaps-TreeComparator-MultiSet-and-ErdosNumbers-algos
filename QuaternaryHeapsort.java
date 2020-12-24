public class QuaternaryHeapsort {

	/**
	 * Sorts the input array, in-place, using a quaternary heap sort.
	 *
	 * @param input to be sorted (modified in place)
	 */
// worst case time complexity is n/4log base 4 (n) + nlog base 4 (n) => n/4log(n)+ nlog()
	// that is nlog(n)
	// worst case space complexity is we are using a single array and inplace
	// recursion and test of the
	// variables are constants so, its O(n)
	public static <T extends Comparable<T>> void quaternaryHeapsort(T[] input) {
		// TODO: implement question 1 here
		int size = input.length;
		int start = 0;
		// Heap is build for size less than 4. Array manuplated to set the array as heap
		if (size < 4)
			quaternaryDownheap(input, 0, size);
		else {
			// Heap is build for size greater than 4. Array manuplated to set the array as
			// heap
			// nlog(n) is the time complexity
			for (int i = size / 4 - 1; i >= 0; i--)
				quaternaryDownheap(input, i, size);
		}

		// one after the another we are geting the content form the heap
		// O(n*log(n)) time complexity
		for (int i = size - 1; i > 0; i--) {
			// Move current root to end
			// Sending the present root value to the end of the array
			T temp = input[0];
			input[0] = input[i];
			input[i] = temp;

			// call quaternaryDownheap on the current heap which is reduced.
			quaternaryDownheap(input, 0, i); // time complexity is O(logn)
		}

	}

	/**
	 * Performs a downheap from the element in the given position on the given max
	 * heap array.
	 *
	 * A downheap should restore the heap order by swapping downwards as necessary.
	 * The array should be modified in place.
	 * 
	 * You should only consider elements in the input array from index 0 to index
	 * (size - 1) as part of the heap (i.e. pretend the input array stops after the
	 * inputted size).
	 *
	 * @param input array representing a quaternary max heap.
	 * @param start position in the array to start the downheap from.
	 * @param size  the size of the heap in the input array, starting from index 0
	 */

	// Worst case time complexity is
	// n= size
	// 
	// log base 4 (n-start) => log(n-start)
	// which is log(n)
	// worst case space complexity is, we are using only an arry of size n and
	// rest are the variable which are constant. So, its O(n).
	public static <T extends Comparable<T>> void quaternaryDownheap(T[] input, int start, int size) {

		// TODO Auto-generated method stub
		int largest = start;
		int nodeOne = 4 * start + 1; // first node
		int nodeTwo = 4 * start + 2; // second node
		int nodeThree = 4 * start + 3; // third node
		int nodeFour = 4 * start + 4; // fourth node
		// If left child is smaller than root
		if (nodeOne < size && input[nodeOne].compareTo(input[largest]) > 0)
			largest = nodeOne;

		// If right child is larger than largest until now.
		if (nodeTwo < size && input[nodeTwo].compareTo(input[largest]) > 0)
			largest = nodeTwo;

		if (nodeThree < size && input[nodeThree].compareTo(input[largest]) > 0)
			largest = nodeThree;

		if (nodeFour < size && input[nodeFour].compareTo(input[largest]) > 0)
			largest = nodeFour;
		// If largest is not root
		if (largest != start) {
			T swap = input[start];
			input[start] = input[largest];
			input[largest] = swap;
			// quaternaryDownheap recursing will affected continue affecting sub-tree
			quaternaryDownheap(input, largest, size); //
		}

	}

}
