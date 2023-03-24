package com.king.yori.user.service;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

import com.king.yori.repository.entity.User;

public interface UserService {
	//토큰 만들기
    public String createToken(User user);

    //토큰 검증
    public Optional<User> verifyJWT(String jwt) throws UnsupportedEncodingException;
    
    
    public String getJwt(String loginId,String password) throws Exception;
    
    public void createUser(User user);
    
    public void updateUser(User user,String nickname,String email);
    
    public void deleteUser(User user);
    
	public Long getTotalUsers(Long start) ;
    
	
}
