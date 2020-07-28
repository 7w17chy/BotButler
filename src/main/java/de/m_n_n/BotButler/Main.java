package de.m_n_n.BotButler;

import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import java.util.HashMap;

class Main extends ListenerAdapter {
    public static void main(String[] args) {
    	 // create thread safe queues
        Queue jobs = new Queue();
        Queue sender_queue = new Queue(jobs);
        jobs.addOther(sender_queue);

        // handle all incoming jobs (meme requests, etc.)
        Executor job_handler = new Executor(jobs, sender_queue);
        job_handler.start();

        // sender sends the results from executor
        Executor send_handler = new Executor(sender_queue, jobs);
        send_handler.start();

        MessageHandler handler = new MessageHandler(jobs, sender_queue,
                new HashMap<String, MessageHandler.MessageReceivedEx>());
    	try {
            JDA jda = new JDABuilder("NzAwNjY5NDA1MjMwNzkyNzc1.XpmTPA.ngsmAJUIz0522szXi67sNuzOmEM")
                .addEventListeners(handler)
                .build();

            // block until jda is ready
            jda.awaitReady();
        } catch (Exception e) {
            e.printStackTrace();
        }

    	handler.addCommand("!hello", (event, jobsq, sendq) -> {
            event.getChannel().sendMessage("Ei gude, wie?").queue();
        });

    	handler.addCommand("!meme", (event, jobsq, sendq) -> {
    	    jobsq.add(new ApiRequest("https://meme-api.herokuapp.com/gimme", event.getChannel(), (obj) -> {
    	        return obj.getString("url");
            }));
        });
    }
}
