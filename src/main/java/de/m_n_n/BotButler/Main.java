package de.m_n_n.BotButler;

import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

class Main extends ListenerAdapter {
    public static void main(String[] args) {

    	try {
            JDA jda = new JDABuilder("NzAwNjY5NDA1MjMwNzkyNzc1.XpmTPA.avuBciDQf2Q0lnNB7JtG85N2Q7k")
                .addEventListeners(new MessageHandler())
                .build();

            // block until jda is ready
            jda.awaitReady();
        } catch (Exception e) {
            e.printStackTrace();
        }
       
    }
}
