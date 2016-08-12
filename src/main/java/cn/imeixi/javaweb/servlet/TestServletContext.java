package cn.imeixi.javaweb.servlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TestServletContext
 */
public class TestServletContext extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * 注意不要覆盖  public void init(ServletConfig config) throws ServletException 这个方法
	 * 如需要初始化 可重载 无参数 的 init()方法
	 */

	public void init() throws ServletException {
		/**
		 * 通过getResource,getResourceAsStream 获取文件
		 * 默认路径：/Users/perfermance/Workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/PerformanceAutoTest
		 */

		System.out.println("********" + getServletContext().getContextPath());
		try {
			System.out.println("!!!!" + getServletContext().getRealPath("/web.xml"));
			System.out.println("!!!!" + getServletContext().getRealPath("/classes/filepath.properties"));
			System.out.println("!!!!" + getServletContext().getResource("/WEB-INF/classes/filepath.properties"));
			System.out
					.println("!!!!" + getServletContext().getResourceAsStream("/WEB-INF/classes/filepath.properties"));
			System.out.println("!!!!" + getServletContext().getResourceAsStream("/web.xml"));
			
			
			System.out.println(Thread.currentThread().getContextClassLoader().getResource("").toURI().getPath());
			System.out.println(this.getClass().getClassLoader().getResource("").toURI().getPath());
			System.out.println(ClassLoader.getSystemResource("").toURI().getPath());
			System.out.println(this.getClass().getResource("").toURI().getPath());
			System.out.println(this.getClass().getResource("/").toURI().getPath());
//			System.out.println(new File("/").getAbsolutePath().toURI().getPath());
//			System.out.println(System.getProperty("user.dir").toURI().getPath());
			System.out.println();


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/**
		 * 基本路径写入配置文件web.xml，从配置文件中读取 方法一：通过servletContext 读取配置参数
		 */

		String loaderIP1 = getServletContext().getInitParameter("loaderIP1");
		String loaderIP2 = getServletContext().getInitParameter("loaderIP2");

		// 基本路径
		// private static final String basePath = "/root/";
		String JmxPlanTemple = getServletContext().getInitParameter("JmxPlanTemple");
		String baseJmeterPath = getServletContext().getInitParameter("baseJmeterPath");
		String baseJmxPath = getServletContext().getInitParameter("baseJmxPath");
		String baseJtlPath = getServletContext().getInitParameter("baseJtlPath");
		String baseLogPath = getServletContext().getInitParameter("baseLogPath");

		System.out.println("方法一：" + "\n"
						+ "LoarderIP1: " + loaderIP1 + "\n" 
						+ "loaderIP2: " + loaderIP2 + "\n" 
						+ "JmxPlanTemple: " + JmxPlanTemple + "\n" 
						+ "baseJmxPath: " + baseJmxPath + "\n" 
						+ "baseJtlPath: " + baseJtlPath + "\n" 
						+ "baseJmeterPath: " + baseJmeterPath + "\n" 
						+ "baseLogPath: " + baseLogPath + "\n" );


		/**
		 * 方法二：通过ClassLoader 获取外部配置文件参数 注意：filepath.properties必须在
		 * 当前／WEB－INF／classes 目录下
		 */
		try {
			Properties properties = new Properties();
			ClassLoader classLoader = getClass().getClassLoader();
			InputStream is = classLoader.getResourceAsStream("filepath.properties");
			properties.load(is);
			loaderIP1 = properties.getProperty("loaderIP1");
			loaderIP2 = properties.getProperty("loaderIP2");
			baseJmeterPath = properties.getProperty("baseJmeterPath");
			baseJmxPath = properties.getProperty("baseJmxPath");
			baseJtlPath = properties.getProperty("baseJtlPath");

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("方法二：" + "\n"
				+ "LoarderIP1: " + loaderIP1 + "\n" 
				+ "loaderIP2: " + loaderIP2 + "\n" 
				+ "JmxPlanTemple: " + JmxPlanTemple + "\n" 
				+ "baseJmxPath: " + baseJmxPath + "\n" 
				+ "baseJtlPath: " + baseJtlPath + "\n" 
				+ "baseJmeterPath: " + baseJmeterPath + "\n" 
				+ "baseLogPath: " + baseLogPath + "\n" );

		/**
		 * 方法三：通过ServletContext 获取外部配置文件参数 getResourceAsStream(String path)
		 * :path 的／ 为当前WEB 应用根目录
		 */
		try {
			Properties properties = new Properties();
			InputStream ins = getServletContext().getResourceAsStream("/WEB-INF/classes/filepath.properties");
			properties.load(ins);
			loaderIP1 = properties.getProperty("loaderIP1");
			loaderIP2 = properties.getProperty("loaderIP2");
			baseJmeterPath = properties.getProperty("baseJmeterPath");
			baseJmxPath = properties.getProperty("baseJmxPath");
			baseJtlPath = properties.getProperty("baseJtlPath");
		} catch (IOException e) {

			e.printStackTrace();
		}
		
		System.out.println("方法三：" + "\n"
				+ "LoarderIP1: " + loaderIP1 + "\n" 
				+ "loaderIP2: " + loaderIP2 + "\n" 
				+ "JmxPlanTemple: " + JmxPlanTemple + "\n" 
				+ "baseJmxPath: " + baseJmxPath + "\n" 
				+ "baseJtlPath: " + baseJtlPath + "\n" 
				+ "baseJmeterPath: " + baseJmeterPath + "\n" 
				+ "baseLogPath: " + baseLogPath + "\n" );

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
