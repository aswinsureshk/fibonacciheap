
public interface IFibonacciHeap {

	/**
	 * Inserts the given node to the Fibonacci Heap
	 * @param maxNode the maximum node in the Fibonacci heap
	 * @param theNode the node to be inserted
	 * @return the new resulting heap max
	 * @throws AssertionError if theNode is null
	 */
	FibonacciHeapNode insert(FibonacciHeapNode maxNode, FibonacciHeapNode theNode);
	
	/**
	 * Increases the value in the {@code FibonacciHeapNode} by theAmount value
	 * @param maxNode the maximum node in the Fibonacci heap
	 * @param theNode the target node on which the operation should be performed
	 * @param theAmount the amount by which the value of {@code FibonacciHeapNode} should be increased
	 * @return the new resulting heap max
	 * @throws FibonacciHeapException if theAmount is negative
	 */
	FibonacciHeapNode increaseKey(FibonacciHeapNode maxNode, FibonacciHeapNode theNode, int theAmount) throws FibonacciHeapException;
		
	/**
	 * Removes the maximum node from the Fibonacci heap
	 * @param maxNode the maximum node in the Fibonacci heap
	 * @return the new resulting heap max
	 * @throws FibonacciHeapException if pairwise_combine fails
	 */
	FibonacciHeapNode removeMax(FibonacciHeapNode maxNode) throws FibonacciHeapException; 
	
	/**
	 * Removes child from the child list of parent and reinserts to root child list
	 * @param maxNode the maximum node from the Fibonacci heap
	 * @param child the node to be cut from parent
	 * @param parent the from which the child has to be removed
	 * @return the new resulting heap max
	 * @throws FibonacciHeapException if child cannot be cut from parent
	 */
	FibonacciHeapNode cut(FibonacciHeapNode maxNode, FibonacciHeapNode child, FibonacciHeapNode parent) throws FibonacciHeapException;
	
	/**
	 * Removes the given node from the Fibonacci Heap
	 * @param maxNode the maximum node in the Fibonacci heap
	 * @param theNode the node to be removed
	 * @return the new resulting heap max
	 * @throws FibonacciHeapException if removeMax fails
	 */
	FibonacciHeapNode remove(FibonacciHeapNode maxNode, FibonacciHeapNode theNode) throws FibonacciHeapException;
	
	/**
	 * Does a {@link #cut} and then recursively does {@link #cascading_cut} towards root
	 * until first encountered {@link FibonacciHeapNode#childcut} is false
	 * @param maxNode the maximum node in the Fibonacci heap
	 * @param theNode the node on which the operation should be performed
	 * @return the new resulting heap max
	 * @throws FibonacciHeapException if theNode cannot be cut from parent
	 * @return the new resulting heap max
	 */
	FibonacciHeapNode cascading_cut(FibonacciHeapNode maxNode, FibonacciHeapNode theNode) throws FibonacciHeapException;
	
	/**
	 * Melds two provided heaps into one heap. Meld may not return maxNode. 
	 * @param heap1 the first heap to be used for melding
	 * @param heap2 the second heap to be used for melding
	 * @return heap2 unless heap1 is null
	 */
	FibonacciHeapNode meld(FibonacciHeapNode heap1, FibonacciHeapNode heap2);

	/**
	 * Updates the maxNode in the root list and returns the new maxNode
	 * @param oldMaxNode the maximum node in the Fibonacci heap
	 * @return the new resulting heap max
	 */
	FibonacciHeapNode updateMaxNode(FibonacciHeapNode oldMaxNode);

	/**
	 * Combines nodes of equal degrees in the root list
	 * @param maxNode the maximum node in the Fibonacci heap 
	 * @throws FibonacciHeapException if makeChild fails
	 */
	void pairwise_combine(FibonacciHeapNode maxNode) throws FibonacciHeapException;
	
	/**
	 * Makes heap1 a child of heap2
	 * @param heap1 the first heap to be used
	 * @param heap2 the second heap to be used
	 * @throws FibonacciHeapException if heap1.key > heap2.key
	 */
	void makeChild(FibonacciHeapNode heap1, FibonacciHeapNode heap2) throws FibonacciHeapException;
}
