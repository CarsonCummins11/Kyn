import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class traitSearch {
		public static final String TRAITS_FILE = "traits.txt";
		public static final int MAX_DIFFERENCE = 2;
		public traitSearch() {
			
		}

	    public static String searchFile(String fileName, String searchStr) throws FileNotFoundException{
	        Scanner scan = new Scanner(new File(fileName));
	        while(scan.hasNextLine()){
	            String line = scan.nextLine().toLowerCase();
	            if(hasClose(line, searchStr.toLowerCase())){
	                scan.close();
	            	return stringDifference(line,searchStr.toLowerCase())<1?line:"Assuming you meant: "+line;
	            }
	        }
	        scan.close();
	        return null;
	    }
	    
	    
	    public static int stringDifference(String s1,String s2){
	    	int ret = 0;
	    	for (int i = 0; i < s1.length() && i < s2.length(); i++) {
				if(s1.charAt(i)!=s2.charAt(i)){
					ret++;
				}
			}
	    	return ret;
	    }

	    private static boolean hasClose(String line, String searchStr) {
			String s = "";
	    	for (int i = 0; i < searchStr.length() && i < line.length(); i++) {
				s += line.charAt(i);
			}
	    	//System.out.println(s);
	    	if (s.length() == searchStr.length()){
	    		return stringDifference(s, searchStr) <= MAX_DIFFERENCE?true:false;
	    	}
	    	return false;
	    }
	   

}
