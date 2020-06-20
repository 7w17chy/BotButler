package de.m_n_n.bot_butler;

import net.dv8tion.jda.api.entities.MessageChannel;

public abstract class Sendable {
	MessageChannel m_channel;
	public abstract String getSendableContent();
}
