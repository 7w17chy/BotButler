package de.m_n_n.bot_butler;

import java.util.Vector;

protected class Queue {
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

	public void add(Job job) {}

	private enum Occupied {
		OCCUPIED,
		FREE	
	}

	private static class QueueElement<T extends Executable> {
		public Occupied occupied;
		private T element;

		QueueElement(T job) {
			element = job;
			occupied = Occupied.OCCUPIED;
		}

		public void markDone() {
			occupied = Occupied.FREE;
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

		// get the index of the next free element
		public int nextFree() {
			for (int i = 0; i < super.jobs.size(); i++) {
				if (super.jobs.elementAt(i).occupied == Occupied.FREE)
					return i;
			}

			// no free element available, maybe extend list?
			return -1;
		}
	}
}
