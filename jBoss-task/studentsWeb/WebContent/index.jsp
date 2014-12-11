<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Students</title>
</head>
<body>
	<form method="POST" action="controller" name="groupForm">
		<input type="hidden" name="command" value="getAllGroups" />
	</form>
	<form method="POST" action="controller" name="studentForm">
		<input type="hidden" name="command" value="getAllStudents" />
	</form>
	<ul>
		<li><a href="#" onclick="groupForm.submit();">Groups</a></li>
		<li><a href="#" onclick="studentForm.submit();">Students</a></li>
	</ul>
</body>
</html>