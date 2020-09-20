package de.m_n_n.BotButler;

import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

class Main extends ListenerAdapter {
    public static void main(String[] args) {

    	try {
            JDA jda = new JDABuilder("NzAwNjY5NDA1MjMwNzkyNzc1.XpmTPA.5r1jPTXBuP4ddizmv98RhOYJan8")
                .addEventListeners(new MessageHandler())
                .build();

            // block until jda is ready
            jda.awaitReady();
        } catch (Exception e) {
            e.printStackTrace();
        }
       
    }
}
