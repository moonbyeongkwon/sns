package com.sns.comment;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sns.comment.bo.CommentBO;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/comment")
@RestController
public class CommentRestController {

	@Autowired
	private CommentBO commentBO;
	
	/**
	 * 댓글쓰기 API
	 * @param postId
	 * @param content
	 * @param session
	 * @return
	 */
	@PostMapping("/create")
	public Map<String, Object> create(
			@RequestParam("postId") int postId, 
			@RequestParam("content") String content,
			HttpSession session) {
		
		Map<String, Object> result = new HashMap<>();
		Integer userId = (Integer)session.getAttribute("userId");
		if (userId == null) {
			// 비로그인 -> 에러 응답
			result.put("code", 500);
			result.put("error_message", "로그인을 해주세요");
			return result;
		}
		
		// 댓글 쓰기 db insert
		commentBO.addComment(userId, postId, content);
		
		result.put("code", 200);
		result.put("result", "성공");
		return result;
	}
	
	@DeleteMapping("/delete")
	public Map<String, Object> delete(
			@RequestParam("commentId") int commentId,
			HttpSession session) {
		
		Map<String, Object> result = new HashMap<>();
		
		// 비로그인
		Integer userId = (Integer)session.getAttribute("userId");
		if (userId == null) {
			result.put("code", 500);
			result.put("error_message", "로그인을 해주세요.");
			return result;
		}
		
		// 댓글 삭제
		commentBO.deleteCommentByIdUserId(commentId, userId);
		
		result.put("code", 200);
		result.put("result", "성공");
		return result;
	}
}




