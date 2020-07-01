package de.m_n_n.BotButler;

import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.MessageBuilder;
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
            	System.out.println("Got new meme request");
                m_jobs.add(new ApiRequest("https://meme-api.herokuapp.com/gimme", event.getChannel(), (obj) -> {
                	String ret = null;
                    try {
                    	ret = obj.getString("postLink");
                    	System.out.println("Got object: " + ret);
                    } catch (Exception e) {
                    	e.printStackTrace();
                    	ret = "Klitzekleiner Fehler beim Parsen der Server-Response. Nicht deine Schuld. Sag bitte mal Max bescheid :)";
                    }
                    
                    return ret;
                }));
                break;
            case "!hello":
            	event.getChannel().sendMessage("Hello!").queue();
            	System.out.println("Hello, world!");
            	break;
            default:
                if (message_content.startsWith("!"))
                    event.getChannel().sendMessage("Sorry, unknown command!").queue();
        }
    }
}
