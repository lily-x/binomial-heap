/**
 * Small-scale testing of the Binomial Heap implementation 
 * 
 * Lily Xu
 * January 2017
 */

public class SmallTest {
	/**
	 * Display items in heap collection
	 */
	public static void printHeap(BinomialHeap h) {
		System.out.println();
		System.out.println(h);
		BinomialTree pointer = h.getPointer();
		BinomialTree t = pointer;
		do {
			System.out.println(t);
			t = t.getRight();
		} while(t != pointer);
		System.out.println();
	}
	
	
	public static void main(String[] args) {
		// demonstrating makeBinomialHeap()
		BinomialHeap h1 = new BinomialHeap();
		
		
		// demonstrating insert()
		BinomialTree temp = null;
		for (int i = 1; i < 101; i++) {
			BinomialTree t = h1.insert(i);
			
			if (i == 68) {
				temp = t;
			}
		}
		
		
		// demonstrating findMin()
		System.out.println("min value: " + h1.findMin());
		
		
		// demonstrating deleteMin()
		h1.deleteMin();
		printHeap(h1);
		
		h1.deleteMin();
		printHeap(h1);
		
		h1.deleteMin();
		printHeap(h1);
		
		
		// demonstrating decreaseKey()
		h1.decreaseKey(temp, -5);
		printHeap(h1);
		
		
		// demonstrating delete()
		h1.delete(temp);
		
		
		// demonstrating union()
		BinomialHeap h2 = new BinomialHeap();
		
		for (int i = 500; i < 600; i++) {
			h2.insert(i);
		}
		
		h2.union(h1);
		h2.deleteMin();
		
		printHeap(h2);
	}
}
