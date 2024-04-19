<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="w-50">
	<h1>회원가입</h1>
	
	<form id="signUpForm" method="post" action="/user/sign-up">
		<table class="sign-up-table table table-bordered">
			<tr>
				<th>ID</th>
				<td>
					<div class="d-flex">
						<input type="text" id="loginId" name="loginId" class="form-control col-7" placeholder="아이디를 입력하세요.">
						<button type="button" id="loginIdCheckBtn" class="btn btn-primary">중복확인</button>
						<br>
					</div> <%-- 아이디 체크 결과 --%> <%-- d-none 클래스: display none (보이지 않게) --%>
					<div id="idCheckLength" class="small text-danger d-none">ID를
							4자 이상 입력해주세요.</div>
					<div id="idCheckDuplicated" class="small text-danger d-none">이미
							사용중인 ID입니다.</div>
					<div id="idCheckOk" class="small text-success d-none">사용 가능한
							ID 입니다.</div>
				</td>
			</tr>
			<tr>
				<th>Password</th>
				<td>
					<input type="password" id="password" name="password" class="form-control col-7" 
					placeholder="비밀번호를 입력하세요.">
				</td>
			</tr>
			<tr>
				<th>Confirm Password</th>
				<td>
					<input type="password" id="confirmPassword" class="form-control col-7"
					placeholder="비밀번호를 입력하세요.">
				</td>
			</tr>
			<tr>
				<th>Name</th>
				<td><input type="text" id="name" name="name"
				class="form-control col-7" placeholder="이름을 입력하세요."></td>
			</tr>
			<tr>
				<th>Email</th>
				<td><input type="text" id="email" name="email"
				class="form-control col-7" placeholder="이메일을 입력하세요."></td>
			</tr>
		</table>
		<br>
		<button type="submit" id="signUpBtn"
		class="btn btn-primary">가입하기</button>
	</form>
</div>

<script>
	$(document).ready(function() {
		// 아이디 중복확인
		$("#loginIdCheckBtn").on('click', function() {
			//alert("d");
			
			// 경고 문구 초기화
			$("#idCheckLength").addClass("d-none");
			$("#idCheckDuplicated").addClass("d-none");
			$("#idCheckOk").addClass("d-none");
			
			let loginId = $("input[name=loginId]").val().trim();
			if (loginId.length < 4) {
				$("#idCheckLength").removeClass("d-none");
				return;
			}
			
			// AJAX - 중복확인
			$.get("/user/is-duplicated-id", {"loginId":loginId})
			.done(function(data) {
				if (data.code == 200) {
					if (data.is_duplicated_id) {
						$("#idCheckDuplicated").removeClass("d-none");
					} else {
						$("#idCheckOk").removeClass("d-none");
					}
				} else {
					alert(data.error_message);
				}
			});
		});
		
		//	회원가입
		$("#signUpForm").on('submit', function(e) {
			e.preventDefault();
			
			//alert("d");
			
			//	validation
			let loginId = $("input[name=loginId]").val().trim();
			let password = $("#password").val();
			let confirmPassword = $("#confirmPassword").val();
			let name = $("#name").val().trim();
			let email = $("#email").val().trim();
			if (!loginId) {
				alert("아이디를 입력하세요.");
				return false;
			}
			if (!password || !confirmPassword) {
				alert("비밀번호를 입력하세요.");
				return false;
			}
			if (password != confirmPassword) {
				alert("비밀번호가 일치하지 않습니다.");
				return false;
			}
			if (!name) {
				alert("이름을 입력하세요.");
				return false;
			}
			if (!email) {
				alert("이메일을 입력하세요.");
				return false;
			}
			let url = $(this).attr("action");
			console.log(url);
			let params = $(this).serialize(); //	form태그에 있는 name 속성값으로 파라미터 구성
			console.log(params);
			
			$.post(url, params)	//	request
			.done(function(data) { //	response callback
				if (data.code == 200) {
					alert("가입을 환영합니다. 로그인 해주세요.");
					location.href = "/user/sign-in-view"; //	로그인 화면 이동
				} else {
					// 로직 실패
					alert(data.error_message);
				}
			});
		});
	});
</script>