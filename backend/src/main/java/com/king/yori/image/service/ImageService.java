package com.king.yori.image.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
	boolean uploadImage(int userId,MultipartFile image) throws Exception;
	
}
