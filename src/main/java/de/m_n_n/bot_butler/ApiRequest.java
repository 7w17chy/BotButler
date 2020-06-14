package de.m_n_n.bot_butler;

import java.util.HashMap;
import java.net.URL;

public class ApiRequest extends Sendable {
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
