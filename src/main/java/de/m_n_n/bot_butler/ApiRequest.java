package de.m_n_n.bot_butler;

import java.collections.HashMap;

public class ApiRequest {
    private String m_url;
    private HashMap<String, String> m_reqParams;

    ApiRequest(String url, HashMap<String, String> req_params) {
        m_url = url;
        m_reqParams = new HashMap<String, String>();
    }

    public String getUrl() {
        return m_url;
    }

    public HashMap<String, String> getReqParams() {
        return m_reqParams;
    }
}