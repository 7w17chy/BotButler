package de.m_n_n.BotButler;

import net.dv8tion.jda.api.entities.MessageChannel;

public abstract class Sendable {
	public abstract MessageChannel getMessageChannel();
	public abstract String getSendableContent();
}
