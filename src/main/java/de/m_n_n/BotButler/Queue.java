package de.m_n_n.BotButler;

import javax.swing.text.html.Option;
import java.util.Vector;
import java.util.Optional;

class Queue {
	private Vector<Optional<Executable>> m_jobs;
	private int m_cursor;
	private Queue m_self;
	private Queue m_other;

	Queue() {
		m_jobs = new Vector<Optional<Executable>>(10, 10);
		m_cursor = 0;
		m_self = this;
		m_other = null;

		for (int i = 0; i < 10; i++)
			m_jobs.set(i, Optional.empty());
	}

	Queue(Queue other) {
		m_jobs = new Vector<Optional<Executable>>(10, 10);
		m_cursor = 0;
		m_self = this;
		m_other = other;

		for (int i = 0; i < 10; i++)
			m_jobs.set(i, Optional.empty());
	}

	public interface Executable {
		void execute(Queue self, Queue other);
	}

	public synchronized void add(Executable job) {
		int free_pos = nextFree();
		m_jobs.set(free_pos, Optional.of(job));
	}

	public void addOther(Queue other) {
		m_other = other;
	}

	public synchronized void executeOn(int which) {
		if (which >= m_jobs.size() || which < 0) {
			System.out.println("Which in function executeOn is out of bounds: " + which);
			return;
		}

		m_jobs.elementAt(which).ifPresent((ex) -> {
			ex.execute(m_self, m_other);
		});
		m_jobs.set(which, Optional.empty());
	}

	public synchronized int nextFree() {
		int next_free = 0;
		for (Optional opt : m_jobs) {
			if (opt.isPresent())
				break;
			next_free++;
		}
		return next_free;
	}

	public synchronized boolean isOccupied(int where) {
		return m_jobs.elementAt(where).isPresent();
	}

	public synchronized int incrementCursor() {
		int nextpos = m_cursor + 1;
		if (nextpos >= m_jobs.size())
			m_cursor = 0;

		m_cursor++;
		return m_cursor;
	}
}
