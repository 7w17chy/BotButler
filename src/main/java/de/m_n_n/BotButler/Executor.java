package de.m_n_n.BotButler;

import java.lang.Thread;

public class Executor extends Thread {
	private Queue m_jobQueue;
	private	Queue m_sendQueue;

	Executor(Queue jobs, Queue send) {
		m_jobQueue = jobs;
		m_sendQueue = send;
	}

	@Override
	public void run() {
		// index in the vector of jobs
		int pos = 0;
		while (true) {
			// wait until new job comes in
			while (!(m_jobQueue.isOccupiedAt(pos)))
				try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
			
			// if a new job came in, execute it and check whether new ones came in after it (they'll take the following
			// positions
			do {
				m_jobQueue.executeOn(pos, (elem) -> {
					ApiRequest.ApiResponse resp = null;
					if (elem.getElement() instanceof ApiRequest) {
						ApiRequest req = (ApiRequest) elem.getElement();
						resp = req.executeRequest();
					} // else if (elem.getElement() instanceof Poll) {...}

					elem.markDone();
					m_sendQueue.add(resp);
				});
				pos = m_jobQueue.incrementCursor();
			} while (m_jobQueue.isOccupiedAt(pos));
			// if all jobs have been executed, we can safely go back to position 0, since that's where the next
			// job will be put
			pos = 0;
		}
	}
}
