<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="bean.*,dao.*"%>
<%@page import="java.util.*" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page  isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<!-- 
<c:forEach items="${list}" var="user">
		<tr>
		<td align="center">${user.id}</td>
		</tr>
	</c:forEach>
-->
<table border=1 align="center">
	<tr>
		<td>id</td>
		<td>姓名</td>
		<td>密码</td>
		<td>操作</td>
	</tr>
	<%
		studentdao dao= new studentdao();
		ArrayList<student> list= dao.queryallstudent();
		Iterator<student> it= list.iterator();
		while(it.hasNext()){
			student s = it.next();
		
	%>
	<tr>
		<td><%=s.getId()%></td>
		<td><%=s.getUsername() %></td>
		<td><%=s.getUserpwd() %></td>
		<td><a href="deleteServlet?id=<%=s.getId()%>">删除</a></td>
		<td><a href="update.jsp?id=<%=s.getId()%>">修改</a></td>
	</tr>
	<% 
		}
		
	%>
	<tr>
		<td><a href="add.jsp">添加</a></td>
	</tr>
</table>
</body>
</html>