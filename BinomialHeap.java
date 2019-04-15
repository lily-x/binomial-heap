/**
 * Implementation of a Binomial Heap
 * 
 * Lily Xu
 * January 2017
 */

public class BinomialHeap {
	private int n;						// number of items
	private int t;						// number of trees
	private BinomialTree minroot;		// pointer to minimum value
	private BinomialTree pointer;		// sentinel for collection of trees
	
	
	/**
	 * BinomialHeap constructor 
	 * equivalent to a makeBinomialHeap method
	 */
	public BinomialHeap() {
		n = 0;
		minroot = null;
		
		// circular doubly linked list with a sentinel
		pointer = new BinomialTree(null);
		pointer.setRight(pointer);
		pointer.setLeft(pointer);
	}
	
	
	/**
	 * Insert item with value x into heap
	 * 
	 * Returns newly created tree
	 */
	public BinomialTree insert(Integer x) {
		BinomialTree t = new BinomialTree(x);
		
		pointer.addRight(t);
		
		if (minroot == null || x < minroot.getKey())
			minroot = t;
		
		this.n++;
		this.t++;
		
		return t;
	}
	
	
	/**
	 * Return the minimum value in heap 
	 */
	public Integer findMin() {
		if (minroot == null)
			return null;
			
		return this.minroot.getKey();
	}
	
	
	/**
	 * Delete and return the tree minroot
	 */
	public BinomialTree deleteMin() {
		// is the heap empty?
		if (this.isEmpty())
			return null;
		
		// remove minroot node
		BinomialTree minTree = minroot;
		this.removeRoot(minroot);
		minroot = null;
		
		// is the heap empty now?
		if (this.isEmpty())
			return minTree;
		
		// initialize array A
		int logn = (int) Math.ceil(Math.log(this.n) / Math.log(2));
		BinomialTree[] A = new BinomialTree[logn + 2];	// create A[0] .. A[logn + 1]
		
		// go through collection of trees
		while (!this.isEmpty()) { 	// go until we hit the sentinel
			BinomialTree t = this.removeTree();
			int d = t.getDegree();
			
			// add each tree to array
			if (A[d] == null) {
				A[d] = t;
			} else {
				this.insertTree(mergeHeapTrees(t, A[d]));
				A[d] = null;
			}
		}
		
		// string together A[0] through A[logn]
		for (int i = 0; i <= logn; i++) {
			if (A[i] == null)
				continue;
			
			BinomialTree t = A[i];
			
			// link trees in collection
			t.setLeft(pointer);
			t.setRight(pointer.getRight());
			pointer.getRight().setLeft(t);
			pointer.setRight(t);
			
			// update minroot
			if (minroot == null || t.getKey() < minroot.getKey())
				minroot = t;

			this.t++;
			this.n += Math.pow(2, t.getDegree());
		}
		
		return minTree;
	}
	
	
	/**
	 * Decrease key of a given node while preserving heap-order property
	 */
	public void decreaseKey(BinomialTree node, Integer key) {
		node.setKey(key);
		
		// continue upwards
		BinomialTree parent = node.getParent();
		while (parent != null && key < parent.getKey()) {
			// swap right
			BinomialTree temp1p = parent.getRight();
			BinomialTree temp1n = node.getRight();
			
			node.setRight(temp1p);
			temp1p.setLeft(node);
			parent.setRight(temp1n);
			temp1n.setLeft(parent);
			
			// swap left
			BinomialTree temp2p = parent.getLeft();
			BinomialTree temp2n = node.getLeft();
			
			node.setLeft(temp2p);
			temp2p.setRight(node);
			parent.setLeft(temp2n);
			temp2n.setRight(parent);
			
			// swap parent/child
			BinomialTree temp3n = node.getChild();
			BinomialTree temp3p = parent.getParent();
			
			parent.setChild(temp3n);
			if (temp3n != null)
				temp3n.setParent(parent);
			node.setParent(temp3p);
			if (temp3p != null)
				temp3p.setChild(node);
			
			// swap degree
			int temp4 = node.getDegree();
			node.setDegree(parent.getDegree());
			parent.setDegree(temp4);
			
			// set parent/child
			node.setChild(parent);
			parent.setParent(node);
			
			parent = node.getParent();
		}
		
		if (key < minroot.getKey())
			minroot = node;
	}
	
	
	/**
	 * Merge two trees while preserving heap-order property
	 * Returns the merged tree
	 */
	public BinomialTree mergeHeapTrees(BinomialTree t1, BinomialTree t2) {
		if (t1 == null && t2 == null) {
			return null;
		} else if (t1 == null) {
			return t2;
		} else if (t2 == null) {
			return t1;
		}
		
		// maintain heap-order
		if (t1.getKey() < t2.getKey()) {
			t1.addChild(t2);
			return t1;
		} else {
			t2.addChild(t1);
			return t2;
		}
	}
	
	
	/**
	 * Remove and return an arbitrary tree from collection
	 * 
	 * helper function used by deleteMin
	 */
	public BinomialTree removeTree() {
		if (this.isEmpty())
			return null;
		
		BinomialTree t = pointer.getRight();
		
		// call helper function to remove specific tree
		this.removeTree(t);
		
		return t;
	}
	
