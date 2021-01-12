<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
	<h4>회원목록</h4>
	<br />
	<table class="table">
		<thead>
			<tr>
				<th>번호</th>
				<th>유저네임</th>
				<th>이메일</th>
				<th></th>
			</tr>
		</thead>

		<c:forEach var="user" items="${users}">
			<tbody>
				<tr>
					<td>${user.id}</td>
					<td>${user.username}</td>
					<td>${user.email}</td>
					<td>
					        <c:if test="${sessionScope.principal.id == user.id}">
							<button onClick="deleteById(${user.id})" class="btn btn-danger">삭제</button>
					    	</c:if>
					</td>

					
				</tr>
			</tbody>
		</c:forEach>
	</table>
</div>

</body>
</html>