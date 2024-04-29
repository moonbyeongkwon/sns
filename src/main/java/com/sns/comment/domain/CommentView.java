package com.sns.comment.domain;

import com.sns.user.entity.UserEntity;

import lombok.Data;
import lombok.ToString;

// 댓글 1개와 매핑
@ToString
@Data
public class CommentView {
	// 댓글 1개
	private Comment comment;
	
	// 댓글쓴이
	private UserEntity user;
}