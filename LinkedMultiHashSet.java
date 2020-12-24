import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * LinkedMultiHashSet is an implementation of a (@see MultiSet), using a
 * hashtable as the internal data structure, and with predictable iteration
 * order based on the insertion order of elements.
 * 
 * Its iterator orders elements according to when the first occurrence of the
 * element was added. When the multiset contains multiple instances of an
 * element, those instances are consecutive in the iteration order. If all
 * occurrences of an element are removed, after which that element is added to
 * the multiset, the element will appear at the end of the iteration.
 * 
 * The internal hashtable array should be doubled in size after an add that
 * would cause it to be at full capacity. The internal capacity should never
 * decrease.
 * 
 * Collision handling for elements with the same hashcode (i.e. with hashCode())
 * should be done using linear probing, as described in lectures.
 * 
 * @param <T> type of elements in the set
 */

// worst case time complexity is O(4n) that is O(n)
public class LinkedMultiHashSet<T> implements MultiSet<T>, Iterable<T> {

	// TODO: implement question 4 in this file
	T arr[];
	Integer[] arrcount;
	Integer[] orderSet;
	Integer[] orderSetTemp;
	int temp = 0;

	int size = 0;
	int distictCountSize = 0;
	int internalCapacity = -1;
	Integer currentCapacity;
	boolean flagResize = false;
	int orderNumber = 0;

	// worst case time complexity O(3n) O(n)
	// worst case space cpmplexity O(1)
	public LinkedMultiHashSet(int initialCapacity) {

		currentCapacity = initialCapacity;
		arr = (T[]) new Object[initialCapacity];
		arrcount = new Integer[initialCapacity];

		orderSet = new Integer[initialCapacity];
	}

	// worst case time complexity,
	// in worst case it will check the entire array till the last, so O(n-1)=> O(n)
	// when resize is triggered then O(n)+)(n) => O(n)
	// worst case space complexity
	// arrays are instance variables in the method we are using only variables so,
	// O(3)
	// when resize is triggered then O(n) for the resize() method fo space
	private void properAdd(T element, int count) {
		int index = element.hashCode() % currentCapacity;

		// System.out.println(index);

		// T arr1[] = (T[]) new Object[10];
		// size is less than current capacity
		// int count = 1;

		if (distictCountSize != currentCapacity) {
			int start = index;

			// if index of arry is null its fine or it iterates until it finds space 
			//to fit in.
			while (true) {
				if (arrcount[index] == null) {
					arr[index] = element;
					arrcount[index] = count; // 1 is the count.

					if (flagResize != true) {
						orderSet[index] = orderNumber;
						orderNumber++;
					} else {
						// recording the order 
						orderSetTemp[index] = orderSet[temp];
					}

					distictCountSize++;
					break;
					// if the no element is present in the calculated index
				} else if (arrcount[index] == -1) {
					arr[index] = element;
					arrcount[index] = count; // 1 is the count.

					if (flagResize != true) {
						orderSet[index] = orderNumber;
						orderNumber++;
					} else {
						orderSetTemp[index] = orderSet[temp];
					}

					distictCountSize++;
					break;
				} else if (arr[index].equals(element)) {
					arrcount[index] = arrcount[index] + count; // 1 is count

					if (flagResize != true) {
						orderSet[index] = orderNumber;
						orderNumber++;
					} else {
						orderSetTemp[index] = orderSet[temp];
					}

					break;
				}

				if (index < currentCapacity - 1)
					index++;
				else
					index = 0;
				// this means we have circled the whole array and could not find the space
				// this code might not execute at all as we have some code that detcts
				// this issue, however we palcing this code
				if (start == index) {
					// resize
					flagResize = true;
					resize();
					flagResize = false;
					orderSet = orderSetTemp;
				}

			}
			size = size + count;

		}

		// its means the array is full and needs to resize
		if (distictCountSize == currentCapacity) {
			// resize
			// System.out.println("resize");
			flagResize = true;
			resize();
			flagResize = false;
			orderSet = orderSetTemp;
			orderSetTemp = null;
		}

	}

	// worst case time complexity n
	// worst case space complexity is 3n
	public void resize() {

		T arrCopy[] = arr;
		Integer arrcountCopy[] = arrcount;
		// doubling th4e capacity 
		Integer previuosCapacity = currentCapacity;
		currentCapacity = currentCapacity * 2;
		internalCapacity = distictCountSize;

		arr = (T[]) new Object[currentCapacity];
		arrcount = new Integer[currentCapacity];
		orderSetTemp = new Integer[currentCapacity];

		size = 0;
		distictCountSize = 0;
		temp = 0;
		// itersting and send the each element of the previous array to the new big array.
		for (int i = 0; i < arrcountCopy.length; i++) {

			if (arrcountCopy[i] != null) {
				if (arrcountCopy[i] != -1) {
					temp = i;
					// complexity is O(1) while resizing so
					add(arrCopy[i], arrcountCopy[i]);
				}
			}

		}
		// arr = arrnew;

	}

	// worst case time complexity O(n)
	// worst case space cpmplexity
	// arrays are instance variables in the method we are using only variables so,
	// O(3)
	// when resize is triggered then O(n) for the resize() method for space
	@Override
	public void add(T element) {
		properAdd(element, 1);

	}

