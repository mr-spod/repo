package components.waitingline;

import components.queue.QueueSecondary;
import components.queue.Queue;

public class WaitingLineSecondary<T> implements WaitingLine<T> {

	private QueueSecondary<T> rep;
	
	@Override
	public void enqueue(T x) {
		this.rep.enqueue(x);
		
	}

	@Override
	public void dequeue(T q) {
		Queue<T> behindQ = this.rep.newInstance();
		T dqD = this.rep.dequeue();
		while (!dqD.equals(q)) {
			behindQ.enqueue(dqD);
			dqD = this.rep.dequeue();
		}
		behindQ.flip();
		this.rep.append(behindQ);
		
	}

	@Override
	public int length() {
		return this.rep.length();
	}

	@Override
	public T front() {
		return this.rep.front();
	}

	@Override
	public void append(WaitingLine<T> q) {
		WaitingLineSecondary<T> localQ = (WaitingLineSecondary<T>)q;
		this.rep.append(localQ.rep);
		localQ.rep.clear();
		
	}

	@Override
	public int requestPosition(T q) {
		// Returns 0 if q is first in line
		Queue<T> behindQ = this.rep.newInstance();
		T dqD = this.rep.dequeue();
		while (!dqD.equals(q)) {
			behindQ.enqueue(dqD);
			dqD = this.rep.dequeue();
		}
		int position = this.rep.length();
		behindQ.enqueue(dqD);
		behindQ.flip();
		this.rep.append(behindQ);
		return position;
	}

	@Override
	public boolean isInLine(T q) {
		boolean answer = false;
		Queue<T> behindQ = this.rep.newInstance();
		T dqD = this.rep.dequeue();
		while (this.rep.length() > 0 && !dqD.equals(q)) {
			behindQ.enqueue(dqD);
			dqD = this.rep.dequeue();
		}
		answer = this.rep.length() > 0 || dqD.equals(q);
		behindQ.enqueue(dqD);
		behindQ.flip();
		this.rep.append(behindQ);
		return answer;
	}

	@Override
	public T requestEntry(int p) {
		Queue<T> behindP = this.rep.newInstance();
		T dqD = this.rep.dequeue();
		while (this.rep.length() > p) {
			behindP.enqueue(dqD);
			dqD = this.rep.dequeue();
		}
		behindP.enqueue(dqD);
		behindP.flip();
		this.rep.append(behindP);
		
		return dqD;
	}
	
	public boolean equals(WaitingLineSecondary<T> w) {
		return this.rep.equals(w.rep);
	}
	
	public String toString() {
		String toString = "<";
		Queue<T> copy = this.rep.newInstance();
		while (this.rep.length() > 0) {
			T here = this.rep.dequeue();
			toString += here;
			if (this.rep.length() > 0) {
				toString += ", ";
			}
			copy.enqueue(here);
		}
		copy.flip();
		this.rep.transferFrom(copy);
		toString += ">";
		return toString;
	}
	
	public int hashCode() {
		return this.front().hashCode() % this.length();
	}
	
}
