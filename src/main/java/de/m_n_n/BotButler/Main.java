package de.m_n_n.BotButler;

import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

class Main extends ListenerAdapter {
    public static void main(String[] args) {
    	 // create thread safe queues
        Queue jobs = new Queue();
        Queue sender_queue = new Queue();

        // handle all incoming jobs (meme requests, etc.)
        Executor job_handler = new Executor(jobs, sender_queue);
        job_handler.start();

        // sender sends the results from executor
        Sender sender = new Sender(sender_queue);
        sender.start();
    	
    	try {
            JDA jda = new JDABuilder("NjczOTAwODE0MTQ1OTQ1NjIw.Xvi5gg.z3YumDB7BXhDIuIcTSAGuCHAO8U")
                .addEventListeners(new MessageHandler(jobs, sender_queue))
                .build();

            // block until jda is ready
            jda.awaitReady();
        } catch (Exception e) {
            e.printStackTrace();
        }
       
    }
}
