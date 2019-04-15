/**
 * Implementation of a Binomial Tree
 * 
 * Lily Xu
 * January 2017
 */

public class BinomialTree {
	private Integer key;
	private BinomialTree parent = null;
	private BinomialTree child = null;
	private BinomialTree left = null;
	private BinomialTree right = null;
	private int degree;
	
	
	/**
	 * BinomialTree constructor
	 */
	public BinomialTree(Integer key) {
		this.key = key;
		this.degree = 0;
		
		this.left = this;
		this.right = this;
	}
	
	
	/**
	 * Add right sibling
	 */
	public void addRight(BinomialTree tree) {
		tree.setLeft(this);
		tree.setRight(this.right);
		this.right.setLeft(tree);
		this.setRight(tree);
	}
	
	
	/**
	 * Add child to tree
	 */
	public void addChild(BinomialTree tree) {
		if (this.child == null) {
			this.child = tree;
		} else {
			this.child.addRight(tree);
		}
		
		tree.setParent(this);
		
		this.degree++;
	}
	
	
	/**
	 * Remove tree from left/right connections
	 * 
	 * Returns true if successfully removed
	 *         false if it was not linked to anything
	 */
	public boolean remove() {
		// no other trees
		if (this.right == this && this.left == this) {
			return false;
		} else {
			this.left.setRight(this.right);
			this.right.setLeft(this.left);
			
			return true;
		}
	}
	
	public String toString() {
		return "key: " + key + ", degree: " + degree;
	}
	
	
	// ---------------------------------------------
	// getters and setters
	
	public void setKey(Integer key) {
		this.key = key;
	}
	
	public void setParent(BinomialTree tree) {
		this.parent = tree;
	}
	
	public void setChild(BinomialTree tree) {
		this.child = tree;
	}
	
	public void setRight(BinomialTree tree) {
		this.right = tree;
	}
	
	public void setLeft(BinomialTree tree) {
		this.left = tree;
	}
	
	public void setDegree(int degree) {
		this.degree = degree;
	}
	
	public BinomialTree getParent() {
		return this.parent;
	}
	
	public BinomialTree getChild() {
		return this.child;
	}
	
	public BinomialTree getLeft() {
		return this.left;
	}
	
	public BinomialTree getRight() {
		return this.right;
	}
	
	public Integer getKey() {
		return this.key;
	}
	
	public int getDegree() {
		return this.degree;
	}
}

