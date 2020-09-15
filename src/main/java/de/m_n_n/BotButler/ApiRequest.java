package de.m_n_n.BotButler;

import java.net.URL;
import java.net.MalformedURLException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.json.JSONObject;

import net.dv8tion.jda.api.entities.MessageChannel;

public class ApiRequest {
	private URL m_req;
	private Parseable m_parser;
	private MessageChannel m_channel;

	@FunctionalInterface
	public interface Parseable {
		String parse(JSONObject obj);
	}

	ApiRequest(String url, MessageChannel channel, Parseable parser) {
		try { m_req = new URL(url); }
		catch (MalformedURLException e) {
			e.printStackTrace();
			m_req = null;
		}

		m_channel = channel;
		m_parser = parser;
	}

	private JSONObject apiRequest() throws IOException {
		InputStream stream = m_req.openStream();
		// vorsicht ist die Mutter der Porzellankiste
		byte[] bytes = new byte[stream.available() * 2];
		stream.read(bytes);
		String result = new String(bytes, StandardCharsets.UTF_8);
		stream.close();

		if (result == "" || result == null) throw new IOException(result);
		return new JSONObject(result);
	}

	private String parseJSON(JSONObject jobj) throws IOException {
		JSONObject obj = apiRequest();
		String res = m_parser.parse(obj);
		return res;
	}

	public String requestAndParse() {
		try {
			JSONObject obj = this.apiRequest();
			return this.parseJSON(obj);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void sendToChannel(String message) {
		m_channel.sendMessage(message).queue();
	}
}
