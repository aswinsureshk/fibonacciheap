
import java.util.HashMap;
import java.util.Map;

public class FibonacciHeapImpl implements IFibonacciHeap {

	@Override
	public FibonacciHeapNode insert(FibonacciHeapNode maxNode, FibonacciHeapNode theNode) {
		
		assert(theNode != null);
		
		theNode.setLeftSibling(theNode);
		theNode.setRightSibling(theNode);
		theNode.setChildcut(false);
		
		//if the existing heap is empty, theNode is the only element
		if (maxNode == null)
			return theNode;
		
		//inserts theNode to the right of maxNode
		theNode.setRightSibling(maxNode.getRightSibling());
		maxNode.getRightSibling().setLeftSibling(theNode);
		
		maxNode.setRightSibling(theNode);
		theNode.setLeftSibling(maxNode);
		//if maxNode is the only node, we set its left also to theNode
		if (maxNode.getLeftSibling() == maxNode)
			maxNode.setLeftSibling(theNode);
		if (theNode.getKey() > maxNode.getKey())
			return theNode;
		
		return maxNode;
	}

	@Override
	public FibonacciHeapNode increaseKey(FibonacciHeapNode maxNode, FibonacciHeapNode theNode, int theAmount) throws FibonacciHeapException {
		
		if (theAmount < 0)
			throw new FibonacciHeapException("The new key is lesser than the current key");
		
		int newKey = theNode.getKey() + theAmount;
		theNode.setKey(newKey);
		FibonacciHeapNode parent = theNode.getParent();
		
		//if key of theNode has increased more than its parent, do a cascading_cut
		if (parent!=null && theNode.getKey()>parent.getKey()) {
			//Set childcut to true so that this node gets cut and call cascasing_cut
			theNode.setChildcut(true);
			maxNode = cascading_cut(maxNode, theNode);
		}
		
		if (theNode.getKey() > maxNode.getKey())
			return theNode;
		
		return maxNode;
	}
	
	/**
	 *  1. remove maxNode and set A to another node in the root list
	 *  2. set B to a node in the children list of maxNode
	 *  3. meld A and B (reinsert subtree of maxNode into root list)
	 *  4. update maxNode
	 *  5. pairwise combine of equal degree nodes in the final root list
	 * @throws FibonacciHeapException 
	 */
	@Override
	public FibonacciHeapNode removeMax(FibonacciHeapNode maxNode) throws FibonacciHeapException {
		
		
		FibonacciHeapNode A = null;
		
		FibonacciHeapNode rightSibling = maxNode.getRightSibling();
		FibonacciHeapNode leftSibling  = maxNode.getLeftSibling();
		
		maxNode.setRightSibling(null);
		maxNode.setLeftSibling(null);
		
		//More than 1 node in root list
		if (rightSibling != maxNode) {
			
			//1
			A = rightSibling;
			leftSibling.setRightSibling(rightSibling);
			rightSibling.setLeftSibling(leftSibling);	
		}
		
		FibonacciHeapNode child = maxNode.getChild();
		maxNode.setChild(null);
		
		FibonacciHeapNode B = null;
		
		if (child != null) {
			B = child;
			FibonacciHeapNode next = child;
			boolean isrootprocessed = false;
			while(next != B || !isrootprocessed) {
				isrootprocessed = true;
				next.setParent(null);
				next = next.getRightSibling();
			}
		}
		
		//3
		maxNode = meld(A, B);
		
//		printHeap(maxNode);
		
		//4
		maxNode = updateMaxNode(maxNode);
		
		//5
		pairwise_combine(maxNode);
		
		return maxNode;
	}

	@Override
	public FibonacciHeapNode cut(FibonacciHeapNode maxNode, FibonacciHeapNode child, FibonacciHeapNode parent) throws FibonacciHeapException {

		FibonacciHeapNode startchild = parent.getChild();
		if (startchild == child && child.getRightSibling() == child) //only one child
			parent.setChild(null);
		else {
			FibonacciHeapNode next = startchild;
			boolean isrootprocessed = false;
			while(next.getRightSibling() != child && (next != startchild || !isrootprocessed)) {
				isrootprocessed = true;
				next = next.getRightSibling();
			}
//			if (next == startchild) //reached the of linked list
//				throw new FibonacciHeapException("child cannot be cut from parent");
			next.setRightSibling(child.getRightSibling());
			child.getRightSibling().setLeftSibling(next);
			parent.setChild(next);
		}
		parent.setDegree(parent.getDegree()-1);
		child.setParent(null);
		child.setChildcut(false);
		child.setLeftSibling(child);
		child.setRightSibling(child);
		//insert the cut child to the root list
		return insert(maxNode, child);
	}

