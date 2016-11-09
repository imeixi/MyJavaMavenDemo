package cn.imeixi.javaweb.servlet;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class HelloWordServlet
 */
@WebServlet(urlPatterns="/helloWorldServlet",
loadOnStartup = 1, name = "SimpleServlet", 
initParams = {@WebInitParam(name = "username", value = "tom"),@WebInitParam(name = "username1", value = "tom1")})

public class HelloWorldServlet implements Servlet  {

	public void destroy() {
		System.out.println("destroy().....");
	}

	public ServletConfig getServletConfig() {
		return null;
	}

	public String getServletInfo() {
		return null;
	}

	public void init(ServletConfig servletConfig) throws ServletException {
		
		//servletConfig  初始化参数
		System.out.println("init()......");
		String name = servletConfig.getInitParameter("username");
		System.out.println("**********   servletConfig.getInitParameter(\"username\")************");
		System.out.println("username:" + name);
		System.out.println();
		
		System.out.println("**********   servletConfig.getInitParameterNames()  ************");
		Enumeration<String> names = servletConfig.getInitParameterNames();
		while(names.hasMoreElements()){
			String username = names.nextElement();
			String value = servletConfig.getInitParameter(username);
			System.out.println(username + ":" + value);
		}
		
		//servletContext Web项目的大管家
		ServletContext servletContext = servletConfig.getServletContext();
		String user = servletContext.getInitParameter("user");
		System.out.println("**********   servletContext.getInitParameter(\"user\")************");
		System.out.println("user:" + user);
		System.out.println();
		
		Map<String,String> params = new HashMap<String, String>();
		System.out.println("**********   servletContext.getInitParameterNames()  ************");
		Enumeration<String> users = servletContext.getInitParameterNames();
		while(users.hasMoreElements()){
			String user2 = users.nextElement();
			String value2 = servletContext.getInitParameter(user2);
			System.out.println(user2 + ":" + value2);
			params.put(user2, value2);
		}
		
		//获取文件在服务器上的路径
		String realPath = servletContext.getRealPath("\note.txt");
		System.out.println(realPath);
		
		//获取当前Web应用的路径
		String contextPath = servletContext.getContextPath();
		System.out.println(contextPath);
		
		servletContext.setAttribute("params", params);
 		
		
	}

	public void service(ServletRequest arg0, ServletResponse arg1) throws ServletException, IOException {
		System.out.println("service()...");		
	}
	
}
