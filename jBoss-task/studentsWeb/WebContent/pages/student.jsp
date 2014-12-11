<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="css/style.css" type="text/css" />
<title>Student</title>
</head>
<body>
<div class="address">
	<a href = "index.jsp">Main page</a>
</div>
	<div class="view">
		<div id="title">First Name:</div>
		<div id="content">${student.firstName}</div>
		<div id="title">Last Name:</div>
		<div id="content">${student.lastName}</div>
		<div id="title">Address:</div>
		<div id="content">${student.address}</div>
		<div id="title">E-mail:</div>
		<div id="content">${student.email}</div>
		<div id="title">Phone Number:</div>
		<div id="content">${student.phoneNumber}</div>
		<div id="title">Group:</div>
		<div id="content">${student.studentGroup.name}</div>
	</div>
</body>
</html>