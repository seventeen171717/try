<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%String id = request.getParameter("id"); %>
<form action="updateServlet" method="post">
	<table>
		<tr>
			<td>
				<input type="hidden" name="id" value="<%=id%>">
			</td>
		</tr>
		<tr>
			<td>姓名</td>
			<td><input type="text" name="username"></td>
		</tr>
		<tr>
			<td>密码</td>
			<td><input type="password" name="userpwd"></td>
		</tr>
		<tr>
			<td><button>提交</button></td>
		</tr>
		
	</table>
</form>
</body>
</html>