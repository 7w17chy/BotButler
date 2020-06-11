package de.m_n_n.bot_butler;

// flow:
// 1. Command kommt rein
// 2. Api Request? -> anderer Thread
//    - anderer thread hat eine queue
// 3. Thread macht api request -> ergebnis wird an weiteren thread gesendet
// 4. response thread sendet response zu discord

public class Main extends ListenerAdapter {
    public static void main(String[] args) throws LoginException {
        new JDABuilder(args[0])
            .addEventListeners(new Bot())
            .setActivity(Activity.playing("Type !ping"))
            .build();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Message msg = event.getMessage();
        if (msg.getContentRaw().equals("!ping")) {
            MessageChannel channel = event.getChannel();
            long time = System.currentTimeMillis();
            channel.sendMessage("Pong!") /* => RestAction<Message> */
                   .queue(response /* => Message */ -> {
                       response.editMessageFormat("Pong: %d ms", System.currentTimeMillis() - time).queue();
                   });
        }
    }
}
