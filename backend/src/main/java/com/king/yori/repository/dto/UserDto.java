package com.king.yori.repository.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class UserDto {
	
	@Getter
	@Setter
	@ToString
	@Valid
	public static class LoginRequest{
		@Size(min=4, message = "최소 4자리 이상 되어야합니다!")
		private String loginId;
		private String password;
	}
	
	@Getter
	@Setter
	@ToString
	public static class SignupRequest{
		@Size(min=4, message = "최소 4자리 이상 되어야합니다!")
		private String loginId;
		private String password;
		private String nickname;
		private String email;
	}
	
	@Getter
	@Setter
	@ToString
	public static class ModifyRequest{
		private String jwt;
		private String nickname;
		private String email;
	}
	
	@Getter
	@Setter
	@ToString
	public static class DeleteRequest{
		private String jwt;
	}
	
}
