package de.m_n_n.bot_butler;

import java.lang.Thread;

public class Job extends Thread {
	private Queue m_jobQueue;

	@Override
	public void run() {
		// increment cursor and check for new
		// if new 
		//   execute 
		//   continue (in that case we want to immediatly check for a new entry)
		// sleep one millisecond
		// goto 1
	}

	public interface Executable {
		public ExecuteResult execute();
	}

	public static class ExecuteResult {
		private Sendable m_result;
		public boolean m_success;

		ExecuteResult(Sendable result, boolean success) {
			m_result = result;
			m_success = success;
		}
	}
}
