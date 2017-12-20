import java.util.Comparator;

import components.map.Map;
import components.map.Map.Pair;
import components.map.Map1L;
import components.set.Set;
import components.set.Set2;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.sortingmachine.SortingMachine;
import components.sortingmachine.SortingMachine1L;

/**
 * This program converts a text file into a tag cloud generator with the users
 * specified maximum number of tags.
 *
 * @author JS & Sean
 *
 */
public final class TagCloudGenerator {

    /**
     * Default constructor--private to prevent instantiation.
     */
    private TagCloudGenerator() {
        // no code needed here
    }

    /**
     * Compares {@code String}s in alphabetical order.
     */
    private static class StringLT implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            return o1.compareTo(o2);
        }
    }

    /**
     * Compares {@code Integer}s in descending order.
     */
    private static class IntegerLT implements Comparator<Integer> {
        @Override
        public int compare(Integer o1, Integer o2) {
            if (o1 > o2) {
                return -1;
            } else if (o1 < o2) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    /**
     * Set of separators.
     *
     * @return set of separators
     */
    private static Set<Character> separators() {
        // creating a constructor and adding characters which will be
        // considered as a separator
        Set<Character> separators = new Set2<>();
        separators.add(' ');
        separators.add('?');
        separators.add(',');
        separators.add('.');
        separators.add(':');
        separators.add(';');
        separators.add('/');
        separators.add('-');
        separators.add('!');
        separators.add('(');
        separators.add(')');
        separators.add('"');
        separators.add('`');
        separators.add('*');
        separators.add('\'');
        separators.add('[');
        separators.add(']');
        separators.add('_');
        separators.add('\n');
        separators.add('\t');
        separators.add('\0');

        return separators;
    }

    /**
     * @param minCount
     *            the nth highest count of any word in the given file,
     *            if n is the given maximum number of tags to count
     */
    private static int minCount;

    /**
     * @param maxCount
     *            the largest count of any word in the given file
     */
    private static int maxCount;

    /**
     * Number of font sizes.
     */
    private static final int FONTSIZEAMOUNT = 37;

    /**
     * Minimum font size number.
     */
    private static final int FONTSIZEMIN = 11;

    /**
     * Outputs the header tags for the HTML file.
     *
     * @param out
     *            file to edit
     * @param fileName
     *            name of the file
     * @param max
     *            maximum number of tags
     * @requires out is not blank or null
     * @ensures print statements are forwarded to the file
     *
     */
    private static void header(SimpleWriter out, String fileName, int max) {
        // outputs these to the file
        out.println("<html>");
        out.println("<head>");
        out.println("<link rel=\"stylesheet\" href=\"./tagcloud.css\">");
        out.println("<title>");
        out.println("Top " + max + " words in " + fileName);
        out.println("</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h2>Top " + max + " words in " + fileName + "</h2>");
        out.println("<hr>");
        out.println("<div class=\"cdiv\">");
        out.println("<p class=\"cbox\">");
    }

    /**
     * Reads the text file and returns it as a String format.
     *
     * @ensures text file is read and assigned to a String variable
     * @param in
     *            file to read
     * @return text
     */
    private static String returnWord(SimpleReader in) {
        StringBuilder text = new StringBuilder();
        // while the input stream has not end, append each line with a space at
        // the end to the StringBuilder variable "text"
        while (!in.atEOS()) {
            text.append(in.nextLine().toLowerCase() + " ");
        }
        // converts text to a String and returns it
        return text.toString();
    }

    /**
     * Reads a text file and returns either a separator or a word and the
     * position of the last read character in a form of a Map.
     *
     * @param s
     *            text to read
     *
     * @requires |s| > 0
     * @ensures word or separator is found
     * @return Map
     */
    private static String nextWordOrSeperator(String s) {
        String answer = "";
        if (s.length() > 0) {
            // Index variable to read s char by char
            int readIndex = 0;
            // Boolean flag used to stop the following while loop
            boolean atEndOfNext = false;
            // Stop loop if readIndex reaches end of s (or flag is set)
            while (readIndex < s.length() && !atEndOfNext) {
                if (separators().contains(s.charAt(readIndex))) {
                    // Increment readIndex if the char of s at that index is a separator
                    readIndex++;
                } else {
                    // Otherwise, stop the loop
                    atEndOfNext = true;
                }
            }
            if (readIndex == 0) {
                /*
                 * Bring readIndex to end of the following word since the
                 * readIndex hasn't increased
                 */

                // Reset flag variable
                atEndOfNext = false;
                // Stop loop if readIndex reaches end of s (or flag is set)
                while (readIndex < s.length() && !atEndOfNext) {
                    if (!separators().contains(s.charAt(readIndex))) {
                        // Increment readIndex if the char of s at that index is
                        // not a separator
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
     * Decide whether if the returned String from nextWordOrSeperator is a word
     * or a separator. Then checks to see if the word exist in the Map. If yes,
     * increase its value by 1. If not, add word with counter 1.
     *
     * @param fullText
     *            text to read from
     *
     * @requires |fullText| > 0
     * @ensures word is added into Map or counter is updated
     * @return Map
     */
    private static Map<String, Integer> mapEntries(String text) {
        Map<String, Integer> wordCounter = new Map1L<>();
        Set<Character> checkSep = separators();
        int position = 0;
        // while the value of position is less than the length of the text
        while (position < text.length()) {
            // get the next word/separator from the position'th character in fullText
            String next = nextWordOrSeperator(text.substring(position));

            // increase position by the length of the next word/separator
            position += next.length();

            if (!checkSep.contains(next.charAt(0))) {
                // we have a word
                if (wordCounter.hasKey(next)) {
                		// this word has been seen before, so increase it's count by 1
                    int count = wordCounter.value(next);
                    count++;
                    wordCounter.replaceValue(next, count);
                } else {
                		// this is the first time we have seen this word, so put it in the map with value 1
                    wordCounter.add(next, 1);
                }
            }

        }
        // returns the completed Map with all words and their counts
        return wordCounter;
    }

    /**
     * Reduces the map according to the max count and sorts it from the highest
     * word count to the lowest word count.
     *
     * @param m
     *            the map to be examined
     * @param max
     *            number of maximum tags
     *
     * @requires |map| > 0
     * @ensures map is reduced according to the maximum tag amount
     */
    public static void reduce(Map<String, Integer> m, int max) {
    		// Make 2 maps and a sorting machine
        Map<String, Integer> copy = m.newInstance();
        Comparator<Integer> ci = new IntegerLT();
        SortingMachine<Integer> sort = new SortingMachine1L<>(ci);
        
        // Remove all pairs from the map
        while (m.size() > 0) {
        		// Add the values in the map to the sorting machine
            Pair<String, Integer> tempPair = m.removeAny();
            sort.add(tempPair.value());
            // Add the pairs of the map to the copy map
            copy.add(tempPair.key(), tempPair.value());
        }
        // Change sorting machine to extraction mode
        sort.changeToExtractionMode();
        // Counter to get the first i entries in sort
        int i = max;
        while (i > 0) {
        		// Get the next highest value
            int nextHighest = sort.removeFirst();
            // Get the key corresponding to the next highest value
            String key = copy.key(nextHighest);
            // Add the key/value pair to the map 
            m.add(key, nextHighest);
            if (i == max) {
            		// Set static variable maxCount to the first value taken from sort
                maxCount = nextHighest;
            } else if (i == 1) {
            		// Set static variable minCount to the last value taken from sort
                minCount = nextHighest;
            }
            // Decrement counter
            i--;
        }
    }

    /**
     * Sorts the map in an alphabetical order and returning it as a
     * SortingMachine.
     *
     * @param m
     *            the map to be examined
     *
     * @requires |map| > 0
     * @ensures map is sorted alphabetically
     * @return sorted keys
     */
    public static SortingMachine<String> sortedKeys(Map<String, Integer> m) {
    		// Make a new map and a sorting machine
        Map<String, Integer> copy = m.newInstance();
        Comparator<String> cs = new StringLT();
        SortingMachine<String> sort = new SortingMachine1L<String>(cs);
        // Remove all pairs from the map
        while (m.size() > 0) {
            Pair<String, Integer> p = m.removeAny();
            // Add the key to the sorting machine
            sort.add(p.key());
            // Add the pair to the copy map
            copy.add(p.key(), p.value());
        }
        // Restore m by transferring from copy
        m.transferFrom(copy);
        // Change the sorting machine to extraction mode
        sort.changeToExtractionMode();
        // Return the sorting machine
        return sort;
    }

    /**
     * Does the calculation for the font sizes and assigns each word a size
     * according to the calculation.
     *
     * @param m
     *            map
     * @param sM
     *            sorted keys in a SortingMachine component
     * @param out
     *            outputs into the text file
     *
     * @requires |map| > 0, |sM| > 0
     * @ensures each word gets a specific font size in the HTML file
     */
    public static void writeCloud(Map<String, Integer> m,
            SortingMachine<String> sort, SimpleWriter out) {
    		// Get the range between the largest and smallest count values in the map
        int countRange = maxCount - minCount;
        while (sort.size() > 0) {
        		// Get the next key
            String key = sort.removeFirst();
            // Get the value for this key
            int value = m.value(key);

            // Calculate the font size for this value
            int offset = value - minCount;
            int fOffset = (offset * FONTSIZEAMOUNT) / countRange;
            int fSize = FONTSIZEMIN + fOffset;

            // Write the line corresponding to this entry into the html file
            out.println("<span style=\"cursor:default\" class=\"f" + fSize
                    + "\" title=\"count: " + value + "\">" + key + "</span>");
        }
    }

    /**
     * Outputs the footer tags for the HTML file.
     *
     *
     * @param out
     *            outputs to the current text
     */
    public static void footer(SimpleWriter out) {
        // outputs these into the file
        out.println("</p>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
        out.close();
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments; unused here
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        // Prompts the user to input the file name including the file path and
        // extension
        out.println(
                "Input the file name with the save path and file extension.");
        out.println("Example: data/example.txt");
        // Saves the input into a String
        String fileName = in.nextLine();
        SimpleReader fileIn = new SimpleReader1L(fileName);

        // Prompts the user to input tjhe save file name, path and extension.
        out.println(
                "Input the output file name with the save path and file extension.");
        out.println("Example: data/example.html");
        // Saves the input into a String
        String outFileName = in.nextLine();
        SimpleWriter fileSave = new SimpleWriter1L(outFileName);

        // Prompts the user to input the maximum number of tags
        out.print("Please input the maximum number of tags: ");
        int maxTag = in.nextInteger();

        // calling the method to start a header for the HTML
        header(fileSave, fileName, maxTag);

        // calling the method to return the text file as a String
        String returnedWord = returnWord(fileIn);

        // calling the method to check if the next word is a word or a
        // separator and returns a map with the word its counts
        Map<String, Integer> wordCounter = mapEntries(returnedWord);

        // calling a method to sort it from the highest word
        // count to the lowest word count.
        reduce(wordCounter, maxTag);

        // calling a method to sort the map alphabetically
        SortingMachine<String> sortedKeys = sortedKeys(wordCounter);

        // calling a method to write each word with its specified font size to
        // HTML file
        writeCloud(wordCounter, sortedKeys, fileSave);

        // calling the method to end the HTML file
        footer(fileSave);

        in.close();
        out.close();

    }

}
