package com.sns.post.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sns.comment.bo.CommentBO;
import com.sns.common.FileManagerService;
import com.sns.like.bo.LikeBO;
import com.sns.post.entity.PostEntity;
import com.sns.post.repository.PostRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PostBO {

	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private FileManagerService fileManagerService;
	
	@Autowired
	private CommentBO commentBO;
	
	@Autowired
	private LikeBO likeBO;
	
	public List<PostEntity> getPostList() {
		return postRepository.findAllByOrderByIdDesc();
	}
	
	public PostEntity addPost(int userId, String userLoginId, String content, MultipartFile file) {

		String imagePath = null;

		// 이미지가 있으면 업로드 후 imagePath를 받아옴
		if (file != null) {
			imagePath = fileManagerService.saveFile(userLoginId, file);
		}

		return postRepository.save(PostEntity.builder()
							.userId(userId)
							.content(content)
							.imagePath(imagePath)
							.build());
	}
	
	public void deletePostByPostIdUserId(int postId, int userId) {
		// 기존글 가져오기
		PostEntity post = postRepository.findById(postId).orElse(null);
		if (post == null) {
			log.error("[글 삭제] post is null. postId:{}, userId:{}", postId, userId);
			return;
		}
		
		// 글 삭제
		postRepository.delete(post);
		
		// 이미지 있으면 삭제
		fileManagerService.deleteFile(post.getImagePath());
		
		// 댓글들 삭제
		commentBO.deleteCommentsByPostId(post.getId());
		
		// 좋아요들 삭제
		likeBO.deleteLikeByPostId(post.getId());
	}
}