	// worst case time complexity O(n)
	// worst case space complexity
	// arrays are instance variables in the method we are using only variables so,
	// O(3)
	// when resize is triggered then O(n) for the resize() method for space
	@Override
	public void add(T element, int count) {
		properAdd(element, count);
	}

	// worst case time complexity O(n-1)=>O(n), when the required element is leaner
	// probed to
	// n-1 .
	// worst case space complexity O(3)
	@Override
	public boolean contains(T element) {
		if (indexOf(element) == -1)
			return false;
		else
			return true;
	}

	// worst case time complexity O(n-1)=>O(n), when the required element is leaner
	// probed to
	// n-1 .
	// worst case space complexity O(3)
	private int indexOf(T element) {
		int index = element.hashCode() % currentCapacity;
		int start = index;
		while (true) {
			// no element
			if (arrcount[index] == null)
				return -1;// return false;
			// used to be an element but not its deleted 
			else if (arrcount[index] == -1) {
				return -1;
				// elemet found
			} else if (arr[index].equals(element))
				return index; // return true;
			if (index < currentCapacity - 1)
				index++;
			else
				index = 0;
			// circled the whole array in searching 
			if (start == index) {
				// element is not found and searched the whole array
				return -1;
			}
		}
	}

	// worst case time complexity O(n-1)=>O(n), when the required element is leaner
	// probed to
	// n-1 .
	// worst case space complexity O(3)
	@Override
	public int count(T element) {

		// System.out.println(indexOf(element));
		if (indexOf(element) != -1)
			return arrcount[indexOf(element)];
		else
			return 0;
	}

	// worst case time complexity O(n-1)=>O(n), when the required element is leaner
	// probed to
	// n-1 .
	// worst case space complexity O(3)
	@Override
	public void remove(T element) throws NoSuchElementException {
		properRemoval(element, 1);
	}

	// worst case time complexity O(n-1)=>O(n), when the required element is leaner
	// probed to
	// n-1 .
	// worst case space complexity O(3)
	private void properRemoval(T element, int count) throws NoSuchElementException {
		int index = element.hashCode() % currentCapacity;
		int start = index;

		while (true) {
			// element is not there in the array
			if (arr[index] == null) {
				throw new NoSuchElementException();
			}
			// element is not there in the array
			if (arrcount[index] == -1) {
				throw new NoSuchElementException();
			}
			if (arr[index].equals(element)) {
				if (arrcount[index] == count) {
					arrcount[index] = -1;

					orderSet[index] = -1;

					distictCountSize--;
				} else if (arrcount[index] > count)
					arrcount[index] = arrcount[index] - count;
				else
					throw new NoSuchElementException();
				// reducing the sixe to an appropate size
				size = size - count;
				break;

			}

			if (index < currentCapacity - 1)
				index++;
			else
				index = 0;

			if (start == index) {
				// element is not found and searched the whole array
				throw new NoSuchElementException();
			}
		}

	}

	// worst case time complexity O(n-1)=>O(n), when the required element is leaner
	// probed to
	// n-1 .
	// worst case space complexity O(3)
	@Override
	public void remove(T element, int count) throws NoSuchElementException {
		properRemoval(element, count);
	}

	// worst case time complexity O(1)
	// worst case space cpmplexity O(1)
	@Override
	public int size() {
		return size;
	}

	// worst case time complexity O(1)
	// worst case space cpmplexity O(1)
	@Override
	public int internalCapacity() {
		return currentCapacity;
	}

	// worst case time complexity O(1)
	// worst case space cpmplexity O(1)
	@Override
	public int distinctCount() {
		return distictCountSize;
	}

	
	// worst case time complexity,  we have a loop in a loop.
	// each loop iterates for n times. so, worst case is O(n^2)
	//worst case space complexity O(2n) => O(n) 
		@Override
	public Iterator<T> iterator() {

		orderSetTemp = orderSet;
		int psize = orderSetTemp.length;
		T tempArray[] = (T[]) new Object[psize];
		int x = 0;
      // double looping to find the order in which we need to present the iterator
		for (int i = 0; i < orderSetTemp.length; i++) {
			// int maxNumber = orderSetTemp[i];
			int maxindex = 0;
			int max = -1;
			for (int j = 0; j < orderSetTemp.length; j++) {

				if (orderSetTemp[j] != null) {
					if (orderSetTemp[j] != -1) {

						if (orderSetTemp[j] > max) {
							maxindex = j;
							max = orderSetTemp[j];
						}
					}
				}
			}

			if (max > -1) {
				tempArray[x] = arr[maxindex];
				x++;
				orderSetTemp[maxindex] = -1;

			}
		}
		//orderSetTemp = tempArray;
		//tempArray = null;
		T tempArray2[] = (T[]) new Object[x];
		// removing the null values in the end
		for(int i=0; i<tempArray2.length;i++) {
			tempArray2[i] = tempArray[i];  
		}
		orderSetTemp = null;
		tempArray = null;
		Iterator<T> iterator = Arrays.stream(tempArray2).iterator();
		return iterator;
	}
}