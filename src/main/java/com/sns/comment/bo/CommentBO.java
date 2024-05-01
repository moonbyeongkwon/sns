package com.sns.comment.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sns.comment.domain.Comment;
import com.sns.comment.domain.CommentView;
import com.sns.comment.mapper.CommentMapper;
import com.sns.user.bo.UserBO;
import com.sns.user.entity.UserEntity;

@Service
public class CommentBO {
	@Autowired
	private CommentMapper commentMapper;
	
	@Autowired
	private UserBO userBO;

	public void addComment(int userId, int postId, String content) {
		commentMapper.insertComment(userId, postId, content);
	}
	
	// input:글번호
	// output:List<CommentView> => timelineBO
	public List<CommentView> generateCommentViewList(int postId) {
		// 결과 리스트 생성
		List<CommentView> commentViewList = new ArrayList<>();
		
		// 댓글 목록
		List<Comment> commentList = commentMapper.selectCommentListByPostId(postId);
		
		// 반복문 순회: comment => CommentView  -> 리스트 담기
		for (Comment comment : commentList) {
			CommentView commentView = new CommentView();
			
			// 댓글
			commentView.setComment(comment);
			
			// 댓글쓴이
			UserEntity user = userBO.getUserEntityById(comment.getUserId());
			commentView.setUser(user);
			
			// !!! 리스트에 담기
			commentViewList.add(commentView);
		}
		
		// 결과 반환
		return commentViewList;
	}
	
	public void deleteCommentByIdUserId(int commentId, int userId) {
		commentMapper.deleteCommentByIdUserId(commentId, userId);
	}

	public void deleteCommentsByPostId(int postId) {
		commentMapper.deleteCommentsByPostId(postId);
	}
	
	
}


