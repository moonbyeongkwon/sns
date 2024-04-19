package com.sns.user.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sns.user.entity.UserEntity;
import com.sns.user.repository.UserRepository;

@Service
public class UserBO {

	@Autowired
	private UserRepository userRepository;
	
	//	input:	loginId
	//	output:	UserEntity or null
	public UserEntity getUserEntityByLoginId(String loginId) {
		return userRepository.findByLoginId(loginId);
	}
	
	//	input:	파라미터 4개
	//	output:	Integer(user pk)
	public Integer addUser(String loginId, String password, String name, String email) {
		UserEntity user = userRepository.save(UserEntity.builder()
								.loginId(loginId)
								.password(password)
								.name(name)
								.email(email)
								.build());
		
		return user == null ? null : user.getId();
	}
	
	
}
