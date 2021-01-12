<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
	<form action="/test/user?cmd=login" method="post" >
		<div class="form-group">
			<label for="exampleInputEmail1">Username</label> 
			<input type="text"  name="username"  class="form-control"  id="usename"
				 placeholder="Enter username"  required/> 
		</div>
		<div class="form-group">
			<label for="exampleInputPassword1">Password</label> 
			<input type="password"  name="password"  class="form-control" id="password"
				placeholder="Enter Password"  required/>
		</div>
		<button type="submit" class="btn btn-light">Login</button>
</div>
</form>

</body>
</html>