package de.m_n_n.bot_butler;

// don't reinvent the wheel...
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.util.HashMap;

import org.json.JSONObject;

public class ApiRequestHandler implements Job.Executable {
    private ApiRequest m_req;

    ApiRequestHandler(String url, HashMap<String, String> params) {
        m_req = new ApiRequest(url, params);
    }

    ApiRequestHandler(String url) {
        m_req = new ApiRequest(url, null);
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
