package com.sns.like.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sns.like.mapper.LikeMapper;

@Service
public class LikeBO {
	
	@Autowired
	private LikeMapper likeMapper;
	
	// input: postId, userId
	// output: int(행의 개수)
	public int getLikeCountByPostIdUserId(int postId, int userId) {
		return likeMapper.selectLikeCountByPostIdOrUserId(postId, userId);
	}

	// input: postId, userId
	// output: X
	public void likeToggle(int postId, int userId) {
		if (getLikeCountByPostIdUserId(postId, userId) > 0) {
			// 만약 행이 존재하면 => 삭제(해제)
			likeMapper.deleteLikeByPostIdUserId(postId, userId);
		} else {
			// 행이 존재 하지 않으면 => 추가
			likeMapper.insertLike(postId, userId);
		}
	}
	
	// input: 글번호
	// output: 행의 개수
	public int getLikeCountByPostId(int postId) {
		return likeMapper.selectLikeCountByPostIdOrUserId(postId, null);
	}
	
	// like가 채워진지 여부
	// input: userId(로그인/비로그인), postId
	// output: 채울지 여부(boolean)
	public boolean filledLikeByPostIdUserId(int postId, Integer userId) {
		// 1. 비로그인 => 빈 하트
		if (userId == null) {
			return false;
		}
		
		// 2. 로그인 & 눌렀음 => 채워진 하트
		// 3. 로그인 & 안 눌렀음 => 빈 하트
		//return likeMapper.selectLikeCountByPostIdUserId(postId, userId) > 0 ? true : false;
		return likeMapper.selectLikeCountByPostIdOrUserId(postId, userId) > 0 ? true : false;
	}
}



