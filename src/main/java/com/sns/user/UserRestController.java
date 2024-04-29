package com.sns.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sns.common.EncryptUtils;
import com.sns.user.bo.UserBO;
import com.sns.user.entity.UserEntity;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/user")
@RestController
public class UserRestController {

	@Autowired
	private UserBO userBO;
	
	/**
	 * 로그인 아이디 중복 확인 API
	 * @param loginId
	 * @return
	 */
	@RequestMapping("/is-duplicated-id")
	public Map<String, Object> isDuplicationId(
			@RequestParam("loginId") String loginId) {
		
		UserEntity user = userBO.getUserEntityByLoginId(loginId);
		
		Map<String, Object> result = new HashMap<>();
		if (user != null) { // 중복
			result.put("code", 200);
			result.put("is_duplicated_id", true);
		} else { // 사용 가능
			result.put("code", 200);
			result.put("is_duplicated_id", false);
		}
		return result;
	}
	
	/**
	 * 회원가입 API
	 * @param loginId
	 * @param password
	 * @param name
	 * @param email
	 * @return
	 */
	@PostMapping("/sign-up")
	public Map<String, Object> signUp(
			@RequestParam("loginId") String loginId,
			@RequestParam("password") String password,
			@RequestParam("name") String name,
			@RequestParam("email") String email) {
		
		// md5 알고리즘- 암호 hashing
		String hashedPassword = EncryptUtils.md5(password);
		
		Integer userId = userBO.addUser(loginId, hashedPassword, name, email);
		
		Map<String, Object> result = new HashMap<>();
		if (userId != null) {
			result.put("code", 200);
			result.put("result", "성공");
		} else {
			result.put("code", 500);
			result.put("error_message", "회원가입에 실패했습니다.");
		}
		
		return result;
	}
	
	/**
	 * 로그인 API
	 * @param loginId
	 * @param password
	 * @param session
	 * @return
	 */
	@PostMapping("/sign-in")
	public Map<String, Object> signIn(
			@RequestParam("loginId") String loginId,
			@RequestParam("password") String password,
			HttpSession session) {
		
		// 비밀번호 md5 hashing
		String hashedPassword = EncryptUtils.md5(password);
		
		// db 조회(loginId, hashing 비번)
		UserEntity user = userBO.getUserEntityByLoginIdPassword(loginId, hashedPassword);
		
		// 응답값
		Map<String, Object> result = new HashMap<>();
		if (user != null) { // 성공
			// 로그인 처리 => 로그인 정보를 세션에 담는다.(사용자 마다)
			session.setAttribute("userId", user.getId());
			session.setAttribute("userLoginId", user.getLoginId());
			session.setAttribute("userName", user.getName());
			
			result.put("code", 200);
			result.put("result", "성공");
		} else { // 로그인 불가
			result.put("code", 300);
			result.put("error_message", "존재하지 않는 사용자입니다.");
		}
		return result;
	}
}