package de.m_n_n.bot_butler;

import java.net.URL;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.json.JSONObject;

public static class ApiRequest implements Queue.Executable {
	private URL m_req;
	private Parseable m_parser;

	private JSONObject apiRequest() throws IOException {
		InputStream stream = m_req.openStream();
		// vorsicht ist die Mutter der Porzellankiste
		byte[] bytes = new byte[stream.available() * 2];
		stream.read(bytes);
		String result = new String(bytes, StandardCharsets.UTF_8);
		stream.close();
		return new JSONObject((result == "" || result == null) ? "{requestex:\"error!\"}" : result);
	}

	public Queue.Result<String> execute() {
		String res = null;
		try {
			JSONObject obj = apiRequest();
			res = m_parser.parse(obj);
		} catch (Exception e) {
			e.printStackTrace();
			return new Queue.Result(false, res);
		}

		return new Queue.Result(true, res);
	}

	public interface Parseable {
		public String parse(JSONObject obj);
	}
}
