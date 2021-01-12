<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
	<form action="/test/user?cmd=join" method="post"
		onsubmit="return valid()">
		<div class="d-flex justify-content-end">
			<button type="button" class="btn btn-info" onClick="usernameCheck()">중복체크</button>
			<br />
		</div>

		<div class="form-group">
			<input type="text" name="username" class="form-control"
				placeholder="Enter Username" required />
		</div>

		<div class="form-group">
			<input type="password" name="password" class="form-control"
				placeholder="Enter Password" required />
		</div>

		<div class="form-group">
			<input type="email" name="email" class="form-control"
				placeholder="Enter Email" required />
		</div>

		<br />
		<button type="submit" class="btn btn-light">회원가입</button>
	</form>
</div>

<script>
	var isChecking = false;
	function valid() {
		if (isChecking == false) {
			alert("아이디 중복체크를 해주세요");
		}
		return isChecking;
	}
	function usernameCheck() {
		// DB에서 확인해서 정상이면 isChecking = true
		var username = $("#username").val(); // 아이디가 username인 input 태그에 적힌 값을 찾아준다.
		$.ajax({ 
			type : "POST",
			url : "/test/user?cmd=usernameCheck",
			data : username,
			contentType : "text/plain; charset=utf-8",
			dataType : "text"
		}).done(function(data) {
			if (data === 'ok') { // 유저네임 있다
				isChecking = false;
				alert('유저네임이 중복되었습니다.')
			} else {
				isChecking = true;
				alert("해당 유저네임을 사용할 수 있습니다.")
			}
		});
	}
</script>
</body>
</html>