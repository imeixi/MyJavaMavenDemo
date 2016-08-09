package cn.imeixi.java.Utils;

public class HttpCilentUtils2 {

	public <T> T Get(String url, List<NameValuePair> params, final Class<T> objectClass) {
		DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
		String paramString = URLEncodedUtils.format(params, "utf-8");
		url += "?" + paramString;
		HttpGet httpGet = new HttpGet(url);
		try {
			httpGet.setHeader("Accept", "application/json");
			HttpResponse httpResponse = defaultHttpClient.execute(httpGet);
			if (httpResponse != null && httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity httpEntity = httpResponse.getEntity();
				if (httpEntity != null) {
					InputStream inputStream = httpEntity.getContent();
					String resultString = convertStreamToString(inputStream);
					inputStream.close();
					return new GsonBuilder().enableComplexMapKeySerialization().create().fromJson(resultString,
							objectClass);
				} else
					return null;
			} else
				return null;

		} catch (Exception e) {
			e.printStackTrace();
			Log.e(ServiceUrl.G_Log_Flag, "Result:" + e.getMessage());
			return null;
		}
	}

	public String Get(String url, List<NameValuePair> params) {

		String requestUrl = url;
		DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
		String paramString = URLEncodedUtils.format(params, "utf-8");
		url += "?" + paramString;
		HttpGet httpGet = new HttpGet(url);
		try {
			httpGet.setHeader("Accept", "application/json");
			HttpResponse httpResponse = defaultHttpClient.execute(httpGet);
			if (httpResponse != null && httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity httpEntity = httpResponse.getEntity();
				if (httpEntity != null) {
					InputStream inputStream = httpEntity.getContent();
					String resultString = convertStreamToString(inputStream);
					inputStream.close();
					return resultString;
				} else
					return "";
			} else
				return "error:" + httpResponse.getStatusLine().getStatusCode();
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getClass() == HttpHostConnectException.class)
				return "error:" + "通信异常，请检查网络，请求：" + requestUrl + ",错误描述：" + e.getMessage();
			else
				return "error:" + e.getMessage();
		}
	}

	public boolean Delete(String url, final List<NameValuePair> params) {
		DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
		String paramString = URLEncodedUtils.format(params, "utf-8");
		url += "?" + paramString;
		HttpDelete httpDelete = new HttpDelete(url);
		HttpResponse httpResponse = null;
		try {
			httpResponse = defaultHttpClient.execute(httpDelete);
			return httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_NO_CONTENT;
		} catch (IOException e) {
			e.printStackTrace();
			Log.e(ServiceUrl.G_Log_Flag, "Result:" + e.getMessage());
		}
		return false;
	}

}
