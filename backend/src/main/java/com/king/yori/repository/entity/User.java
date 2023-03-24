package com.king.yori.repository.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.king.yori.repository.dto.UserDto;

import lombok.Getter;

@Getter
@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	@JsonIgnore
	private Long userId;
	private String loginId;
	private String nickname;
	private String email;
	
	@JsonIgnore	// 패스워드는 json으로 보내지 않기 위해서 사용
	@Column(updatable=false)
	private String password;

	
	
	
	public User(String loginId, String nickname, String email, String password) {
		super();
		this.loginId = loginId;
		this.nickname = nickname;
		this.email = email;
		this.password = password;
	}

	public User(Long userId, String loginId, String nickname, String email) {
		super();
		this.userId = userId;
		this.loginId = loginId;
		this.nickname = nickname;
		this.email = email;
	}
	
	public User(UserDto.SignupRequest signupRequest) {
		
		this.email    =  signupRequest.getEmail();
		this.loginId  =  signupRequest.getLoginId();
		this.nickname =  signupRequest.getNickname();
		this.password =  signupRequest.getPassword();
	}
	
	public void modifyByNicknameAndEmail(String nickname, String email) {
		this.email = email;
		this.nickname = nickname;
	}

	public User() {
	}
	
}
