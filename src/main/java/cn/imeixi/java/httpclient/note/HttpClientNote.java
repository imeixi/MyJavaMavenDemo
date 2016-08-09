package cn.imeixi.java.httpclient.note;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.util.EntityUtils;

public class HttpClientNote {

	/**
	 * HTTPClient 4.5中文教程 笔记
	 * http://blog.csdn.net/u011179993/article/details/47131773
	 * 
	 * @author zhengAh
	 */

	/**
	 * 1.1 执行Request 模型
	 * 
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public void requestModel() throws ClientProtocolException, IOException {

		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet("http://localhost");
		CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
		try {
			// <...>
		} finally {
			httpResponse.close();
		}
		httpClient.close();
	}

	/**
	 * 1.1.1、http request Http请求头需包含的信息 :Method URI HTTP/version CRLF eg: GET
	 * /user/getListCardRegisteredApp?feedId=c_1459326680465054&pageSize=
	 * 2147483647 HTTP/1.1
	 * 
	 * Method: GET，HEAD， POST，PUT， DELETE， TRACE 和 OPTIONS (HttpClient4.5
	 * 完全实现了HTTP 1.1 协议的所有方法：) 每个方法都有一个特别的类：HttpGet，HttpHead，
	 * HttpPost，HttpPut，HttpDelete，HttpTrace和HttpOptions
	 * 
	 * URI:包含了一个协议名称(http/ftp/smtp)，主机名，可选端口，资源路径，可选的参数，可选的片段。
	 * URIBuilder工具类可用来简化创建、修改请求 URIs
	 * 
	 * @throws URISyntaxException
	 * 
	 */

	public void httpRequest() throws URISyntaxException {
		URI uri = new URIBuilder().setScheme("http").setHost("www.google.com").setPath("/search")
				.setParameter("q", "httpclient").setParameter("btnG", "Google Search").setParameter("aq", "f")
				.setParameter("oq", "").build();
		HttpGet httpget = new HttpGet(uri);
		System.out.println(httpget.getURI());

		HttpGet httpget1 = new HttpGet("http://www.google.com/search?hl=en&q=httpclient&btnG=Google+Search&aq=f&oq=");
		System.out.println(httpget1.getURI());
	}

	/**
	 * 1.1.2.HTTP 响应（Response） HTTP 相应是服务器接收并解析请求信息后返回给客户端的信息，它的起始行包含了一个
	 * <协议版本>，一个<状态码>和<描述状态的短语>。 eg: HTTP/1.1 200 OK
	 */

	public void httpResponse() {
		HttpResponse response = new BasicHttpResponse(HttpVersion.HTTP_1_1, HttpStatus.SC_OK, "OK");
		System.out.println(response.getProtocolVersion());
		System.out.println(response.getStatusLine().getProtocolVersion());
		System.out.println(response.getStatusLine().getStatusCode());
		System.out.println(response.getStatusLine().getReasonPhrase());
		System.out.println(response.getStatusLine().toString());
	}

	/**
	 * 1.1.3.处理报文首部（Headers）
	 * 一个HTTP报文包含了许多描述报文的首部，比如内容长度，内容类型等。HttpClient提供了一些方法来取出，添加，移除，枚举首部。
	 */

