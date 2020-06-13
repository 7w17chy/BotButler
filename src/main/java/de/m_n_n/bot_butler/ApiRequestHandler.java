package de.m_n_n.bot_butler;

// don't reinvent the wheel...
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.io.IOException;

import org.json.JSONObject;

public class ApiRequestHandler {
    private ApiRequest m_req;

    ApiRequestHandler(String url, HashMap<String, String> params) {
        m_req = new ApiReqest(url, params);
    }

    ApiRequestHandler(String url) {
        m_req = new ApiRequest(url, null);
    }

    private JsonObject apiRequest() {
        String result = null;
        try {
            InputStream stream = m_req.openStream();
            // vorsicht ist die Mutter der Porzellankiste
            byte[] bytes = new byte[stream.available() * 2];
            stream.read(bytes);
            result = new String(arr, StandardCharsets.UTF_8);
            stream.close();
        } catch (IOException e) {
            // we've got a problem
            e.printStackTrace();
        }
        return new JSONObject((result == "" || result == null) ? "{requestex:\"error!\"}" : result);
    }

    public String getSendableContent(Sendable send) {
        JsonObject req = apiRequest();
        return send.getSendable(req);
    }

    public interface Sendable {
        public String getSendable(JsonObject json);
    }
}
