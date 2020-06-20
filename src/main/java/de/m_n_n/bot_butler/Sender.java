package de.m_n_n.bot_butler;

import java.lang.Thread;

public class Sender extends Thread {
	Queue m_sendQueue;

	Sender(Queue queue) {
		m_sendQueue = queue;
	}

	@Override
	public void run() {
		int pos = -1;
		while (true) {
			try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
		} 
	}
}