	public void httpReaderHeader() {
		// 构造response（带请求头的）
		// response 包括：
		// <起始行> StatusLine
		// <请求首部> Header
		HttpResponse response = new BasicHttpResponse(HttpVersion.HTTP_1_1, HttpStatus.SC_OK, "ok");
		response.setHeader("Set-Cookie", "c1=a; path=/; domain=localhost");
		response.addHeader("Set-Cookie", "c2=b; path=\"/\", c3=c; domain=\"localhost\"");

		// 读取header中的"Set-Cookie" 首部第1行和最后1行"
		Header h1 = response.getFirstHeader("Set-Cookie");
		Header h2 = response.getLastHeader("Set-Cookie");

		System.out.println("读取header中的\"Set-Cookie\" 首部第1行和最后1行");
		System.out.println("response.getFirstHeader(\"Set-Cookie\"): " + h1);
		System.out.println("response.getLastHeader(\"Set-Cookie\"): " + h2);
		System.out.println();

		// 读取全部首部
		System.out.println("*********   读取header中的全部首部（不包括起始行）   *************");
		System.out.println("response.getAllHeaders():");
		Header[] hs = response.getAllHeaders();
		for (Header header : hs) {
			System.out.println(header);
		}

		// 获得所有<指定类型>首部最有效的方式是使用HeaderIterator接口
		System.out.println();
		System.out.println("*********   获得所有<指定类型>首部  HeaderIterator *************");
		System.out.println("HeaderIterator it = response.headerIterator(\"Set-Cookie\")");
		HeaderIterator it = response.headerIterator("set-cookie");
		while (it.hasNext()) {
			System.out.println(it.nextHeader());
		}

		// HTTP报文转化为单个的HTTP元素。
		// 请求头中，用“，”区分 element
		// 每个element后用“；”区分 paraments
		System.out.println();
		System.out.println("*********   HTTP报文转化为单个的HTTP元素  *************");
		// HeaderElementIterator its = new BasicHeaderElementIterator(it);
		HeaderElementIterator its = new BasicHeaderElementIterator(response.headerIterator("set-cookie"));
		while (its.hasNext()) {
			HeaderElement elem = its.nextElement();
			System.out.println(elem.getName() + "=" + elem.getValue());
			NameValuePair[] params = elem.getParameters();
			for (int i = 0; i < params.length; i++) {
				System.out.println(" " + params[i]);
			}
		}
	}

	/**
	 * 1.1.4.HTTP实体（HTTP Entity） 依据实体的内容来源，HttpClient区分出三种实体：
	 * 流式实体（streamed）：内容来源于一个流，或者在运行中产生【译者：原文为generated on the fly】。特别的，这个类别包括从响应中接收到的实体。流式实体不可重复。
	 * 自包含实体（self-contained）：在内存中的内容或者通过独立的连接/其他实体获得的内容。自包含实体是可重复的。【ByteArrayEntity】或【StringEntity】 这类实体大部分是HTTP内含实体请求。
	 * 包装实体（wrapping）：从另外一个实体中获取内容。
	 * 
	 * 1.1.4.2 使用HTTP实体
	 * 
	 * 由于一个实体能够表现为<二进制>和<字符>内容，它支持二进制编码（为了支持后者，即字符内容）。
	 * 
	 * 实体将会在一些情况下被创建：当执行一个含有内容的请求时或者当请求成功，响应体作为结果返回给客户端时。
	 * 		读取实体的内容，可以通过   HttpEntity#getContent()   方法取出输入流，返回一个Java.io.InputStream,
	 * 		       	     或者通过   HttpEntity#writeTo(OutputStream) 方法提供一个输出流给,它将会返回写入给定流的所有内容。
	 * 
	 * 当实体内部含有信息时，
	 * 		使用   HttpEntity#getContentType()
	 * 			  HttpEntity#getContentLength()  方法将会读取一些基本的元数据，
	 * 		比如Content-Type和Content-Length这样的首部（如果他们可用的话），
	 * 		由于Content-Type首部能够包含文本MIME类型（像 text/plain或text/html），
	 * 		它也包含了与MIME类型相对应的字符编码，
	 * 			HttpEntity#getContentEncoding() 方法被用来读取这些字符编码。
	 * 
	 * 如果对应的首部不存在，则Content-Length的返回值为-1，Content-Type返回值为NULL。
	 * 如果Content-Type是可用的，一个Header类的对象将会返回。
	 * @throws ParseException 
	 * @throws IOException 
	 */
	
