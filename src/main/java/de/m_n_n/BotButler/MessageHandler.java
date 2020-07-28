package de.m_n_n.BotButler;

import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.HashMap;

public class MessageHandler extends ListenerAdapter {
	
	private Queue m_jobs;
	private Queue m_send_queue;
	private HashMap<String, MessageReceivedEx> m_commands;
	
	MessageHandler(Queue jobs, Queue send_queue, HashMap<String, MessageReceivedEx> commands) {
		m_jobs = jobs;
		m_send_queue = send_queue;
		m_commands = commands;
	}

	public interface MessageReceivedEx {
	    public void execute(MessageReceivedEvent event, Queue jobs, Queue sender);
    }

	public void addCommand(String command, MessageReceivedEx ex) {
	    m_commands.put(command, ex);
    }

	@Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String message_content = event.getMessage().getContentRaw();
        if (m_commands.containsKey(message_content)) {
            m_commands.get(message_content).execute(event, m_jobs, m_send_queue);
            return;
        }

        if (message_content.startsWith("!"))
            event.getChannel().sendMessage("Sorry, den Befehl kenn' ich leider noch nicht...").queue();
    }
}
