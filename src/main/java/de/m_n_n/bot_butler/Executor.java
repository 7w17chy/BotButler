package de.m_n_n.bot_butler;

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
		int pos = -1;
		while (true) {
			pos = m_jobQueue.incrementCursor();
			if (m_jobQueue.isOccupiedAt(pos)) {
				m_jobQueue.executeOn(pos, (elem) -> {
					ApiRequest.ApiResponse resp = null;

					if (elem.getElement() instanceof ApiRequest) {
						ApiRequest req = (ApiRequest) elem.getElement();
						resp = req.executeRequest();
					}

					m_sendQueue.add(resp);
					elem.markDone();
				});

				continue;
			}

			try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
		} 
	}
}
