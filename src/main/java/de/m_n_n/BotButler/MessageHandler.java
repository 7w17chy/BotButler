package de.m_n_n.BotButler;

import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class MessageHandler extends ListenerAdapter {
	
	private Queue m_jobs;
	private Queue m_send_queue;
	
	MessageHandler(Queue jobs, Queue send_queue) {
		m_jobs = jobs;
		m_send_queue = send_queue;
	}
	
	@Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String message_content = event.getMessage().getContentRaw();
        switch (message_content) {
            case "!meme":
                m_jobs.add(new ApiRequest("memeurl", event.getChannel(), (obj) -> {
                    return new String("Got obj: " + obj.toString());
                }));
                break;
            default:
                if (message_content.startsWith("!"))
                    event.getChannel().sendMessage("Sorry, unknown command!");
        }
    }
}
