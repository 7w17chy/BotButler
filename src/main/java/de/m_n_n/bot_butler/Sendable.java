package de.m_n_n.bot_butler;

public abstract class Sendable {
	private int m_channel_id;

	public abstract String parse();

	public interface Parseable {
		public String parse();
	}
}
