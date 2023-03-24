package com.king.yori.repository.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * 테스트를 위한 dto이다.
 * 실제 joriking 어플에서는 사용하지 않는다!
 * 
 * @author bq
 *
 */
public class ImageDto {
	
	
	@Getter
	@Setter
	@ToString
	public static class UploadRequest{
		private String message;
		private MultipartFile file; 
	}
	
	
}
