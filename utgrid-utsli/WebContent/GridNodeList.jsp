<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="org.jredfoot.utgrid.utsgd.ws.client.GridNodeService"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
	out.println("inicio");
	GridNodeService gns = new GridNodeService();
	out.println("E ai?");
	gns.getGridNodeServicePort().listGridNodes();
	out.println("fim");
%>
</body>
</html>