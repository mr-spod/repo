import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.IOException;

/**
 * Put a short phrase describing the program here.
 * 
 * @author Put your name here
 * 
 */
public final class ProgramWithIO {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private ProgramWithIO() {
    }
    
    /**
     * Computes {@code a} mod {@code b} as % should have been defined to work.
     * 
     * @param a
     *            the number being reduced
     * @param b
     *            the modulus
     * @return the result of a mod b, which satisfies 0 <= {@code mod} < b
     * @requires b > 0
     * @ensures <pre>
     * 0 <= mod  and  mod < b  and
     * there exists k: integer (a = k * b + mod)
     * </pre>
     */
    public static int mod(int a, int b) {
    		int rem = a % b;
    		rem = rem < 0 ? -rem : rem;
    		
    		if (a < 0) {
    			rem = mod(b, rem);
    		}
    		
    		return rem;
    }
    
    private static int hashFunction(int i) { 
    		return i;
    }

    /**
     * Main method.
     * 
     * @param args
     *            the command line arguments
     */
    	public static void main(String[] args) {
        
    		String[] fakeArgs = new String[2];
    		fakeArgs[0] = "data/text.txt";
    		fakeArgs[1] = "data/newtext.txt";
    		

    		try {
    			BufferedReader fileIn = new BufferedReader(new FileReader(fakeArgs[0]));
    			PrintWriter fileOut = new PrintWriter(new BufferedWriter(new FileWriter(fakeArgs[1])));
    			try {
        			String s = fileIn.readLine(); 
        			while (s != null) {
        				fileOut.println(s);
        				s = fileIn.readLine();
        			}
        			try {
        				fileIn.close();
        				fileOut.close();
        			} catch (IOException e) { 
        				System.err.println("Error closing input file");
        			}
        		} catch (IOException e) {
        			System.err.println("Error reading from file");
        		}
    		} catch (IOException e) {
    			System.err.println("Error opening file");
    			return;
    		}
    		 
    		
    		
    	
//        SimpleReader fileIn = new SimpleReader1L(fakeArgs[0]);
//        SimpleWriter fileOut = new SimpleWriter1L(fakeArgs[1]);
//        
//        while (!fileIn.atEOS()) {
//        		fileOut.println(fileIn.nextLine());
//        }
//        
//        fileIn.close();
//        fileOut.close();
        
    }

}
