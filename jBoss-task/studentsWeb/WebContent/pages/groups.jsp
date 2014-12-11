<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Groups</title>
</head>
<body>
<div class="address">
	<a href = "index.jsp">Main page</a>
</div>
	<ul>
		<c:forEach var="group" items="${groups}">
			<form name="groupForm${group.id}" method="POST" action="controller">
				<input type="hidden" name="command" value="getStudentsByGroup" /> 
				<input type="hidden" name="groupId" value="${group.id}" />
			</form>
			<li><a href="#" onclick="groupForm${group.id}.submit()">${group.name} </a></li>
		</c:forEach>
	</ul>
</body>
</html>