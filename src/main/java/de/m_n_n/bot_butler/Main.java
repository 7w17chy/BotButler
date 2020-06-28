package de.m_n_n.bot_butler;

import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

class Main extends ListenerAdapter {
    public static void main(String[] args) {
        try {
            JDA jda = new JDABuilder("token")
                .addEventListeners(new Main())
                .build();

            // block until jda is ready
            jda.awaitReady();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // create thread safe queues
        Queue jobs = new Queue();
        Queue sender_queue = new Queue();

        // handle all incoming jobs (meme requests, etc.)
        Executor job_handler = new Executor(jobs, sender_queue);
        job_handler.start();

        // sender sends the results from executor
        Sender sender = new Sender(sender_queue);
        sender.start();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String message_content = event.getMessage().getContentRaw();
        Queue jobs = new Queue();
        switch (message_content) {
            case "!meme":
                jobs.add(new ApiRequest("memeurl", event.getChannel(), (obj) -> {
                    return new String("Got obj: " + obj.toString());
                }));
                break;
            default:
                if (message_content.startsWith("!"))
                    event.getChannel().sendMessage("Sorry, unknown command!");
        }
    }
}
