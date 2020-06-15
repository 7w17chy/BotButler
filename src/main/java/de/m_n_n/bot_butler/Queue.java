package de.m_n_n.bot_butler;

import java.util.Vector;

class Queue {
	private Vector<QueueElement> jobs;
	private Cursor cursor;

	Queue(int vec_size, int cursor_pos) {
		jobs = new Vector<QueueElement>(vec_size);
		cursor = new Cursor(cursor_pos);
	}

	Queue() {
		jobs = new Vector<QueueElement>(10);
		cursor = new Cursor(0);
	}

	public void add(Job.Executable job) {
		int next_free = nextFree();
		if (next_free >= 0) {
			jobs.set(next_free, new QueueElement(job));
			return;
		}
		
		// there's no free element
		jobs.add(new QueueElement(job));
	}

	public int nextFree() {
		for (int i = 0; i < jobs.size(); i++) {
			if (jobs.elementAt(i).occupied == Occupied.FREE)
				return i;
		}

		// no free element available, maybe extend list?
		return -1;
	}

	public QueueElement next() {
		return jobs.elementAt(cursor.increment());
	}

	public QueueElement nextJob() {
		for (int i = 0; i < jobs.size(); i++) {
			if (jobs.elementAt(i).occupied == Occupied.OCCUPIED) {
				jobs.elementAt(i).markDone();
				return jobs.elementAt(i);
			}
		}

		return null;
	}

	private enum Occupied {
		OCCUPIED,
		FREE	
	}

	public static class QueueElement {
		public Occupied occupied;
		private Job.Executable element;

		QueueElement(Job.Executable job) {
			element = job;
			occupied = Occupied.OCCUPIED;
		}

		public void markDone() {
			occupied = Occupied.FREE;
		}

		public Job.Executable getElement() {
			return element;
		}

		public boolean isOccupied() {
			return (occupied == Occupied.FREE) ? false : true;
		}
	}

	// we need to extend Queue in order to get access to its fields
	private static class Cursor extends Queue {
		private int m_index;

		Cursor(int index) {
			m_index = index;
		}
		
		Cursor() {
			m_index = 0;
		}

		public int increment() {
			if ((m_index + 1) >= super.jobs.size()) {
				m_index = 0;
				return m_index;
			}

			m_index++;
			return m_index;
		}
	}
}
