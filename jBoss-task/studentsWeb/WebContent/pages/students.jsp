<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Students</title>
</head>
<body>
<div class="address">
	<a href = "index.jsp">Main page</a>
</div>
	<c:choose>
		<c:when test="${!(empty requestScope.group)}">
			<h4>Students of ${group.name} group.</h4>
		</c:when>
		<c:otherwise>
			<h4>All Students.</h4>
		</c:otherwise>
	</c:choose>
	<form method="POST" action="controller" name="groupForm">
		<input type="hidden" name="command" value="getAllGroups" />
	</form>
	<table>
		<tr>
			<th>№</th>
			<th>First Name</th>
			<th>Last Name</th>
			<th>Group</th>
		</tr>
		<c:forEach var="student" items="${students}" varStatus="loop">
			<form name="studentForm${student.id}" action="controller" method="POST" >
				<input type="hidden" name="command" value="getStudentById" /> 
				<input type="hidden" name="studentId" value="${student.id}" />
			</form>
			<tr>
				<td>${loop.index + 1}</td>
				<td><a href="#" onclick="studentForm${student.id}.submit()">${student.firstName}</a></td>
				<td>${student.lastName}</td>
				<td>${student.studentGroup.name}</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>