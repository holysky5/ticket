package com.ourlife.dev.terminal.zyb;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class HttpUtil {
	public static final String GB2312 = "GB2312";
	public static final String UTF_8 = "UTF-8";

	public static String postRequest(String url, Map<String, String> params,
			String charset) {
		return postRequest(url, params, charset, "");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String postRequest(String url, Map<String, String> params,
			String charset, String split) {
		HttpClient httpclient = new DefaultHttpClient();

		try {
			HttpPost httppost = new HttpPost(url);
			List nvps = new ArrayList();
			Iterator it = params.keySet().iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				nvps.add(new BasicNameValuePair(key, params.get(key)));
			}

			httppost.setEntity(new UrlEncodedFormEntity(nvps, charset));
			HttpResponse response = httpclient.execute(httppost);

			HttpEntity resEntity = response.getEntity();
			StringBuffer buffer = new StringBuffer();
			if (resEntity != null) {
				InputStream instream = resEntity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(instream, charset));

				String line = "";
				while ((line = reader.readLine()) != null) {
					buffer.append(line + split);
				}
				return buffer.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
		return null;
	}
}