package components.queue;

import components.queue.QueueSecondary;
import components.sequence.Sequence;
import components.sequence.Sequence1L;
import java.util.Iterator;


/**
 * {@code Queue} represented as a {@code Sequence} of entries, with
 * implementations of primary methods.
 * 
 * @param <T>
 *            type of {@code Queue} entries
 * @correspondence this = $this.entries
 */
public class Queue3<T> extends QueueSecondary<T> {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Entries included in {@code this}.
     */
    private Sequence<T> entries;

    /**
     * Creator of initial representation.
     */
    private void createNewRep() {
        this.entries = new Sequence1L<T>();
    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public Queue3() {
        this.createNewRep();
    }
    
    /*
     * Secondary instance methods ---------------------------------------------
     */
    /**
     * Reports the front of {@code this}.
     * 
     * @return the front entry of {@code this}
     * @aliases reference returned by {@code front}
     * @requires this /= <>
     * @ensures <front> is prefix of this
     */
    @Override
    public T front() {
        assert this.length() > 0 : "Violation of: this /= <>";
     
        return this.entries.entry(0);
     
    }

    /*
     * Standard methods -------------------------------------------------------
     */

	@Override
	public void clear() {
		while (this.length() > 0) {
			this.dequeue();
		}
	}

	@Override
	public Queue<T> newInstance() {
		return new Queue3<T>();
	}

	@Override
	public void transferFrom(Queue<T> source) {
		this.clear();
		while (source.length() > 0) {
			this.enqueue(source.dequeue());
		}
		this.flip();
		
	}

    /*
     * Kernel methods ---------------------------------------------------------
     */

    @Override
    public final void enqueue(T x) {
        assert x != null : "Violation of: x is not null";

        this.entries.add(this.length(), x);

    }

    @Override
    public final T dequeue() {
        assert this.length() > 0 : "Violation of: this /= <>";

        return this.entries.remove(0);
    }

    @Override
    public final int length() {
        return this.entries.length();
    }

	@Override
	public Iterator<T> iterator() {
		return this.entries.iterator();
	}

}