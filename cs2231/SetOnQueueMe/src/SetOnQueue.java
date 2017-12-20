import components.simplereader.SimpleReader;
import components.queue.Queue1L;
import components.queue.Queue;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Put a short phrase describing the program here.
 * 
 * @author Put your name here
 * 
 */
public final class SetOnQueue {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private SetOnQueue() {
    }
    
    /**
     * Finds {@code x} in {@code q} and, if such exists, moves it to the front
     * of {@code q}.
     * 
     * @param <T>
     *            type of {@code Queue} entries
     * @param q
     *            the {@code Queue} to be searched
     * @param x
     *            the entry to be searched for
     * @updates q
     * @ensures <pre>
     * perms(q, #q)  and
     * if <x> is substring of q
     *  then <x> is prefix of q
     * </pre>
     */
    public static <T> void moveToFront(Queue<T> q, T x) {
    		boolean isSubstring = false;
    		int dqs = 1;
    		for (T e: q) {
    			if (e == x) {
    				isSubstring = true;
    			} else {
    				dqs++;
    			}
    		}
    		if (isSubstring && dqs > 1) {
    			Queue<T> temp = q.newInstance();
    			Queue<T> newQ = q.newInstance();
    			while (dqs > 1) {
    				temp.enqueue(q.dequeue());
    				dqs--;
    			}
    			newQ.enqueue(q.dequeue());
    			temp.append(q);
    			newQ.append(temp);
        		q.transferFrom(newQ);
    		}
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
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
