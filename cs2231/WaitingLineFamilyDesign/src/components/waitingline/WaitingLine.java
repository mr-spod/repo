package components.waitingline;

/**
 * @author JS
 * @author Sean
 * @author Will
 * @author Kyle
 * @author Eason
 * @author Marco
 */

/**
 * {@code WaitingLineKernel} enhanced with secondary methods.
 *
 * @param <T>
 *            type of {@code WaitingLine} entries
 *
 */
public interface WaitingLine<T> extends WaitingLineKernel<T> {

    /**
     * Reports the front of {@code this}.
     *
     * @return the front entry of {@code this}
     * @aliases reference returned by {@code front}
     * @requires this /= <>
     * @ensures <front> is prefix of this
     */
    T front();

    /**
     * Concatenates ("appends") {@code q} to the end of {@code this}.
     *
     * @param q
     *            the {@code WaitingLine} to be appended to the end of
     *            {@code this}
     * @updates this
     * @clears q
     * @ensures this = #this * #q
     */
    void append(WaitingLine<T> q);

//    /**
//     * Removes {@code q} from {@code this}.
//     *
//     * @param q
//     *            the {@code WaitingLine} to be removed from {@code this}
//     * @updates this
//     * @ensures |this| = |#this| - |q|
//     */
//    void removeLine(WaitingLine<T> q);

    /**
     * Returns the position of {@code q}.
     *
     * @param q
     *            entry identifier
     *
     * @return position in the line
     */
    int requestPosition(T q);

    /**
     * Returns if {@code q} is in the line.
     *
     * @param q
     *            entry identifier
     * @return true or false
     *
     */
    boolean isInLine(T q);
    // If q is in line

    /**
     * Returns the entry of {@code q} at position {@code p}.
     *
     * @param p
     *            position
     * @requires |this| >= p
     * @return entry at position {@code p}
     */
    T requestEntry(int p);
    // Returns entry at position p

}
