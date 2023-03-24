package com.king.yori.image.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.king.yori.image.service.ImageService;

import io.swagger.annotations.ApiOperation;

/**
 * 
 * rest api로 이미지를 컨트롤하는 것을 테스트 하기 위한 컨트롤러이다!
 * 실 joriking의 어플에선 사용하지 않지만, 테스트 겸 디버그 용도로 사용하자!
 * 
 * @author bq
 *
 */
@RestController
@RequestMapping("/image")
public class ImageController {

	@Autowired
	ImageService imageService;
	
	
	
	@PostMapping("/upload")
	@ApiOperation(value = "이미지 업로드", notes = "이미지 등록(upload) swagger", produces = "multipart/form-data")
	public Object upload(int userId, MultipartFile image){
		
		try {
			imageService.uploadImage(userId, image);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}
	
	
}
