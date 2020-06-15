package de.m_n_n.bot_butler;

import java.lang.Thread;

public class Job extends Thread {
	private Queue m_jobQueue;

	//@Override
	//public void run() {
	//	// increment cursor and check for new
	//	// if new 
	//	//   execute 
	//	//   continue (in that case we want to immediatly check for a new entry)
	//	// sleep one millisecond
	//	// goto 1
	//}
	
	@Override
	public void run() {
		while (true) {
			Queue.QueueElement next = m_jobQueue.next();
			if (next.isOccupied()) {
				ExecuteResult result = next.getElement().execute();
				next.markDone();
				// TODO: send to send-thread
				continue;
			}
			// sleep one millisecond
			try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
		} 
	}

	public interface Executable {
		public ExecuteResult execute();
	}

	public static abstract class ExecuteResult {
		private boolean m_success;

		public abstract boolean didSucceed();
	}
}
