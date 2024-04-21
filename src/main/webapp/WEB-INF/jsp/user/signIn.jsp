<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="d-flex justify-content-center">
	<div class="login-box">
		<h1 class="mb-4">로그인</h1>
		
		<form id="loginForm" action="/user/sign-in" method="post">
			<div clas="input-group mb-3">
				<div class="input-group-prepend">
					<span class="input-group-text">ID</span>
				</div>
				<input type="text" class="form-control" id="loginId" name="loginId">
			</div>
			
			<div class="input-group mb-3">
				<div class="input-group-prepend">
					<span class="input-group-text">PW</span>
				</div>
				<input type="password" class="form-control" id="password" name="password">
			</div>
			
			<input type="submit" id="loginBtn" class="btn btn-block btn-primary" value="로그인">
			<a class="btn btn-block btn-dark" href="/user/sign-up-view">회원가입</a>
		</form>
	</div>
</div>

<script>
	$(document).ready(function() {
		//	로그인
		$("form").on('submit', function(e) {
			e.preventDefault();
			
			let loginId = $("input[name=loginId]").val().trim();
			let password = $("#password").val();
			
			if (!loginId) {
				alert("아이디를 입력하세요.");
				return false;
			}
			
			if (!password) {
				alert("패스워드를 입력하세요.");
				return false;
			}
			
			let url = $(this).attr('action');
			console.log(url);
			let params = $(this).serialize();
			console.log(params);
			
			$.post(url, params)
			.done(function(data) {
				if (data.result == "성공") {
					location.href = "/post/post-list-view"
				} else {
					alert(data.error_message);
				}
			});
		});
	});
</script>