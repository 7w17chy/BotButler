package de.m_n_n.BotButler;

import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import java.io.File;

class Main extends ListenerAdapter {
    public static void main(String[] args) {
        // get configuration
        Configuration config = new Configuration(new File("config.txt"));
        try { config.parse(); } catch(Exception e) { e.printStackTrace(); }

    	try {
            JDA jda = new JDABuilder(config.token)
                .addEventListeners(new MessageHandler(config))
                .build();

            // block until jda is ready
            jda.awaitReady();
        } catch (Exception e) {
            e.printStackTrace();
        }
       
    }
}
