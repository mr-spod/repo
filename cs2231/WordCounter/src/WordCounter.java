import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.set.Set1L;
import components.set.Set;
import components.map.Map;
import components.map.Map1L;
import components.queue.Queue;
import components.queue.Queue1L;
import java.util.*;

/**
 * This program asks a user for the paths of a text file and an html file. 
 * It reads the words in the text file and counts them, 
 * then writes html code to the latter path.
 * The html is a simple webpage with a 2 column table containing the words & their counts.
 * 
 * @author Sean O'Donnell
 * 
 */

public final class WordCounter {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private WordCounter() {
    }
    
    /**
     * Private class implementing Comparator interface
     * 
     * Compares {@code String}s alphabetically
     */
    private static class WordComparator implements Comparator<String> {
    		public WordComparator() {}
    	
    		@Override
    		public int compare(String s1, String s2) {
    			// Use java's String.compareTo() method
    			return s1.compareTo(s2);
    		}
    }

    /**
     * Static set containing word separators filled in main method.
     */
    private static Set<Character> separators;
    
    /**
     * Counts the number of appearances of each word in the given string.
     * 
     * @returns a {@code Map1L<String, Integer>} whose pairs represent each word in the given string and its corresponding number of appearances.
     * 
     */
    private static Map<String, Integer> countWords(String s) {
    		assert s != null : "Violation of: s is not null";
    		
    		Map<String, Integer> wordMap = new Map1L<String, Integer>();
    		// Index variable used to traverse s word by word
    		int readIndex = 0;
    		while (readIndex < s.length()) {
    			// Next either word or separator
    			String next = nextWordOrSeparator(s.substring(readIndex)).toLowerCase();
    			// If it's a word...
    			if (!separators.contains(next.charAt(0))) {
    				// If this word has been counted already...
    				if (wordMap.hasKey(next)) {
    					// Increase value for this key by one
    					int value = wordMap.value(next);
    					wordMap.replaceValue(next, value + 1);
    				} else {
    					// Add a key/value pair of this word and 1, since this is the first time we've encountered this word.
    					wordMap.add(next, 1);
    				}
    			}
    			// Increase the index variable by the length of the next word/separator
    			readIndex += next.length();
    		}
    		return wordMap;
    }
    
    /**
     * Returns the next occurring {@code String} of either separators or alphabetical characters in s.
     * 
     * @param s
     * 		{@code String} to be read
     * @ensures
     * 		if {@code separators.countains(s.charat(0))}
     * 			the string of separator characters at the front of s
     * 		else
     * 			the string of characters (the word) at the front of s
     * @returns
     * 		either a word or a string of separator characters
     */
    private static String nextWordOrSeparator(String s) {
    		String answer = "";
    		if (s.length() > 0) {
    			// Index variable to read s char by char
        		int readIndex = 0;
        		// Boolean flag used to stop the following while loop
    			boolean atEndOfNext = false;
    			// Stop loop if readIndex reaches end of s (or flag is set)
        		while (readIndex < s.length() && !atEndOfNext) {
        			if (separators.contains(s.charAt(readIndex))) {
        				// Increment readIndex if the char of s at that index is a separator
        				readIndex++;
        			} else {
        				// Otherwise, stop the loop
        				atEndOfNext = true;
        			}
        		}
        		if (readIndex == 0) {
        			/*
        			 *  Bring readIndex to end of the following word since the readIndex hasn't increased
        			 */
        			
        			// Reset flag variable
        			atEndOfNext = false;
        			// Stop loop if readIndex reaches end of s (or flag is set)
        			while (readIndex < s.length() && !atEndOfNext) {
        				if (!separators.contains(s.charAt(readIndex))) {
            				// Increment readIndex if the char of s at that index is not a separator
        					readIndex++;
        				} else {
        					// Otherwise, stop the loop
        					atEndOfNext = true;
        				}
        			}
        		}
        		// The next word/separator is s[0, readIndex)
        		answer = s.substring(0, readIndex);
    		}
    		return answer;
    }
    
    /**
     * Reads the text content of a file and returns it (with spaces at line breaks)
     * 
     * @param filePath
     * 		the path to the file to be read
     * @requires
     * 		[there exists a text file at filePath]
     * @return
     * 		a {@code String} representing the text content of the file
     */
    private static String contentsOfTextFile(String filePath) {
    		// Create reader object to read file at given path
    		SimpleReader fileIn = new SimpleReader1L(filePath);
    		String answer = "";
    		// Concatenate the next line until the end of the file 
    		while (!fileIn.atEOS()) {
    			answer += fileIn.nextLine() + " ";
    		}
    		// Close reader object
    		fileIn.close();
    		
    		return answer;
    }
    
