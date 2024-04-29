package com.sns.timeline.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sns.comment.bo.CommentBO;
import com.sns.comment.domain.CommentView;
import com.sns.like.bo.LikeBO;
import com.sns.post.bo.PostBO;
import com.sns.post.entity.PostEntity;
import com.sns.timeline.domain.CardView;
import com.sns.user.bo.UserBO;
import com.sns.user.entity.UserEntity;

@Service
public class TimelineBO {

	@Autowired
	private PostBO postBO;
	
	@Autowired
	private UserBO userBO;
	
	@Autowired
	private CommentBO commentBO;
	
	@Autowired
	private LikeBO likeBO;
	
	// input: userId(로그인/비로그인) => null일 수도 있다.
	// output: List<CardView>
	public List<CardView> generateCardViewList(Integer userId) {
		List<CardView> cardViewList = new ArrayList<>();
		
		// 글 목록을 가져온다. List<PostEntity>
		List<PostEntity> postList = postBO.getPostList();
		
		// 글 목록 반복문 순회
		// PostEntity => CardView 변경    -> CardViewList 추가
		for (PostEntity post : postList) {
			// 글(post) 하나에 대응되는 하나의 카드를 만든다.
			CardView cardView = new CardView();
			
			// 글 1개
			cardView.setPost(post);
			
			// 글쓴이 정보
			UserEntity user = userBO.getUserEntityById(post.getUserId());
			cardView.setUser(user);
			
			// 댓글들
			List<CommentView> commentList = commentBO.generateCommentViewList(post.getId());
			cardView.setCommentList(commentList);
			
			// 좋아요 개수
			int likeCount = likeBO.getLikeCountByPostId(post.getId());
			cardView.setLikeCount(likeCount);
			
			// 로그인 된 사람이 좋아요를 누른지 여부
			boolean filledLike = likeBO.filledLikeByPostIdUserId(post.getId(), userId);
			cardView.setFilledLike(filledLike);
			
			// !!! 마지막에 cardViewList에 넣는다.
			cardViewList.add(cardView);
		}
		
		return cardViewList;
	}
}



