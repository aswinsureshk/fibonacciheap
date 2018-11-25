
public class FibonacciHeapNode {
	
	private FibonacciHeapNode parent;
	private FibonacciHeapNode leftSibling;
	private FibonacciHeapNode rightSibling;
	private FibonacciHeapNode child;
	private int degree;
	private int key;
	private boolean childcut;
	
	public FibonacciHeapNode(int key) {
		
		this.parent = null;
		this.leftSibling = null;
		this.rightSibling = null;
		this.child = null;
		this.degree = 0;
		this.key = key;
		this.childcut = false;
	}
	
	public FibonacciHeapNode(FibonacciHeapNode parent, FibonacciHeapNode leftSibling, FibonacciHeapNode rightSibling,
					FibonacciHeapNode child, int degree, int key, boolean childcut) {
		
		this.parent = parent;
		this.leftSibling = leftSibling;
		this.rightSibling = rightSibling;
		this.child = child;
		this.degree = degree;
		this.key = key;
		this.childcut = childcut;
	}
	
	public FibonacciHeapNode getParent() {
		return parent;
	}
	public void setParent(FibonacciHeapNode parent) {
		this.parent = parent;
	}
	public FibonacciHeapNode getLeftSibling() {
		return leftSibling;
	}
	public void setLeftSibling(FibonacciHeapNode leftSibling) {
		this.leftSibling = leftSibling;
	}
	public FibonacciHeapNode getRightSibling() {
		return rightSibling;
	}
	public void setRightSibling(FibonacciHeapNode rightSibling) {
		this.rightSibling = rightSibling;
	}
	public FibonacciHeapNode getChild() {
		return child;
	}
	public void setChild(FibonacciHeapNode child) {
		this.child = child;
	}
	public int getDegree() {
		return degree;
	}
	public void setDegree(int degree) {
		this.degree = degree;
	}
	public int getKey() {
		return key;
	}
	public void setKey(int key) {
		this.key = key;
	}
	public boolean isChildcut() {
		return childcut;
	}
	public void setChildcut(boolean childcut) {
		this.childcut = childcut;
	}
	
	
}
