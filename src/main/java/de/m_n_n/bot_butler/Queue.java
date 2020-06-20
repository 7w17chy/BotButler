package de.m_n_n.bot_butler;

import java.util.Vector;

class Queue {
	private Vector<QueueElement> m_jobs;
	private int m_cursor;

	Queue(int vec_size, int cursor_pos) {
		m_jobs = new Vector<QueueElement>(vec_size);
		int i = 0;
		m_cursor = 0;
		while (i < vec_size) {
			m_jobs.set(i, new QueueElement(null));
			i++;
		}
	}

	Queue() {
		m_jobs = new Vector<QueueElement>(10);
		int i = 0;
		m_cursor = 0;
		while (i < 10) {
			m_jobs.set(i, new QueueElement(null));
			i++;
		}
	}

	public synchronized <T> void add(T job) {
		int free_pos = nextFree();
		m_jobs.elementAt(free_pos).replace(free_pos, job);
	}

	public synchronized void executeOnFreeElement(Executable ex) {
		int pos = incrementCursor();

		if (!isOccupiedAt(pos))
			ex.execute(m_jobs.elementAt(pos));
	}

	public synchronized void executeOnOccupiedElement(Executable ex) {
		int pos = incrementCursor();

		if (isOccupiedAt(pos))
			ex.execute(m_jobs.elementAt(pos));
	}

	public synchronized void executeOn(int which, Executable ex) {
		ex.execute(m_jobs.elementAt(which));
	}

	public int incrementCursor() {
		int currpos = m_cursor;
		if (currpos++ > m_jobs.size())
			m_cursor = 0;

		m_cursor++;
		return m_cursor;
	}

	public boolean isOccupiedAt(int i) {
		return (m_jobs.elementAt(i).isOccupied()) ? true : false;
	}

	private int nextFree() {
		int i = m_cursor++;
		for (; i != m_cursor && m_jobs.elementAt(i).isOccupied(); i++)
			if (i == m_jobs.size())
				i = 0;

		return i;
	}

	private int nextOccupied() {
		int i = m_cursor++;
		for (; i != m_cursor && !(m_jobs.elementAt(i).isOccupied()); i++)
			if (i == m_jobs.size())
				i = 0;

		return i;
	}

	private enum Occupied {
		OCCUPIED,
		FREE	
	}

	public static class QueueElement<T> {
		public Occupied m_occupied;
		private T m_element;

		QueueElement(T job) {
			m_element = job;
			m_occupied = Occupied.OCCUPIED;
		}

		public void replace(int index, T new_job) {
			m_element = new_job;
			m_occupied = Occupied.OCCUPIED;
		}

		public void markDone() {
			m_occupied = Occupied.FREE;
		}

		public T getElement() {
			return m_element;
		}

		public boolean isOccupied() {
			return (m_occupied == Occupied.FREE) ? false : true;
		}
	}

	@FunctionalInterface
	public interface Executable {
		public void execute(QueueElement element);
	}
}