	/**
	 * 1. remove theNode from its doubly linked sibling list
	 * 2. if theNode has a parent, remove theNode from parent's children list
	 * 3. set the parent field of theNode's children to null
	 * 4. Combine top-level list and child-level list of theNode; do not pairwise-combine equal degrees
	 * 	  =>  meld top level heap and child heap
	 */
	@Override
	public FibonacciHeapNode remove(FibonacciHeapNode maxNode, FibonacciHeapNode theNode) throws FibonacciHeapException {
		
		if (theNode == maxNode)
			return removeMax(theNode);
		
		//1
		theNode.getLeftSibling().setRightSibling(theNode.getRightSibling());
		
		//2
		FibonacciHeapNode parent = theNode.getParent();
		if (parent != null) {
			FibonacciHeapNode startchild = parent.getChild();
			if (theNode == startchild) 
				parent.setChild(null);
			else {
				FibonacciHeapNode next = startchild;
				while(next.getRightSibling() != theNode && next.getRightSibling() != startchild)
					next = next.getRightSibling();
				if (next.getRightSibling() == startchild) //reached the of linked list
					throw new FibonacciHeapException("theNode cannot be removed from parent's children list");
				next.setRightSibling(theNode.getRightSibling());
				theNode.getRightSibling().setLeftSibling(next);
			}		
		}
		
		//3
		FibonacciHeapNode child = maxNode.getChild();
		
		if (child != null) {
			FibonacciHeapNode start = child;
			FibonacciHeapNode next = child;
			boolean isrootprocessed = false;
			while(next != start || !isrootprocessed) {
				isrootprocessed = true;
				next.setParent(null);
				next = next.getRightSibling();
			}
		}
		
		//4 - maxNode doesn't change as meld() returns second argument
		maxNode = meld(child, maxNode);
		
		return maxNode;		
	}

	@Override
	public FibonacciHeapNode cascading_cut(FibonacciHeapNode maxNode, FibonacciHeapNode theNode) throws FibonacciHeapException {
		  
		FibonacciHeapNode parent = theNode.getParent();
		if (parent != null) {
			if (theNode.isChildcut()) {
				maxNode = cut(maxNode, theNode, parent);
				return cascading_cut(maxNode, parent);
			}
			else 
				theNode.setChildcut(true);
		}
		return maxNode;
	}

	@Override
	public FibonacciHeapNode meld(FibonacciHeapNode heap1, FibonacciHeapNode heap2) {
		
		if (heap1 == null)
			return heap2;
		
		if (heap2 == null)
			return heap1;
		
		FibonacciHeapNode tempRightSiblingHeap1 = heap1.getRightSibling();
		heap1.setRightSibling(heap2.getRightSibling());
		heap2.getRightSibling().setLeftSibling(heap1);
		heap2.setRightSibling(tempRightSiblingHeap1);
		tempRightSiblingHeap1.setLeftSibling(heap2);
		return heap2;
	}
	
	@Override
	public FibonacciHeapNode updateMaxNode(FibonacciHeapNode oldMaxNode) {
		
		if (oldMaxNode == null)
			return null;
		FibonacciHeapNode node = oldMaxNode;
		FibonacciHeapNode newMaxNode = oldMaxNode;
		boolean isrootprocessed = false;
		while (node != oldMaxNode || !isrootprocessed) {	
			isrootprocessed = true;
			if (node.getKey() > newMaxNode.getKey())
				newMaxNode = node;
			node = node.getRightSibling();
		}
		return newMaxNode;
	}
	
	public static void printHeap(FibonacciHeapNode maxNode) {
		
		FibonacciHeapNode node = maxNode;
		FibonacciHeapNode temp = maxNode;
		boolean isrootprocessed = false;
		while (node != temp || !isrootprocessed) {
			isrootprocessed = true;
			System.out.print("-" + node.getKey() + "-");
			node = node.getRightSibling();
		}
		System.out.println();
	}
	
	@Override
	public void pairwise_combine(FibonacciHeapNode maxNode) throws FibonacciHeapException {
		
		if (maxNode == null || maxNode.getRightSibling() == maxNode) //only one node - nothing to combine
			return;
		Map<Integer, FibonacciHeapNode> degreeMap = new HashMap<>();
		FibonacciHeapNode startNode = maxNode.getRightSibling();
		degreeMap.put(startNode.getDegree(), startNode);
		FibonacciHeapNode node = startNode.getRightSibling();
		while (node != maxNode || (node == maxNode && degreeMap.containsKey(node.getDegree()))) {
			
			
			if (degreeMap.containsKey(node.getDegree())){
				
				FibonacciHeapNode existingNode = degreeMap.get(node.getDegree());
				
				if (existingNode == node)
					break;
				
				degreeMap.remove(node.getDegree());
				
				//Do a pairwise combine of node and existingNode 
				//(the '=' should be on the heap2, else maxNode will become a child and will break logic when there are two equal degreeNodes)
				FibonacciHeapNode heap1 = node.getKey() < existingNode.getKey() ? node : existingNode;
				FibonacciHeapNode heap2 = node.getKey() >= existingNode.getKey() ? node : existingNode;
				
				//Disconnect heap1 from root list and make it a child of heap2
				heap1.getLeftSibling().setRightSibling(heap1.getRightSibling());
				heap1.getRightSibling().setLeftSibling(heap1.getLeftSibling());
				
				heap1.setLeftSibling(heap1);
				heap1.setRightSibling(heap1);
				
				makeChild(heap1, heap2);
				node = heap2;
			}
			else {
				degreeMap.put(node.getDegree(), node);
				node = node.getRightSibling();
			}
		}
	}

	@Override
	public void makeChild(FibonacciHeapNode heap1, FibonacciHeapNode heap2) throws FibonacciHeapException {
		
		if(heap1.getKey() > heap2.getKey())
			throw new FibonacciHeapException("heap1 should be smaller than heap2");
		
		if (heap2.getChild() != null) 
			insert(heap2.getChild(), heap1);
		else
			heap2.setChild(heap1);
		
		heap2.setDegree(heap2.getDegree()+1);
		heap1.setChildcut(false);
		heap1.setParent(heap2);
		
	}
	
}

