import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class traitSearch {
	public static final String TRAITS_FILE = "traits.txt";
	public static final int MAX_DIFFERENCE = 2;

	public traitSearch() {

	}

	public static String searchFile(String fileName, String searchStr) throws FileNotFoundException {
		Scanner scan = new Scanner(new File(fileName));
		int smallestDif = Integer.MAX_VALUE;
		String closestMatch = "";
		while (scan.hasNextLine()) {
			String lineNorm = scan.nextLine();
			String line = lineNorm.toLowerCase();
			int a;
			if((a=keyDistance(line.trim(), searchStr.toLowerCase().trim()))<smallestDif){
				closestMatch=lineNorm;
				smallestDif = a;
			}
		}
		scan.close();
		closestMatch = closestMatch.replaceAll("0", "Recessive Autosomal Trait");
		closestMatch = closestMatch.replaceAll("1", "Dominant Autosomal Trait");
		closestMatch = closestMatch.replaceAll("2", "Recessive X-Linked Trait");
		closestMatch = closestMatch.replaceAll("3", "Dominant X-Linked Trait");
		closestMatch = closestMatch.replaceAll("4", "Blendable Trait");
		closestMatch = closestMatch.replaceAll("5", "Mutation");
		closestMatch = closestMatch.replaceAll("6", "Not Inherited");
		return smallestDif==0?closestMatch:"?"+closestMatch;
	}

	public static int keyDistance(String s1, String s2) {
		int ret = 0;
		for (int i = 0; i < s1.length() && i < s2.length(); i++) {
			int[] s1Coord = keyCoordinate(s1.charAt(i));
			int[] s2Coord = keyCoordinate(s2.charAt(i));
			ret+=Math.abs(s1Coord[0]-s2Coord[0])+Math.abs(s1Coord[1]-s2Coord[1]);
		}
		return ret;
	}

	public static int[] keyCoordinate(char c){
	    	char[][] keyboard = {{'1','2','3','4','5','6','7','8','9','0'},{'q','w','e','r','t','y','u','i','o','p'},{'a','s','d','f','g','h','j','k','l'},{'z','x','c','v','b','n','m'},{' '}};
				for (int i = 0; i < keyboard.length; i++) {
					for (int j = 0; j < keyboard[i].length; j++) {
						if(keyboard[i][j]==c){
						int[] temp = {i,j};
						return temp;
						}
					}
				}
				int[] temp ={5000,5000}; 
				return temp;
	    }

	public static int stringDifference(String s1, String s2) {
		int ret = 0;
		for (int i = 0; i < s1.length() && i < s2.length(); i++) {
			if (s1.charAt(i) != s2.charAt(i)) {
				ret++;
			}
		}
		return ret;
	}


}
