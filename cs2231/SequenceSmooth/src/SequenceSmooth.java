import components.sequence.Sequence;

/**
 * Implements method to smooth a {@code Sequence<Integer>}.
 * 
 * @author Sean O'Donnell
 * 
 */
public final class SequenceSmooth {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private SequenceSmooth() {
    }

    /**
     * Smooths a given {@code Sequence<Integer>}.
     * 
     * @param s1
     *            the sequence to smooth
     * @requires |s1| >= 1
     * @ensures <pre>
     * |smooth| = |s1| - 1  and 
     *  for all i, j: integer, a, b: string of integer
     *      where (s1 = a * <i> * <j> * b)
     *    (there exists c, d: string of integer
     *       (|c| = |a|  and
     *        smooth = c * <(i+j)/2> * d))
     * @returns the smoothed {@code Sequence<Integer>}
     * </pre>
     */
    public static Sequence<Integer> smooth(Sequence<Integer> s1) {
        assert s1 != null : "Violation of: s1 is not null";
        assert s1.length() >= 1 : "|s1| >= 1";
        
//        Sequence<Integer> smooth = s1.newInstance();
//        if (s1.length() > 1) {
//        		int thisEntry = s1.remove(0);
//        		int nextEntry = s1.entry(0);
//        		int answer = average(thisEntry, nextEntry);
//        		smooth = smooth(s1);
//        		smooth.add(0, answer);
//        		s1.add(0, thisEntry);
//        }
//        return smooth;

      Sequence<Integer> ans = s1.newInstance();
      for (int i = 0; i < s1.length() - 1; i++) {
      		int thisEntry = s1.entry(i);
      		int nextEntry = s1.entry(i + 1);
      		int answer = average(thisEntry, nextEntry);
      		ans.add(i, answer);
      }
      return ans;
    }
    
    /**
     * Returns the integer average of two given {@code int}s.
     * 
     * @param j
     *            the first of two integers to average
     * @param k
     *            the second of two integers to average
     * @return the integer average of j and k
     * @ensures average = (j+k)/2
     */
    public static int average(int j, int k) {
    		int answer;
    		if (j == -k) {
    			answer = 0;
    		} else {
    			answer = (j / 2) + (k / 2);
    			if (j % 2 != 0 && k % 2 != 0) {
    				answer++;
    			}
    		}
    	    return answer;
    }

}