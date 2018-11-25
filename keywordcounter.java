import java.io.File;

public class keywordcounter {
	
	public static void main(String[] args) {
		
		if (args.length != 1) 
			System.out.println("Run as : java keywordcounter file_name");
		
		assert(args.length == 1);
		
		File inputFile = new File(args[0]);
		
		Program p = new Program();
		
		try {
		
			p.start(inputFile);
		
		} catch (FibonacciHeapException e) {
			e.printStackTrace();
			System.out.println(" Program exited : " + e.getLocalizedMessage());
		}

	}

}