    /**
     * Puts the keys in a {@code Map<String, Integer>} into alphabetical order in a {@code Queue<String>}
     * 
     * @param map
     * 		A map with words as keys
     * @ensures
     * 		The elements of the returned {@code Queue<String>} are in alphabetical order
     * @return
     * 		A {@code Queue} of the keys in map sorted in alphabetical order
     */
    private static Queue<String> alphabetizedKeys(Map<String, Integer> map) {
    		assert map != null : "Violation of: map is not null";
    	
    		Queue<String> keys = new Queue1L<String>();
    		
    		// Enqueue each key of the given map
    		for (Map.Pair<String, Integer> p: map) {
    			keys.enqueue(p.key());
    		}
    		// Sort the queue using the private comparator class
    		WordComparator comp = new WordComparator();
    		keys.sort(comp);
    		
    		return keys;
    }
    
    /**
     * @return
     * 		{@code String} of html code representing a page w a table of the words & counts
     * 
     * @param txtFileName
     * 		name of text file to be read
     * @param map
     * 		{@code Map<String, Integer>} of the words in txtFileName and their respective counts
     * @param keys
     * 		{@code Queue<String>} of words in alphabetical order
     */
    private static String htmlPage(String txtFileName,  Map<String, Integer> map, Queue<String> keys) {
    		assert map != null : "Violation of: map is not null";
    		assert keys != null : "Violation of: keys is not null";
    	
    		// Page & body header
    		String htmlString = "<html><head><title>Words Counted in " + txtFileName + "</title></head><body><h2>Words Counted in " + txtFileName + "</h2><hr>";
    		// Html table
    		htmlString += htmlTable(map, keys);
    		// End tags of html code
    		htmlString += "</body></html>";
    		return htmlString;
    }
    
    /**
     * @return
     * 		{@code String} of html code representing a 2 column table 
     * 		of the {@code Map.Pair<String, Integer>}s in map in the order of keys
     * 
     * @param map
     * @param keys
     */
    private static String htmlTable(Map<String, Integer> map, Queue<String> keys) {
    		// Html for table and title row
    		String tableString = "<table border=\"1\"><tbody><tr><th>Word</th><th>Count</th></tr>";
    		for (String key: keys) {
    			// Concatenate a row for each key
    			tableString += htmlTableRow(key, map.value(key));
    		}
    		// End table html
    		tableString += "</tbody></table>";
    		return tableString;
    }
    
    /**
     * @return
     * 		{@code String} of html code representing one 2 column row
     * 		with word in the first column and count in the second 
     * 
     * @param word
     * @param count
     */
    private static String htmlTableRow(String word, int count) {
    		// Return an html table row with 2 columns: |word|count|
    		return "<tr><td>" + word + "</td><td>" + count + "</td></tr>";
    }

    /**
     * Main method.
     * 
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();
        
        /*
         * Create static set of "all possible" separator characters
         */
        separators = new Set1L<Character>();
        separators.add(' ');
        separators.add(',');
        separators.add(':');
        separators.add('/');
        separators.add('-');
        separators.add('.');
        separators.add('?');
        separators.add('!');
        separators.add(';');
        separators.add('"');
        separators.add('&');
        separators.add('(');
        separators.add(')');
        
        /*
         * Get path for input .txt file
         */
        out.print("Enter the path to the text file: ");
        String filePath = in.nextLine();
        
        /*
         * Get path for output .html file
         */
        out.print("Enter the path to the output file: ");
        String outputPath = in.nextLine();
        
        /*
         * Represent text file as a single String object
         */
        String fileString = contentsOfTextFile(filePath);
        
        /*
         * Create map counting {word: count} pairs
         */
        Map<String, Integer> wordMap = countWords(fileString);
        
        /*
         * Create an alphabetized Queue of the keys in the map
         */
        Queue<String> keys = alphabetizedKeys(wordMap);
        
        /*
         * Write html code to the output file at the given path
         */
        String htmlString = htmlPage(filePath, wordMap, keys);

		/*
		 *  Create writer object at given path and write
		 */
		SimpleWriter fileOut = new SimpleWriter1L(outputPath);
		fileOut.print(htmlString);
        
        /*
         * Close input and output streams
         */
        in.close();
        out.close();
		fileOut.close();
    }

}
