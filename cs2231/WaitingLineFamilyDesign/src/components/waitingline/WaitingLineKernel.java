package components.waitingline;

/**
 * @author JS
 * @author Sean
 * @author Will
 * @author Kyle
 * @author Eason
 * @author Marco
 */

public interface WaitingLineKernel<T> {

    /**
     * Adds {@code x} to the end of {@code this}.
     *
     * @param x
     *            the entry to be added
     * @aliases reference {@code x}
     * @updates this
     * @ensures this = #this * <x>
     */
    void enqueue(T x);

    /**
     * Removes {@code q} from {@code this}.
     *
     * @param q
     *            entry identifier
     * @updates this
     * @requires this /= <> && q is in this
     * @ensures |this| = |#this| -1
     */
    void dequeue(T q);

    /**
     * Reports length of {@code this}.
     *
     * @return the length of {@code this}
     * @ensures length = |this|
     */
    int length();

}
