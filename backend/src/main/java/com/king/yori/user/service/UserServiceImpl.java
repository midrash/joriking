package com.king.yori.user.service;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.king.yori.repository.dao.UserDao;
import com.king.yori.repository.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class UserServiceImpl implements UserService{
	
	
	final static String key = "나중에 이거 바꿔서 하면 된다!";
	
	@Autowired
	UserDao userDao;
	
	
    public String createToken(User user) {

        //Header 부분 설정
        Map<String, Object> headers = new HashMap<>();
        headers.put("typ", "JWT");
        headers.put("alg", "HS256");

        //payload 부분 설정
        Map<String, Object> payloads = new HashMap<>();        
        payloads.put("userId", user.getUserId());
        payloads.put("loginId", user.getLoginId());
        payloads.put("email", user.getEmail());
        payloads.put("nickname", user.getNickname());

        Long expiredTime = 1000 * 60L * 60L * 2L; // 토큰 유효 시간 (2시간)

        Date ext = new Date(); // 토큰 만료 시간
        ext.setTime(ext.getTime() + expiredTime);
     
        // 토큰 Builder
        String jwt = Jwts.builder()
                .setHeader(headers) // Headers 설정
                .setClaims(payloads) // Claims 설정
                .setSubject("user") // 토큰 용도 
                .setExpiration(ext) // 토큰 만료 시간 설정
                .signWith(SignatureAlgorithm.HS256, key.getBytes()) // HS256과 Key로 Sign
                .compact(); // 토큰 생성

        return jwt;
    }
    
    //토큰 검증
    public Optional<User> verifyJWT(String jwt) throws UnsupportedEncodingException {
        Map<String, Object> claimMap = null;
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(key.getBytes("UTF-8")) // Set Key
                    .parseClaimsJws(jwt) // 파싱 및 검증, 실패 시 에러
                    .getBody();

            claimMap = claims;

            //Date expiration = claims.get("exp", Date.class);
            //String data = claims.get("data", String.class);
            
        } catch (ExpiredJwtException e) { // 토큰이 만료되었을 경우
            System.out.println(e);
        } catch (Exception e) { // 그외 에러났을 경우
            System.out.println(e);
        }
        
        // 맵으로 된 값을 다시 class로 집어넣는다.
        Optional<User> user = userDao.findById(((Integer)claimMap.get("userId")).longValue());
        
        return user;
    }    
    
    public String getJwt(String loginId,String password) throws Exception {
    	User user = userDao.findByLoginIdAndPassword(loginId, password);
		
		if( user == null ) throw new NullPointerException("fail login");		// 의도적으로 에러를 만들기 위해 던졌다.
		
		String jwt = createToken(user);
		
		try {
			verifyJWT(jwt);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return jwt;
    }
    
    public void createUser(User user) {
		userDao.save(user);
    }
    
    public void updateUser(User user,String nickname,String email) {
		user.modifyByNicknameAndEmail(nickname, email);
		userDao.flush();
    }
    
    public void deleteUser(User user) {
    	userDao.delete(user);
    }
	public Long getTotalUsers(Long start) {
		return userDao.findByStart(start);
	}
    
	
}
