package com.sns.like;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sns.like.bo.LikeBO;

import jakarta.servlet.http.HttpSession;

@RestController
public class LikeRestController {
	
	@Autowired
	private LikeBO likeBO;
	
	// GET: /like?postId=13    @RequestParam("postId")
	// GET: /like/13	 	   @PathVariable
	@RequestMapping("/like/{postId}")
	public Map<String, Object> likeToggle(
			@PathVariable(name = "postId") int postId,
			HttpSession session) {
		
		Map<String, Object> result = new HashMap<>();
		
		// 로그인 여부 확인
		Integer userId = (Integer)session.getAttribute("userId");
		if (userId == null) {
			result.put("code", 500);
			result.put("error_message", "좋아요를 하려면 로그인을 해주세요");
			return result;
		}
		
		// BO 호출 => logic은 BO에서
		likeBO.likeToggle(postId, userId);
		
		// 응답값
		result.put("code", 200);
		result.put("result", "성공");
		return result;
	}
}

