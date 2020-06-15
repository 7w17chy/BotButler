package de.m_n_n.bot_butler;

import java.util.HashMap;
import java.net.URL;

import org.json.JSONObject;

public class API {
	public static class ApiRequest {
		private URL m_url;
		private HashMap<String, String> m_reqParams;
		private Parseable m_parser;
		private int m_channel_id;

		ApiRequest(String url, HashMap<String, String> req_params, Sendable.Parseable parser) {
			m_url = new URL(url);
			m_reqParams = new HashMap<String, String>();
			m_parser = parser;
		}

		ApiRequest(String url, Sendable.Parseable parser) {
			m_url = new URL(url);
			m_reqParams = null;
			m_parser = parser;
		}

		public String parse() {
			m_parser.parse();
		}

		public URL getUrl() {
			return m_url;
		}

		public HashMap<String, String> getReqParams() {
			return m_reqParams;
		}
	}

	public static class ApiResponse extends Sendable {
		private JSONObject m_obj;
		private int m_channel_id;

		ApiResponse(JSONObject obj, int channel_id) {
			m_obj = obj;
			m_channel_id = channel_id;
		}

		public String parse() {

		}
	}

	public static class ApiRequestHandler implements Job.Executable {
		private ApiRequest m_req;

		ApiRequestHandler(String url, HashMap<String, String> params, Sendable.Parseable parser) {
			m_req = new ApiRequest(url, params, parser);
		}

		ApiRequestHandler(String url, Sendable.Parseable parser) {
			m_req = new ApiRequest(url, null, parser);
		}

		private JSONObject apiRequest() throws IOException {
			InputStream stream = m_req.getUrl().openStream();
			// vorsicht ist die Mutter der Porzellankiste
			byte[] bytes = new byte[stream.available() * 2];
			stream.read(bytes);
			String result = new String(bytes, StandardCharsets.UTF_8);
			stream.close();
			return new JSONObject((result == "" || result == null) ? "{requestex:\"error!\"}" : result);
		}

		public Job.ExecuteResult execute() {
			JSONObject res = null;
			try {
				res = apiRequest();
			} catch (Exception e) {
				e.printStackTrace();
				return new Job.ExecuteResult(null, false);
			}

			return new Job.ExecuteResult(res, true);
		}
	}
}
