<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.Map"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

		<h2>Test2</h2>
			<%
		Map<String,String> params = null;
		params = (Map<String,String>) application.getAttribute("params");
	%>
	<%
		if (params != null && params.size() > 0) {
			for (String key : params.keySet()) {
	%>
				<%= key %>  = <%=params.get(key) %>
	<%		
				
			}
		}
	%>
</body>
</html>