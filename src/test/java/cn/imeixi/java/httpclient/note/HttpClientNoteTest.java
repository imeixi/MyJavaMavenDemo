package cn.imeixi.java.httpclient.note;

import java.io.IOException;
import java.net.URISyntaxException;
import org.apache.http.ParseException;
import org.junit.Test;
import cn.imeixi.java.httpclient.note.HttpClientNote;

public class HttpClientNoteTest {

	@Test
	public void testHttpRequest() throws URISyntaxException {
		new HttpClientNote().httpRequest();
	}
	
	@Test
	public void testHttpResponse() throws URISyntaxException {
		new HttpClientNote().httpResponse();
	}
	
	@Test
	public void testHttpHeader() throws URISyntaxException {
		new HttpClientNote().httpReaderHeader();
	}
	
	@Test
	public void testHttpEntity() throws URISyntaxException, ParseException, IOException {
		new HttpClientNote().httpEntity();
	}
	
	@Test
	public void testHttpClientExample() {
		new HttpClientExample().httpGetBaidu();
	}

}