	/**
	 * Remove a specific tree from collection
	 * 
	 * helper function used by deleteMin
	 */
	public void removeTree(BinomialTree t) {
		if (this.isEmpty())
			return;
		
		t.remove();
		
		// set t to point to itself
		t.setRight(t);
		t.setLeft(t);
		
		// update variables
		this.t--;
		this.n -= Math.pow(2, t.getDegree());
	}
	
	
	/**
	 * Remove root of a tree and add its children to collection
	 * 
	 * helper function used by deleteMin
	 */
	public void removeRoot(BinomialTree t) {
		// t has no children
		if (t.getDegree() == 0) {
			this.removeTree(t);
			return;
		}
		
		// remove t from collection
		this.removeTree(t);
		
		// add children of t into collection
		BinomialTree t1 = this.pointer;
		BinomialTree t2 = t.getChild();
		BinomialTree t1Right = t1.getRight();
		BinomialTree t2Left = t2.getLeft();
		
		t1.setRight(t2);
		t2.setLeft(t1);
		t1Right.setLeft(t2Left);
		t2Left.setRight(t1Right);
		
		// update variables
		this.n += Math.pow(2, t.getDegree()) - 1;
		this.t += t.getDegree();
	}
	
	/**
	 * Add specific tree to collection
	 * 
	 * helper function used by deleteMin
	 */
	public BinomialTree insertTree(BinomialTree t) {
		// update pointers
		t.setRight(pointer.getRight());	
		t.setLeft(pointer);
		pointer.getRight().setLeft(t);
		pointer.setRight(t);
		
		// update variables
		this.t++;
		this.n += Math.pow(2, t.getDegree());
		
		return t;
	}
	
	
	/**
	 * Delete node from heap
	 */
	public void delete(BinomialTree node) {
		this.decreaseKey(node, Integer.MIN_VALUE);
		this.deleteMin();
	}
	
	
	/**
	 * Perform union operation
	 */
	public void union(BinomialHeap heap) {
		this.n += heap.n;
		this.t += heap.t;
		
		if (heap.isEmpty()) {
			return;
		} else if (this.isEmpty()) {
			this.pointer = heap.pointer;
			this.minroot = heap.minroot;
			return;
		}
		
		// rearrange pointers
		BinomialTree h1 = this.pointer;
		BinomialTree h2 = heap.pointer.getRight();
		BinomialTree h1Right = h1.getRight();
		BinomialTree h2Left = h2.getLeft();
		
		h1.setRight(h2);
		h2.setLeft(h1);
		h1Right.setLeft(h2Left);
		h2Left.setRight(h1Right);
		
		// remove sentinel from h2
		heap.pointer.remove();
		
		// update minroot if necessary
		if (heap.findMin() < this.findMin()) {
			this.minroot = heap.getMinroot();
		}
	}
	
	/**
	 * Return true if the heap has no items (only sentinel)
	 */
	public boolean isEmpty() {
		// technically, if one of these is true both will be true
		return (pointer.getRight() == pointer 
				&& pointer.getLeft() == pointer);
	}
	
	
	/**
	 * String representation of important features of heap
	 */
	public String toString() {
		return "(n = " + n + ", t = " + t + ", minroot = " + findMin() 
				+ ", pointer = '" + pointer.getRight().toString() + "')";
	}
	
	
	// ---------------------------------------------
	// getter methods
	
	public BinomialTree getPointer() {
		return this.pointer;
	}
	
	public BinomialTree getMinroot() {
		return this.minroot;
	}
	
	public int getN() {
		return this.n;
	}
}
