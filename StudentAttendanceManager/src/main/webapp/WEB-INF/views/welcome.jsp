<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<html>
<body>

	<h1>Classroom</h1>

	<p>Add or remove Students to the attendance sheet.<br/>Record attendance.</p>
	<table style="border: 1px solid; width: 500px; text-align: center">
		<thead style="background: #D8D8D8">
			<tr>
				<th>First Name</th>
				<th>Last Name</th>
				<th>Present</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${students}" var="student">
				<c:url var="editUrl"
					value="/main/students/edit?id=${student.id}" />
				<c:url var="deleteUrl"
					value="/main/students/delete?id=${student.id}" />
				<tr>
					<td><c:out value="${student.firstName}" /></td>
					<td><c:out value="${student.lastName}" /></td>
					<td><c:out value="${student.present}" /></td>
					<td><a href="${editUrl}">Edit</a></td>
					<td><a href="${deleteUrl}">Delete</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<c:url var="addUrl" value="/main/students/add" />
	<a href="${addUrl}">Add</a>
</body>
</html>