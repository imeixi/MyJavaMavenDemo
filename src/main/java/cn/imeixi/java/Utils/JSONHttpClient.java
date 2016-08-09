package cn.imeixi.java.Utils;

import org.apache.http.impl.client.DefaultHttpClient;

public class JSONHttpClient {
    public <T> T PostObject(final String url, final T object,
            final Class<T> objectClass) throws Exception {
        DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        try {
            String data = new GsonBuilder().create().toJson(object);
            StringEntity stringEntity = new StringEntity(data);
            httpPost.setEntity(stringEntity);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setHeader("Accept-Encoding", "gzip");
            HttpResponse httpResponse = defaultHttpClient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                HttpEntity httpEntity = httpResponse.getEntity();
                if (httpEntity != null) {
                    InputStream inputStream = httpEntity.getContent();
                    org.apache.http.Header contentEncoding = httpResponse
                            .getFirstHeader("Content-Encoding");
                    if (contentEncoding != null
                            && contentEncoding.getValue().equalsIgnoreCase(
                                    "gzip")) {
                        inputStream = new GZIPInputStream(inputStream);
                    }
                    String resultString = convertStreamToString(inputStream);
                    inputStream.close();
                    return new GsonBuilder().create().fromJson(resultString,
                            objectClass);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(ServiceUrl.G_Log_Flag, "Result:" + e.getMessage());
            throw e;
        }
        return null;
    }
 
    public <T> T PostParams(String url, final List<NameValuePair> params,
            final Class<T> objectClass) throws Exception {
        String paramString = URLEncodedUtils.format(params, "utf-8");
        url += "?" + paramString;
        return PostObject(url, null, objectClass);
    }
 
    private String convertStreamToString(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return stringBuilder.toString();
    }
     
}