	public void httpEntity() throws ParseException, IOException{
		//创建一个String实体，实体内容是"important message"，实体类型"text/plain"
		StringEntity myEntity = new StringEntity("important message", ContentType.create("text/plain", "utf-8"));
		System.out.println(myEntity.getContentType());
		System.out.println(myEntity.getContentLength());
		System.out.println(EntityUtils.toString(myEntity));
		System.out.println(EntityUtils.toByteArray(myEntity).length);
	}
	
	/**
	 * 1.1.5.确保释放低级别的资源 为了确保正确的释放系统资源，你必须关掉与实体与实体相关的的内容流，还必须关掉响应本身。
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public void closeHttpEntityStream() throws ClientProtocolException, IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet("http://localhost/");
		CloseableHttpResponse response = httpclient.execute(httpGet);
		try {
			HttpEntity httpEntity = response.getEntity();
			if(httpEntity != null){
				InputStream is = httpEntity.getContent();
				try {
					//
					
				} catch (Exception e) {
					e.printStackTrace();
				} finally{
					is.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 关闭内容流和关闭响应的不同点是：前者将会通过消费实体内容保持潜在的连接，而后者迅速的关闭并丢弃连接。
	 * 请注意，一旦实体被HttpEntity#writeTo(OutputStream)方法成功地写入时，也需要确保正确地释放系统资源。
	 * 如果方法获得通过HttpEntity#getContent()，它也需要在一个finally子句中关闭流。
	 * 当使用实体时，你可以使用EntityUtils#consume(HttpEntity)方法来确保实体内容完全被消费并且使潜在的流关闭。
	 */
	/**
	 * 某些情况，整个响应内容的仅仅一小部分需要被取出，会使消费其他剩余内容的性能代价和连接可重用性代价太高，这时可以通过关闭响应来终止内容流。
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public void readTwoByteAndClose() throws ClientProtocolException, IOException{
		CloseableHttpClient httpclient = HttpClients.createDefault();  
		HttpGet httpget = new HttpGet("http://localhost/");  
		CloseableHttpResponse response = httpclient.execute(httpget);  
		try {  
		HttpEntity entity = response.getEntity();  
		if (entity != null) {  
		        InputStream instream = entity.getContent();  
		        int byteOne = instream.read();  
		        int byteTwo = instream.read(); 
		        System.out.println(byteOne + byteTwo);
		    // Do not need the rest  
		}  
		} finally {  
		    response.close();  
		} 
	}
	
	/**
	 * 1.1.6.消费实体内容 
	 * 为了消费实体内容，推荐的方式是使用 HttpEntity#getContent() 或者 HttpEntity#writeTo(OutputStream)方法。
	 * HttpClient也提供了一个EntityUtils类，它有几个静态方法更容易的从实体中读取内容或信息。
	 * 取代了直接读取java.io.InputStream，可以通过这个类的方法取出全部内容体并放入一个String中或者byte数组中。
	 * 可是，<强烈不建议>使用EntityUtils，除非响应实体来自于信任的HTTP服务器并且知道它的长度。【译者：】
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public void httpEntityUtils() throws ClientProtocolException, IOException{
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet("http://localhost/");
		CloseableHttpResponse response = httpClient.execute(httpGet);
		try {
			HttpEntity entity = response.getEntity();
			if(entity != null){
				long len = entity.getContentLength();
				if(len != -1 && len < 2048){
					 System.out.println(EntityUtils.toString(entity)); 
				}else{
					 // Stream content out  
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			
		}
		
	}
	
	/**
	 * 把原始的实体用BufferedHttpEntity类包装起来,以便多次读取
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public void httpEntityBuffer() throws ClientProtocolException, IOException{
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet("http://localhost/");
		CloseableHttpResponse response = httpClient.execute(httpGet);
		try {
			HttpEntity entity = response.getEntity();
			if(entity != null){
				entity = new BufferedHttpEntity(entity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			
		}
	}
	
}
