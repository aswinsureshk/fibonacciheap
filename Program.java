
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Program {
	
	private final File outputFile = new File("output_file.txt");
	private Map<String, FibonacciHeapNode> map = new HashMap<>();  
	private Map<FibonacciHeapNode, String> map2 = new HashMap<>(); //inverse map 
	private FibonacciHeapNode maxNode;
	private IFibonacciHeap fibonacciHeap = new FibonacciHeapImpl();
	private BufferedReader br = null;
	private BufferedWriter bw = null;
	
	public void start(File inputFile) throws FibonacciHeapException {
		
		try {
			
			br = new BufferedReader(new FileReader(inputFile));
			bw = new BufferedWriter(new FileWriter(outputFile));

			String line = null;
			while ((line = br.readLine()) != null) {
				if (line.charAt(0) == '$') {
					line = line.substring(1); //remove $
					String[] sar = line.split(" ");
					String keyword = sar[0];
					Integer count = Integer.valueOf(sar[1]);
					store(keyword, count);
				}
				else if (line.equals("stop"))
					break;
				else {
					int query = Integer.valueOf(line.trim());
					String result = heapify(query);
					bw.write(result);
					bw.newLine();
				}
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if (br != null)
					br.close();
				if (bw != null)
					bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public void store(String keyword, int count) throws FibonacciHeapException {
		
		if (map.containsKey(keyword)) {
			
			FibonacciHeapNode theNode = map.get(keyword);
			maxNode = fibonacciHeap.increaseKey(maxNode, theNode, count);
		}
		else {
			FibonacciHeapNode theNode = new FibonacciHeapNode(count);
			maxNode = fibonacciHeap.insert(maxNode, theNode);
			map.put(keyword, theNode);
			map2.put(theNode, keyword);
		}
		
	}
	
	/**
	 * Do remove max query times to find the {@code query} 
	 * most frequent keywords, then reinsert the removed nodes
	 * @param  query the number of maximum values to be found
	 * @return the resulting comma separated String formed by appending individual max keywords
	 * @throws FibonacciHeapException
	 * @throws IOException
	 */
	public String heapify(int query) throws FibonacciHeapException, IOException {
		
		int c = 1;
		String result = "";
//		printMap();
		List<FibonacciHeapNode> removed = new ArrayList<>();
//		printHeap(maxNode);
		while(c <= query) {
			
			if (maxNode == null) break;
			result += map2.get(maxNode);
			if (c < query) 
			   result += ",";
		    removed.add(maxNode);
		    maxNode = fibonacciHeap.removeMax(maxNode);
			c++;
//			printHeap(maxNode);

		}
		for (FibonacciHeapNode node : removed) 
			maxNode = fibonacciHeap.insert(maxNode, node);
		
//		printHeap(maxNode);
		
		if (result!=null && result.endsWith(","))
			result = result.substring(0, result.length()-1);

		return result;
	}
	
	private void printMap() {
		
		for (FibonacciHeapNode fibNode : map2.keySet()) 
			System.out.println(fibNode.getKey() + " " + map2.get(fibNode));
	}
	
	public static void printHeap(FibonacciHeapNode fnode) {
		
		if (fnode == null) return;
		FibonacciHeapNode node = fnode;
		FibonacciHeapNode temp = fnode;
		boolean isrootprocessed = false;
		
		FibonacciHeapImpl.printHeap(node);
		while (node != temp || !isrootprocessed) {
			isrootprocessed = true;
			printHeap(node.getChild());
			node = node.getRightSibling();
		}
	}
	
}

