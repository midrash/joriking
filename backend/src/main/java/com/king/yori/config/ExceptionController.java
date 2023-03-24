package com.king.yori.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

/**
 * 예외처리 컨트롤러
 * 만약 유효성 검사나, 값이 찾아지지 않는 등의 에러가 발생할 경우 해당 컨트롤러로 받아서 에러를 처리한다!
 * 
 * @author bq
 *
 */
@ControllerAdvice
@RestController
public class ExceptionController {
	
	Map<String,Object> res = new HashMap<String, Object>();
	// 커플링을 낮추는게 좋지만, 별도의 HashMap에 대한 bean 정의가 되어 있지 않으므로 직접 호출하였다. 나중에 개선할 여지가 보인다.
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity catchValidException(MethodArgumentNotValidException e) {
		
		FieldError error = e.getBindingResult().getFieldError();
		
		res.put("message", error.getDefaultMessage());
		
		return new ResponseEntity<>(res,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(NullPointerException.class)
	public Object catchNullException(NullPointerException e) {
		
		res.put("message",e.getMessage());
		
		return new ResponseEntity<>(res,HttpStatus.BAD_REQUEST);
	}
	
}
