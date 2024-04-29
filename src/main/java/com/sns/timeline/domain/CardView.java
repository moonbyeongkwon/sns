package com.sns.timeline.domain;

import java.util.List;

import com.sns.comment.domain.CommentView;
import com.sns.post.entity.PostEntity;
import com.sns.user.entity.UserEntity;

import lombok.Data;
import lombok.ToString;

// 글 1개와 매핑
@ToString
@Data // getter, setter
public class CardView {
	// 글 1개
	private PostEntity post;
	
	// 글쓴이 정보
	private UserEntity user;
	
	// 댓글 n개
	private List<CommentView> commentList;
	
	// 좋아요 개수
	private int likeCount;
	
	// 좋아요 여부(로그인 된 사람)
	private boolean filledLike;
}



