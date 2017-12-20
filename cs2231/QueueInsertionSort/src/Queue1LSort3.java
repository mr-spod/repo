import java.util.Comparator;

import components.queue.Queue;
import components.queue.Queue1L;

/**
 * Layered implementations of secondary method {@code sort} for
 * {@code Queue<String>}.
 *
 * @param <T>
 *            type of {@code Queue} entries
 * @mathdefinitions <pre>
 * IS_TOTAL_PREORDER (
 *   r: binary relation on T
 *  ) : boolean is
 *  for all x, y, z: T
 *   ((r(x, y) or r(y, x))  and
 *    (if (r(x, y) and r(y, z)) then r(x, z)))
 *
 * IS_SORTED (
 *   s: string of T,
 *   r: binary relation on T
 *  ) : boolean is
 *  for all x, y: T where (<x, y> is substring of s) (r(x, y))
 * </pre>
 */
public final class Queue1LSort3<T> extends Queue1L<T> {

    /**
     * No-argument constructor.
     */
    public Queue1LSort3() {
        super();
    }

    /**
     * Inserts the given {@code T} in the {@code Queue<T>} sorted according to
     * the given {@code Comparator<T>} and maintains the {@code Queue<T>}
     * sorted.
     *
     * @param <T>
     *            type of {@code Queue} entries
     * @param q
     *            the {@code Queue} to insert into
     * @param x
     *            the {@code T} to insert
     * @param order
     *            the {@code Comparator} defining the order for {@code T}
     * @updates q
     * @requires <pre>
     * IS_TOTAL_PREORDER([relation computed by order.compare method])  and
     * IS_SORTED(q, [relation computed by order.compare method])
     * </pre>
     * @ensures <pre>
     * perms(q, #q * <x>)  and
     * IS_SORTED(q, [relation computed by order.compare method])
     * </pre>
     */
    private static <T> void insertInOrder(Queue<T> q, T x,
            Comparator<T> order) {
        assert q != null : "Violation of: q is not null";
        assert order != null : "Violation of: order is not null";
        if (q.length() > 0) {
    			int rotates = 0;
    			boolean hasBeenInserted = false;
    			while (rotates < q.length() && !hasBeenInserted) {
    				if (order.compare(q.front(), x) < 0) {
    					q.rotate(1);
    					rotates++;
    				} else {
    					q.enqueue(x);
    					q.rotate(-(rotates+1));
    					hasBeenInserted = true;
    				}
    			}
    			if (!hasBeenInserted) {
    				q.enqueue(x);
    			}
        } else {
        		q.enqueue(x);
        }

    }

//    @Override
//    public void sort(Comparator<T> order) {
//        assert order != null : "Violation of: order is not null";
//
//        Queue<T> temp = this.newInstance();
//        while (this.length() > 0) {
//        		insertInOrder(temp, this.dequeue(), order);
//        }
//        this.transferFrom(temp);
//
//    }
    
    /**
     * Partitions {@code q} into two parts: entries no larger than
     * {@code partitioner} are put in {@code front}, and the rest are put in
     * {@code back}.
     * 
     * @param <T>
     *            type of {@code Queue} entries
     * @param q
     *            the {@code Queue} to be partitioned
     * @param partitioner
     *            the partitioning value
     * @param front
     *            upon return, the entries no larger than {@code partitioner}
     * @param back
     *            upon return, the entries larger than {@code partitioner}
     * @param order
     *            ordering by which to separate entries
     * @clears q
     * @replaces front, back
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures <pre>
     * perms(#q, front * back)  and
     * for all x: T where (<x> is substring of front)
     *  ([relation computed by order.compare method](x, partitioner))  and
     * for all x: T where (<x> is substring of back)
     *  (not [relation computed by order.compare method](x, partitioner))
     * </pre>
     */
    private static <T> void partition(Queue<T> q, T partitioner,
            Queue<T> front, Queue<T> back, Comparator<T> order) {
    		while (q.length() > 0) {
    			if (order.compare(partitioner, q.front()) >= 0) {
        			front.enqueue(q.dequeue());
    			} else {
    				back.enqueue(q.dequeue());
    			}
    		}
    }
    
    /**
     * Sorts {@code this} according to the ordering provided by the
     * {@code compare} method from {@code order}.
     * 
     * @param order
     *            ordering by which to sort
     * @updates this
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures <pre>
     * perms(this, #this)  and
     * IS_SORTED(this, [relation computed by order.compare method])
     * </pre>
     */
    public void sort(Comparator<T> order) {
        if (this.length() > 1) {
            /*
             * Dequeue the partitioning entry from this
             */
        		T part = this.dequeue();
     
            /*
             * Partition this into two queues as discussed above
             * (you will need to declare and initialize two new queues)
             */
        		Queue<T> front = this.newInstance();
        		Queue<T> back = this.newInstance();
        		partition(this, part, front, back, order);
     
            /*
             * Recursively sort the two queues
             */
        		front.sort(order);
        		back.sort(order);
     
            /*
             * Reconstruct this by combining the two sorted queues and the
             * partitioning entry in the proper order
             */
        		this.transferFrom(front);
        		this.enqueue(part);
        		this.append(back);
     
        }
    }

}
