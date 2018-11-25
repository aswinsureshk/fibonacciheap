# fibonacciheap

## COP 5536Fall 2018

**Programming Project**

### How to run :

-  make clean

-  make

-  java keywordcounter input_file.txt

**Outline:**

keywordcounter.java will read the file supplied as argument via the command line and does a store()
when keywords and count are encountered in file. The store() does an increaseKey() operation in the 
FibonacciHeap on the given keyword. The memory address of the FibonacciHeapNode is stored in a HashMap.
When an integer ‘n’ is encountered, a heapify() is called which does removeMax() operation is performed 
on the heap n times, written to the output_file.txt and then the removed nodes are reinserted back into the heap.

Implementation :

Each node in the heap is of type FibonacciHeapNode.

FibonacciHeapException is thrown if any exception occurs during Heap operations.

I have defined the IFibonacciHeap interface for defining the operations supported by a max Fibonacci Heap.

The methods that alter the maxNode during operations such as increaseKey or removeMax etc. will return the new maxNode.

The function prototype for the supported operations are shown below :


| FibonacciHeapNode insert(FibonacciHeapNode maxNode, FibonacciHeapNode theNode); |
| --- |
| Description | Inserts a Node into Fibonacci Heap |
| Parameters |   | maxNode | the maximum node in the Fibonacci heap |
| theNode | the node to be inserted |
| Return Value | the new resulting heap max |

| FibonacciHeapNodeincreaseKey(FibonacciHeapNodemaxNode,FibonacciHeapNodetheNode, **int** theAmount) **throws** FibonacciHeapException; |
| --- |
| Description | Increases the value in the given theNode by theAmount value |
| Parameters |   | maxNode | the maximum node in the Fibonacci heap |
| theNode | the target node on which the operation should beperformed |
| theAmount | the amount by which the value of {@codeFibonacciHeapNode} should be increased |
| Return Value | Tthe new resulting heap max |



| FibonacciHeapNode removeMax(FibonacciHeapNode maxNode) **throws** FibonacciHeapException; |
| --- |
| Description | Removes the maximum node from the Fibonacci heap |
| Parameters |   | maxNode | the maximum node in the Fibonacci heap |
| Return Value | the new resulting heap max |



| FibonacciHeapNode cut(FibonacciHeapNode maxNode, FibonacciHeapNode child,FibonacciHeapNode parent) **throws** FibonacciHeapException; |
| --- |
| Description | Removes child from the child list of parent and reinserts to root child list |
| Parameters |   | maxNode | the maximum node in the Fibonacci heap |
| child | child the node to be cut from parent |
| parent | parent the from which the child has to be removed |
| Return Value | the new resulting heap max |



| FibonacciHeapNode remove(FibonacciHeapNode maxNode, FibonacciHeapNode theNode) **throws** FibonacciHeapException; |
| --- |
| Description | Removes the given node from the Fibonacci Heap |
| Parameters |   | maxNode | the maximum node in the Fibonacci heap |
| theNode | the target node on which the operation should beperformed |
| Return Value | the new resulting heap max |



| FibonacciHeapNode cascading\_cut(FibonacciHeapNode maxNode, FibonacciHeapNode theNode) **throws** FibonacciHeapException; |
| --- |
| Description | DoesacutontheNodeandthenrecursivelydoescascading\_cuttowardsroot until first encountered FibonacciHeapNode.childcut is false |
| Parameters |   | maxNode | the maximum node in the Fibonacci heap |
| theNode | the target node on which the operation should beperformed |
| Return Value | the new resulting heap max |



| FibonacciHeapNode meld(FibonacciHeapNode heap1, FibonacciHeapNode heap2); |
| --- |
| Description | Meldstwoprovidedheapsintooneheap.MeldmaynotreturnmaxNode. |
| Parameters |   | maxNode | the maximum node in the Fibonacci heap |
| heap1 | the first heap to be used |
| heap2 | the second heap to be used |
| Return Value | heap2 unless heap1 is null |



| FibonacciHeapNode updateMaxNode(FibonacciHeapNode oldMaxNode); |
| --- |
| Description | Updates the maxNode in the root list and returns the new maxNode |
| Parameters |   | oldMaxNode | the maximum node in the Fibonacci heap |
| Return Value | the new resulting heap max |

| **void** pairwise\_combine(FibonacciHeapNode maxNode) **throws** FibonacciHeapException; |
| --- |
| Description | Combines nodes of equal degrees in the root list |
| Parameters |   | maxNode | the maximum node in the Fibonacci heap |
| Return Value | - |



| **void** makeChild(FibonacciHeapNode heap1, FibonacciHeapNode heap2) **throws** FibonacciHeapException; |
| --- |
| Description | Makes heap1 a child of heap2 |
| Parameters |   | heap1 | the first heap to be used (child of resulting heap) |
| heap2 | the first heap to be used (parent of resulting heap) |
| Return Value | - |



### Sample Input and output :

**Input (from file input\_file.txt [provided via cmd]):**

**Sample input 1:**

$facebook 5

$youtube 3

$facebook 10

$amazon 2

$gmail 4

$weather 2

$facebook 6

$youtube 8

$ebay2

$news2

$facebook 12

$youtube 11

$amazon 6

3

$facebook 12

$amazon 2

$playing4

$gmail15

$drawing 3

$ebay12

$netflix6

$cnn 5

5

Stop

### Output 1 (in file output\_file.txt):


facebook,youtube,amazon
facebook,youtube,gmail,ebay,amazon


### Sample input 2:

$facebook 1

$youtube3

$australia5

$amazon 7

$gmail 9

$weather 11

$india 13

$china 15

$usa 17

$japan 19

1

$youtube17

$australia16

3

3

$japan2

3

$japan2

3

Stop

### Output 2(in file output\_file.txt):

japan australia,youtube,japan
australia,youtube,japan
australia,japan,youtube
japan,australia,youtube
