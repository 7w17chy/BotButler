package de.m_n_n.bot_butler;

import java.lang.Thread;

public class Job extends Thread {
	private Queue m_jobQueue;

	@Override
	public void run() {
		while (true) {
			//Queue.QueueElement next = m_jobQueue.next();
			//if (next.isOccupied()) {
			//	ExecuteResult result = next.getElement().execute();
			//	next.markDone();
			//	// TODO: send to send-thread
			//	continue;
			//}
			//// sleep one millisecond
			// 
			// what's happening:
			// check for new element (increment cursor)
			// if new:
			//	  execute()
			//	  continue
			// sleep for a millisecond
			try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
		} 
	}
}
