import components.array.Array;
import components.array.Array1L;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Lets the user test the {@code hashCode(String)} method, by reading text lines
 * from a file (whose name is supplied by the user), and then outputting the
 * distribution of lines into buckets.
 *
 * @author Put your name here
 *
 */
public final class HashingExploration {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private HashingExploration() {
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
        assert b > 0 : "Violation of: b > 0";

        int rem = a % b;
        if (a < 0 && rem != 0) {
        		rem += b;
        }
        return rem;
    }

    /**
     * Returns a hash code value for the given {@code String}.
     *
     * @param s
     *            the {@code String} whose hash code is computed
     * @return a hash code value for the given {@code String}
     * @ensures hashCode = [hash code value for the given String]
     */
    private static int hashCode(String s) {
        assert s != null : "Violation of: s is not null";

        int h = 0;
        for (int i = 0; i < s.length(); i++) {
        		h += s.charAt(i) ^ (i + 1);
        }
        return h;
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
         * Get hash table size and file name.
         */
        out.print("Hash table size: ");
        int hashTableSize = in.nextInteger();
        out.print("Text file name: ");
        String textFileName = in.nextLine();
        /*
         * Set up counts and counted.
         */
        Array<Integer> counts = new Array1L<Integer>(hashTableSize);
        /*
         * Initialize every element in the counts array
         */
        
        for (int i = 0; i < hashTableSize; i++) {
        		counts.setEntry(i, 0);
        }
        
        Set<String> counted = new Set1L<String>();
        /*
         * Get some lines of input, hash them, and record counts.
         */
        SimpleReader textFile = new SimpleReader1L(textFileName);
        while (!textFile.atEOS()) {
            String line = textFile.nextLine();
            if (!counted.contains(line)) {
                int bucket = mod(hashCode(line), hashTableSize);
                counts.setEntry(bucket, counts.entry(bucket) + 1);
                counted.add(line);
            }
        }
        textFile.close();
        /*
         * Report results.
         */
        out.println();
        out.println("Bucket\tHits\tBar");
        out.println("------\t----\t---");
        for (int i = 0; i < counts.length(); i++) {
			int count = counts.entry(i);
	        out.print(i + "\t" + count + "\t");
            for (int j = 0; j < count; j++) {
                out.print("*");
            }
            out.println();
        }
        out.println();
        out.println("Total:\t" + counted.size());
        
        in.close();
        out.close();
    }
}


