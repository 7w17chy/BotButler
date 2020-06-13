package de.m_n_n.bot_butler;

public class Main extends ListenerAdapter {
    public static void main(String[] args) throws LoginException {
        new JDABuilder(args[0])
            .addEventListeners(new Bot())
            .setActivity(Activity.playing("Type !ping"))
            .build();
    }

    //@Override
    //public void onMessageReceived(MessageReceivedEvent event) {
    //    Message msg = event.getMessage();
    //    if (msg.getContentRaw().equals("!ping")) {
    //        MessageChannel channel = event.getChannel();
    //        long time = System.currentTimeMillis();
    //        channel.sendMessage("Pong!") /* => RestAction<Message> */
    //               .queue(response /* => Message */ -> {
    //                   response.editMessageFormat("Pong: %d ms", System.currentTimeMillis() - time).queue();
    //               });
    //    }
    //}

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        // we're only interested in the lowercased, raw underlying message as string
        String msg = event.getMessage().getContentRaw().toLower();

        switch (msg) {
        case "!ping":
            long time = System.currentTimeMillis();
            channel.sendMessage("Pong!") /* => RestAction<Message> */
                .queue(response /* => Message */ -> {
                    response.editMessageFormat("Pong: %d ms", System.currentTimeMillis() - time).queue();
                });
            break;
        default:
            System.out.println("Henlo, world!");
        }
    }
}
