package de.m_n_n.bot_butler;

import java.lang.Thread;

public class Job extends Thread {
	private Queue m_jobQueue;

	@Override
	public void run() {
		for (;;) {
			System.out.print(".");
		}
	}

	public interface Executable {
		public ExecuteResult execute();
	}

	public static class ExecuteResult {
		private Sendable m_result;

		ExecuteResult(Sendable result) {
			m_result = result;
		}
	}
}
