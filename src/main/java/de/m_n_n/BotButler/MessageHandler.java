package de.m_n_n.BotButler;

import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.Queue;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;

public class MessageHandler extends ListenerAdapter {

    private ExecutorService m_executor;
    private Queue<Future<?>> m_futQueue;
    private Semaphore m_mutex;
    public QueueChecker m_checker;

    private class QueueChecker extends Thread {
        private Queue<Future<?>> m_futures;
        private Semaphore m_mutex;

        QueueChecker(Semaphore mutex, Queue<Future<?>> futQueue) {
            m_futures = futQueue;
            m_mutex = mutex;
        }

        public void run() {
            for (;;) {
                try { m_mutex.acquire(); } catch (InterruptedException e) { e.printStackTrace(); }
                int i = 0;
                for (Future<?> fut : m_futures) {
                    Future<?> currentFuture = m_futures.peek();
                    if (currentFuture.isDone()) m_futures.remove(i);
                    i++;
                }
                i = 0;

                try {
                    m_mutex.release();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

	MessageHandler() {
        m_executor = Executors.newCachedThreadPool();
        // let's use a linked list...
        m_futQueue = new LinkedList<Future<?>>();
        m_mutex = new Semaphore(1);
        m_checker = new QueueChecker(m_mutex, m_futQueue);
        m_checker.start();
	}
	
	@Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String message_content = event.getMessage().getContentRaw();
        // do nothing if message's content is not a command
        if (!message_content.startsWith("!")) return;
        
        switch (message_content) {
            case "!meme":
                try { m_mutex.acquire(); } catch (InterruptedException e) { e.printStackTrace(); }
                m_futQueue.add(m_executor.submit(() -> {
                    ApiRequest req = new ApiRequest("https://meme-api.herokuapp.com/gimme", event.getChannel(), (obj) -> {
                        String ret = null;
                        try {
                            ret = obj.getString("postLink");
                        } catch (Exception e) {
                            e.printStackTrace();
                            ret = "Klitzekleiner Fehler beim Parsen der Server-Response. Nicht deine Schuld. Sag bitte mal Max bescheid :)";
                        }

                        return ret;
                    });
                    String result = req.requestAndParse();
                    req.sendToChannel(result);
                }));
                m_mutex.release();
                break;
            case "!hello":
            	event.getChannel().sendMessage("Gude!").queue();
            	break;
            case "!witz":
                try { m_mutex.acquire(); } catch (InterruptedException e) { e.printStackTrace(); }
                m_futQueue.add(m_executor.submit(() -> {
                    ApiRequest req = new ApiRequest("https://official-joke-api.appspot.com/random_joke", event.getChannel(), (obj) -> {
                        String ret = null;
                        try {
                            //SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd-hh-mm-ss.Z");
                            //Date publication_date = format.parse(obj.getString("appeared_at"));
                            ret = obj.getString("setup") + "\n" + obj.getString("punchline");
                        } catch (Exception e) {
                            e.printStackTrace();
                            ret = "Klitzekleiner Fehler beim Parsen der Server-Response. Nicht deine Schuld. Sag bitte mal Max bescheid :)";
                        }
                        return ret;
                    });
                    String result = req.requestAndParse();
                    req.sendToChannel(result);
                }));
                m_mutex.release();
                break;
            case "!trump":
                try { m_mutex.acquire(); } catch (InterruptedException e) { e.printStackTrace(); }
                m_futQueue.add(m_executor.submit(() -> {
                    ApiRequest req = new ApiRequest("http://tronalddump.io/random/quote", event.getChannel(), (obj) -> {
                        String ret = null;
                        try {
                            ret = "Tump hat einmal gesagt: " + "\"" + obj.getString("value") + "\"";
                        } catch (Exception e) {
                            e.printStackTrace();
                            ret = "Klitzekleiner Fehler beim Parsen der Server-Response. Nicht deine Schuld. Sag bitte mal Max bescheid :)";
                        }
                        return ret;
                    });
                    String result = req.requestAndParse();
                    req.sendToChannel(result);
                }));
                m_mutex.release();
                break;
            default:
                if (message_content.startsWith("!"))
                    event.getChannel().sendMessage("\""+ message_content + "\"enn' ich noch nicht...").queue();
        }
    }
}
