package de.m_n_n.bot_butler;

import java.net.*;

public class ApiRequestHandler {
    private ApiRequest m_req;

    ApiRequestHandler(String url, HashMap<String, String> params) {
        m_req = new ApiReqest(url, params);
    }

    private JsonObject apiRequest() {
        URL api_url = new URL(m_req.getUrl());
        HttpURLConnection con = (HttpURLConnection) api_url.openConnection();
        con.setRequestMethod();
        return new JsonObject();
    }

    public String getSenableContent(Sendable send) {
        JsonObject req = apiRequest();
        return send.getSendable(req);
    }

    public interface Sendable {
        public String getSendable(JsonObject json);
    }
}
