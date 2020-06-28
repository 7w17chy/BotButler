package de.m_n_n.bot_butler;

import java.net.URL;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.json.JSONObject;

import net.dv8tion.jda.api.entities.MessageChannel;

public class ApiRequest {
	private URL m_req;
	private Parseable m_parser;
	private MessageChannel m_channel;

	private JSONObject apiRequest() throws IOException {
		InputStream stream = m_req.openStream();
		// vorsicht ist die Mutter der Porzellankiste
		byte[] bytes = new byte[stream.available() * 2];
		stream.read(bytes);
		String result = new String(bytes, StandardCharsets.UTF_8);
		stream.close();

		if (result == "" || result == null)
			throw new IOException(result);

		return new JSONObject(result);
	}

	public ApiResponse executeRequest() {
		String res = null;
		try {
			JSONObject obj = apiRequest();
			res = m_parser.parse(obj);
		} catch (Exception e) {
			e.printStackTrace();
			return new ApiResponse(m_channel, null);
		}

		return new ApiResponse(m_channel, res);
	}

	public interface Parseable {
		public String parse(JSONObject obj);
	}

	public class ApiResponse extends Sendable {
		private MessageChannel m_channel;
		private String m_content;

		ApiResponse(MessageChannel msgc, String content) {
			m_channel = msgc;
			m_content = content;
		}

		public String getSendableContent() {
			return m_content;
		}
	}
}
