package de.m_n_n.BotButler;

import java.util.Vector;

class Queue {
	private Vector<QueueElement> m_jobs;
	private int m_cursor;

	Queue(int vec_size, int cursor_pos) {
		m_jobs = new Vector<QueueElement>(vec_size, vec_size);
		m_cursor = 0;
		
		for (int i = 0; i < vec_size; i++)
			m_jobs.add(new QueueElement(null));
	}

	Queue() {
		m_jobs = new Vector<QueueElement>(10, 10);
		m_cursor = 0;
		
		for (int i = 0; i < 10; i++)
			m_jobs.add(new QueueElement(null));
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
		if (which >= m_jobs.size() || which < 0) {
			System.out.println("Which in function executeOn is out of bounds: " + which);
			return;
		}
		
		ex.execute(m_jobs.elementAt(which));
	}

	public synchronized int incrementCursor() {
		int nextpos = m_cursor + 1;
		if (nextpos >= m_jobs.size())
			m_cursor = 0;

		m_cursor++;
		return m_cursor;
	}

	public boolean isOccupiedAt(int i) {
		return (m_jobs.elementAt(i).isOccupied()) ? true : false;
	}

	private synchronized int nextFree() {
		int i = ((m_cursor + 1) >= m_jobs.size()) ? 0 : ++m_cursor;
		for (; i != m_cursor && m_jobs.elementAt(i).isOccupied(); i++)
			if (i == m_jobs.size())
				i = 0;

		return i;
	}

	private synchronized int nextOccupied() {
		int i = ((m_cursor + 1) > m_jobs.size()) ? 0 : m_cursor++;
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
