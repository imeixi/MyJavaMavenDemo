package cn.imeixi.java.Utils.Json;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import cn.imeixi.java.Utils.Json.model.User;

/**
 * 使用Jackson的三种方式
 * 
 * 数据绑定模式：使用最方便 流模式：性能最佳 树模式：最灵活
 */
public class JacksonUtils {

	// 1、数据绑定模式（最常用）
	public void dataBind() throws JsonParseException, JsonMappingException, IOException {

		// Json 转成 javabean
		// readValue
		ObjectMapper mapper = new ObjectMapper();
		User user = mapper.readValue(new File("user.json"), User.class);

		System.out.println(user);

		// Javabean 转成 JsonString
		// writeValueAsString
		String str = mapper.writeValueAsString(user);
		System.out.println(str);
		

		// Javabean 转成 Json file
		// writeValue
		mapper.writeValue(new File("user-modified.json"), user);

	}

	// 2、树模式例子
	// 树模式就像XML的DOM树。Jackson用JsonNode来生成树。
	public void treeMode() throws Exception, IOException {
		ObjectMapper m = new ObjectMapper();
		// can either use mapper.readTree(source), or mapper.readValue(source,
		// JsonNode.class);
		JsonNode rootNode = m.readTree(new File("user.json"));
		// ensure that "last name" isn't "Xmler"; if is, change to "Jsoner"
		JsonNode nameNode = rootNode.path("name");
		String lastName = nameNode.path("last").textValue();
		if ("xmler".equalsIgnoreCase(lastName)) {
			((ObjectNode) nameNode).put("last", "Jsoner");
		}
		// and write it out:
		m.writeValue(new File("user-modified-tree.json"), rootNode);
	}



	//3、流模式 Stream
	//
	public void streamCreateJson() throws FileNotFoundException, IOException{
		JsonFactory jFactory = new JsonFactory();
		JsonGenerator jGenerator = jFactory.createGenerator(new FileOutputStream("user-create.json"));
		
	}
	
	
	public static void main(String[] args) throws Exception {
		// new JacksonUtils().dataBind();
		new JacksonUtils().treeMode();
	}

}
