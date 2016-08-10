package cn.imeixi.java.httpclient.note;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.util.EntityUtils;


public class HttpClientExample {
	
	private static final Log log = LogFactory.getLog(HttpClientExample.class);
	
	public void httpGetBaidu(){
		
		//构建请求
		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		//请求起始行
		HttpGet httpGet = new HttpGet("https://www.baidu.com/");
		
		//构建首部
		httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:39.0) Gecko/20100101 Firefox/39.0");
		
		try {
			CloseableHttpResponse response = httpClient.execute(httpGet);
			
			//获得起始行 
			System.out.println(response.getStatusLine().toString() + "\n");
			
			//获取首部
			Header[] headers = response.getAllHeaders();
			for(Header h:headers){
				System.out.println(h);
			}
			
			System.out.println();
			//将首部中的Set-Cookie 转换为元数据
			HeaderElementIterator its = new BasicHeaderElementIterator(response.headerIterator("set-cookie"));
			while (its.hasNext()) {
				HeaderElement elem = its.nextElement();
				System.out.println(elem.getName() + "=" + elem.getValue());
				NameValuePair[] params = elem.getParameters();
				for (int i = 0; i < params.length; i++) {
					System.out.println("    " + params[i]);
				}
			}
			
			System.out.println();
			System.out.println();
			//获取实体
			HttpEntity ety=response.getEntity(); 
		//	System.out.println(EntityUtils.toString(ety));
			InputStream is = ety.getContent();
			BufferedInputStream bis = new BufferedInputStream(is);
			OutputStream os =  new FileOutputStream(new File("baidu.txt"));
			BufferedOutputStream bos = new BufferedOutputStream(os);
			
			byte[] b = new byte[1024];
			int len = 0;
			while((len = bis.read(b)) != -1){
				bos.write(b, 0, len);
				bos.flush();
			}
			bos.close();
			os.close();
			bis.close();
			is.close();
			
			EntityUtils.consume(ety);
			response.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	
    public  void ClientCustomContext() throws Exception {  
        CloseableHttpClient httpclient = HttpClients.createDefault();  
        try {  
            // Create a local instance of cookie store  
            CookieStore cookieStore = new BasicCookieStore();
  
            // Create local HTTP context  
            HttpClientContext localContext = HttpClientContext.create();  
            // Bind custom cookie store to the local context  
            localContext.setCookieStore(cookieStore);  
  
            HttpGet httpget = new HttpGet("http://localhost/");  
            System.out.println("Executing request " + httpget.getRequestLine());  
  
            // Pass local context as a parameter  
            CloseableHttpResponse response = httpclient.execute(httpget, localContext);  
            try {  
                System.out.println("----------------------------------------");  
                System.out.println(response.getStatusLine());  
                List<Cookie> cookies = cookieStore.getCookies();  
                for (int i = 0; i < cookies.size(); i++) {  
                    System.out.println("Local cookie: " + cookies.get(i));  
                }  
                EntityUtils.consume(response.getEntity());  
            } finally {  
                response.close();  
            }  
        } finally {  
            httpclient.close();  
        }  
    } 
}

