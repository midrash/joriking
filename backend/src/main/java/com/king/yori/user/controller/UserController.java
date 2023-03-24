package com.king.yori.user.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.king.yori.repository.dto.UserDto;
import com.king.yori.repository.entity.User;
import com.king.yori.user.service.UserService;

import io.swagger.annotations.ApiOperation;


@CrossOrigin(origins = { "http://localhost:3000","http://192.168.0.10:3000" })
@RestController
@RequestMapping("/user")
public class UserController {
	
	
	@Autowired
	UserService service;
	
	Map<String,Object> res = new HashMap<>();
	// 커플링을 낮추는게 좋지만, 별도의 HashMap에 대한 bean 정의가 되어 있지 않으므로 직접 호출하였다. 나중에 개선할 여지가 보인다.
	
	@PostMapping("/login")
	@ApiOperation(value = "로그인")
	public Object login(@Valid @RequestBody UserDto.LoginRequest req) throws Exception {
		
		res.put("message", "success");
		res.put("jwt", service.getJwt(req.getLoginId(), req.getPassword()));
		return new ResponseEntity<>(res,HttpStatus.OK);
	}
	
	
	@PostMapping("/signup")
	@ApiOperation(value = "회원가입")
	public Object signup(@RequestBody UserDto.SignupRequest req) {
		
		User user = new User(req);
		service.createUser(user);
		
		res.put("message", "success");
		return new ResponseEntity<>(res,HttpStatus.OK);
	}
	
	@PutMapping("/modify")
	@ApiOperation(value = "회원 정보 수정")
	public Object modify(@RequestBody UserDto.ModifyRequest req) {
		
		Optional<User> user;
		try {
			user = service.verifyJWT(req.getJwt());
			service.updateUser(user.get(),req.getNickname(),req.getEmail());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		res.put("message", "success");
		return new ResponseEntity<>(res,HttpStatus.OK);
	}
	
	@DeleteMapping("/delete")
	@ApiOperation(value = "회원 정보 삭제")
	public Object delete(@RequestBody UserDto.DeleteRequest req) {
		try {
			Optional<User> user = service.verifyJWT(req.getJwt());
			service.deleteUser(user.get());
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		res.put("message", "success");
		return new ResponseEntity<>(res,HttpStatus.OK);
	}
	
	@GetMapping("/count/{start}")
	public Object total(@RequestParam Long start) {
		
		res.put("message", "success");
		res.put("count",service.getTotalUsers(start));
		return new ResponseEntity<>(res,HttpStatus.OK);
	}
	
}
