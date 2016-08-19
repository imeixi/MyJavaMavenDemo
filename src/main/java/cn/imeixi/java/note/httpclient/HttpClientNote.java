package cn.imeixi.java.note.httpclient;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.net.ssl.SSLException;

import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpConnection;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class HttpClientNote {

	/**
	 * HTTPClient 4.5中文教程 笔记
	 * http://blog.csdn.net/u011179993/article/details/47131773
	 * 
	 * @author zhengAh
	 */

	private HttpContext context;

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
	
	/**
	 * 1.1.7.生产实体内容
	 * HttpClient提供了几个类，用来通过HTTP连接高效地传输内容。
	 * 这些类的实例均与内含实体请求有关，比如POST和PUT，它们能够把实体内容封装进友好的HTTP请求中。
	 * 对于基本的数据容器String, byte array, input stream, and file，
	 * HttpClient为它们提供了几个对应的类：StringEntity, ByteArrayEntity, InputStreamEntity, and FileEntity。
	 * 
	 * 请注意:InputStreamEntity是不可重复的，因为它仅仅能够从数据流中读取一次。
	 * 一般建议实现一个定制的HttpEntity类来代替使用一般的InputStreamEntity。FileEntity将会是一个好的出发点。
	 */
	public void postEntity(){
		//构建HTTPClient
		CloseableHttpClient httpClient = HttpClients.createDefault();
		//构建Post方法对象
		HttpPost post = new HttpPost("http://localhost");
		//构建请求的实体
		File file = new File("baidu.txt");
		FileEntity entity = new FileEntity(file,ContentType.create("text/plain","utf-8"));
		//添加POST请求实体
		post.setEntity(entity);
		//执行Request请求
		try {
			CloseableHttpResponse response = httpClient.execute(post);
			
			
			
			response.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 1.1.7.1 HTML表单
	 * 许多应用需要模仿一个登陆HTML表单的过程，
	 * 比如：为了登陆一个web应用或者提交输入的数据。
	 * HttpClient提供了UrlEncodedFormEntity类来简化这个过程。
	 */
	public void postFormEntity(){
		//构建HTTPClient
		CloseableHttpClient httpClient = HttpClients.createDefault();
		//构建Post方法对象
		HttpPost post = new HttpPost("http://localhost");
		//构建Form表单的请求的实体
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("paramName1", "paramValue1"));
		formparams.add(new BasicNameValuePair("paramName2", "paramValue2"));
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
		System.out.println(entity);
		//添加POST请求实体
		post.setEntity(entity);
		//执行Request请求
		try {
			CloseableHttpResponse response = httpClient.execute(post);
			
			
			
			response.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//生成的参数 paramName1=paramValue1&paramName2=paramValue2
	}
	
	/**
	 * 
	 * 1.1.7.2 内容分块
	 * 通常，我们推荐让HttpClient选择基于被传递的HTTP报文属相最合适的传输编码方式。
	 * 可以通过设置HttpEntity#setChunked()为true
	 * 来通知HttpClient你要进行<分块编码>。
	 * 注意HttpClient将会使用这个标志作为提示。
	 * 当使用一些不支持分块编码的HTTP版本（比如HTTP/1.0.）时，这个值将会忽略。
	 * 【译者：分块编码是是HTTP1.1协议中定义的Web用户向服务器提交数据的一种方法】
	 */
	public void postBlockEntity(){
		//构建HTTPClient
		CloseableHttpClient httpClient = HttpClients.createDefault();
		//构建Post方法对象
		HttpPost post = new HttpPost("http://localhost");
		//构建Form表单的请求的实体
		StringEntity entity = new StringEntity("important message",ContentType.create("text/plain", "utf-8"));
		
		//设置<分块编码>
		entity.setChunked(true);
		
		//添加POST请求实体
		post.setEntity(entity);
		//执行Request请求
		try {
			CloseableHttpResponse response = httpClient.execute(post);
			
			response.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 1.1.8.响应处理器
	 * 最简单、最方便的方式来处理响应是使用ResponseHandler接口，它包含了一个handleResponse(HttpResponse response)方法。
	 * 当你使用ResponseHandler时，无论是请求执行成功亦或出现异常，HttpClient将会自动地确保连接释放回连接管理器中。
	 */
	public void responseHandler(){
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet("http://localhost/json");
		ResponseHandler<MyJsonObject> rh = new ResponseHandler<MyJsonObject>() {

			public MyJsonObject handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
				StatusLine statusLine = response.getStatusLine();  
		        HttpEntity entity = response.getEntity();  
		        if (statusLine.getStatusCode() >= 300) {  
		            throw new HttpResponseException(statusLine.getStatusCode(),  
		                statusLine.getReasonPhrase());  
		        }  
		        if (entity == null) {  
		             throw new ClientProtocolException("Response contains no content");  
		        }  
		        Gson gson = new GsonBuilder().create();  
		        ContentType contentType = ContentType.getOrDefault(entity);  
		        Charset charset = contentType.getCharset();  
		        Reader reader = new InputStreamReader(entity.getContent(), charset);  
		        return gson.fromJson(reader, MyJsonObject.class);  
			}
			
		};
		try {
			MyJsonObject myjson = httpClient.execute(httpGet, rh);
		} catch (Exception e) {
			e.printStackTrace();
		}  
	}
	
	/**
	 * 1.2.HttpClient接口
	 * Strategy   |ˈstrætədʒi  策略
	 * 设置keepAlive时间
	 */
	public void creatKeepAliveHttpClient(){
		ConnectionKeepAliveStrategy keepAliveStrategy = new DefaultConnectionKeepAliveStrategy(){
			public long getkeepAliveDuartion(HttpResponse response,HttpContext context){
				long keepAlive = super.getKeepAliveDuration(response, context);
				if(keepAlive == -1){
					// 如果keep-alive的值内有被服务器明确设置，保持持续连接5秒 
					keepAlive = 5000;
				}
				return keepAlive;
			}
		};
		CloseableHttpClient httpClient = HttpClients.custom().setKeepAliveStrategy(keepAliveStrategy).build();
	}
	
	
	/**
	 * 1.2.1.HttpClient线程安全 HttpClient实现是线程安全的。对于不同的请求执行，这个类的相同实例是可复用的。
	 * 【译者：也就是说，一组相关的请求由同一个HttpClient实例实现】
	 * 
	 * 1.2.2.HttpClient资源分配
	 * 当一个HttpClient实例不再需要时并且它不在连接管理器之内时，必须通过<CloseableHttpClient#close()>方法关闭。
	 */
	
	/**
	 * 1.3.Http执行上下文(context) 
	 * 
	 * 在HTTP请求执行的这一过程中， HttpClient添加了下列属性到执行上下文中：
	 * `HttpConnection   实例代表连接到目标服务器的当前连接。 
	 * `HttpHost         实例代表连接到目标服务器的当前连接。
	 * `HttpRoute        实例代表了完整的连接路由。
	 * `HttpRequest      实例代表了当前的HTTP请求。最终的HttpRequest对象在执行上下文中总是准确代表了状态信息，因为它已经发送给了服务器。
	 * 				     每个默认的HTTP/1.0 和 HTTP/1.1使用相对的请求URI，可是，请求以non-tunneling模式通过代理发送时，URI会是绝对的。
	 * `HttpResponse     实例代表当前的HTTP响应。
	 * `java.lang.Boolean    对象是一个标识，它标志着当前请求是否完整地传输给连接目标。 
	 * `RequestConfig    代表当前请求配置。
	 * `java.util.List<URI>   对象代表一个含有执行请求过程中所有的重定向地址。
	 * 
	 *  你可以使用HttpClientContext适配器类来简化上下文状态的活动
	 */
	public void httpClientContext(HttpContext context){
		HttpClientContext clientContext = HttpClientContext.adapt(context);
		HttpConnection conn = clientContext.getConnection();
		HttpHost target = clientContext.getTargetHost();
		HttpRequest request = clientContext.getRequest();
		HttpResponse response = clientContext.getResponse();
		RequestConfig config = clientContext.getRequestConfig();
	}
	
	/**
	 * 代表一个逻辑相关的会话中的多个请求序列应该被同一个HttpContext实例执行，以确保请求之间会话上下文和状态信息的自动传输。
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public void httpContextTransfer() throws ClientProtocolException, IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet1 = new HttpGet("http://localhost/1");
		RequestConfig requestConfig = RequestConfig.custom()
				.setSocketTimeout(1000)
				.setConnectTimeout(1000)
				.build();
		httpGet1.setConfig(requestConfig);
		
		//将httpGET1请求的上下文属性放入到context中
		CloseableHttpResponse response1 = httpClient.execute(httpGet1, context);
		try {
			HttpEntity entity1 = response1.getEntity();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			response1.close();
		}
		
		HttpGet httpGet2 = new HttpGet("http://localhost/2");
		//将httpGET2s请求的上下文属性放入到context中
		CloseableHttpResponse response2 = httpClient.execute(httpGet2, context);
		try {
			HttpEntity entity2 = response2.getEntity();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			response2.close();
		}
	}

	/**
	 * 1.4 HTTP协议拦截器 
	 * HTTP协议拦截器是一个实现了HTTP协议特定方面的程序。
	 * 通常协议拦截器将作用于报文的一个特定的首部或一组相关的首部。
	 * 或者添加一个特定的首部或一组相关的首部到将要发送的报文中。
	 * 协议拦截器也可以操作报文内含的实体--显而易见的内容解压/压缩就是一个好的例子。
	 * 包装实体类使用了装饰模式对原始的实体进行装饰。几个协议拦截器能够结合构成一个逻辑单元。
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	
	public void doHttpFilter() throws ClientProtocolException, IOException{
		CloseableHttpClient httpClient = HttpClients.custom()
				.addInterceptorLast(new HttpRequestInterceptor() {
					
					public void process(final HttpRequest request, final HttpContext context) throws HttpException, IOException {
						AtomicInteger count = (AtomicInteger) context.getAttribute("count");
						request.addHeader("Count", Integer.toString(count.getAndIncrement()));;
					}
				}).build();
		
		AtomicInteger count = new AtomicInteger(1);
		HttpClientContext localContext = HttpClientContext.create();
		localContext.setAttribute("count", count);
		
		HttpGet httpget = new HttpGet("http://localhost/");
		for(int i = 0;i < 10;i++){
			CloseableHttpResponse response = httpClient.execute(httpget, localContext);
			try {
				HttpEntity entity = response.getEntity();
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
		
	}

	
	/**
	 * 1.5 异常处理   http://blog.csdn.net/u011179993/article/details/49022519
	 * 1.5.4.请求尝试处理器（Request retry handler）
	 * 
	 *  为了能够使用自定义异常的恢复机制，你必须提供一个HttpRequestRetryHandler接口的实现。
	 * 
	 * 请注意你可以使用StandardHttpRequestRetryHandler代替默认使用的，以便处理那些被RFC-2616定义为幂等的并且能够安全的重试的请求方法。
	 * 方法有：GET, HEAD, PUT, DELETE, OPTIONS, and TRACE。
	 */
	public void httpException(){
		HttpRequestRetryHandler myRetryHandler = new HttpRequestRetryHandler(){

			public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {

				if(executionCount >=5){
					//Do not retry if over max retry count
					return false;
				}
				
				if(exception instanceof InterruptedIOException){
					//Timeout
					return false;
				}
				
				if(exception instanceof UnknownHostException){
					//unknow host
					return false;
				}
				
				if(exception instanceof ConnectTimeoutException){
					//connect refuse
					return false;
				}
				
				if(exception instanceof SSLException){
					//SSl handshake exception
					return false;
				}
				return false;
			
			}
		};
		
		HttpClientContext httpClientContext = HttpClientContext.adapt(context);
		HttpRequest request = httpClientContext.getRequest();
	}
	
	/**
	 * 1.6 终止请求
	 * 在一些情况下，由于目标服务器的高负载或客户端有很多同时的请求发出，那么 HTTP 请求会在预期的时间内执行失败。 
	 * 这时，有必要过早地中止请求，解除在 I/O 执行中的线程锁。 
	 * HttpClient 执行时，可以在任意阶段通过调用 <HttpUriRequest#abort() > 方法中止请求。 
	 * 这个方法是线程安全的，而且可以从任意线程中调用。
	 * 当一个 HTTP 请求被中止时，它的执行线程--就封锁在 I/O 操作中了--而且保证通过抛出InterruptedIOException异常来解锁。
	 */
	
	/**
	 * 1.7.重定向处理 
	 * HttpClient自动处理所有类型的重定向。除了那些由 HTTP 规范明令禁止的，比如需要用户干预的。
	 * 参考其它（状态码 303）POST 和 PUT 请求的重定向转换为符合 HTTP 规范要求的
	 * GET请求。你可以使用一个重定向策略，来突破POST方法自动重定向的限制（POST自动重定向为HTTP规范强加）。
	 */
	public void redirectStrategy(){
		LaxRedirectStrategy redirectStrategy = new LaxRedirectStrategy();
		CloseableHttpClient httpclient = HttpClients.custom().setRedirectStrategy(redirectStrategy).build();
	}
	
	/**
	 * 在请求报文执行过程中，HttpClient经常需要改写它。每个默认的HTTP/1.0和HTTP/1.1使用相对URI。同样，
	 * 原始请求需要从一个地址重定向到另一个地址多次。最终绝对的HTTP地址将会被原始的请求和上下文构建。
	 * 功能方法URIUtils#resolve被使用来构建最终请求形成的绝对URI。
	 * 这个方法包含了来自于重定向和原始请求的上一个标识。
	 */
	public void URIUtilsResolve(){
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpClientContext context = HttpClientContext.create();
		HttpGet httpGet = new HttpGet("http://localhost:8080");
		try {
			CloseableHttpResponse response = httpClient.execute(httpGet,context);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
			
		
}		
	
	
	
	
	

