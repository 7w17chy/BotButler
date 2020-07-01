package de.m_n_n.BotButler;

import java.lang.Thread;

public class Sender extends Thread {
	Queue m_sendQueue;

	Sender(Queue queue) {
		m_sendQueue = queue;
	}

	/*@Override
	public void run() {
		int pos = -1;
		while (true) {
			pos = m_sendQueue.incrementCursor();
			if (m_sendQueue.isOccupiedAt(pos)) {
				m_sendQueue.executeOn(pos, (elem) -> {
					if (elem.getElement() instanceof Sendable) {
						Sendable send = (Sendable) elem.getElement();
						String send_val = send.getSendableContent();
						send.m_channel.sendMessage(send_val).queue();
					}

					elem.markDone();
				});

				continue;
			}

			try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
		}
	} */
	
	@Override
	public void run() {
		int pos = 0;
		while (true) {
			while (!(m_sendQueue.isOccupiedAt(pos)))
				try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
			
			do {
				m_sendQueue.executeOn(pos, (elem) -> {
					if (elem.getElement() instanceof Sendable) {
						Sendable send = (Sendable) elem.getElement();
						String send_val = send.getSendableContent();
						send.m_channel.sendMessage(send_val).queue();
					}

					elem.markDone();
				});
				pos = m_sendQueue.incrementCursor();
			} while (m_sendQueue.isOccupiedAt(pos));
			pos = 0;
		}
	}
}