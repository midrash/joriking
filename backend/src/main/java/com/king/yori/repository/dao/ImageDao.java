package com.king.yori.repository.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.king.yori.repository.entity.Image;

/**
 * 이미지 테스트를 위한 DAO이다.
 * 
 * 디버그 용도로 쓰거나 아니면 삭제하면 된다!
 * 
 * @author bq
 *
 */
public interface ImageDao extends JpaRepository<Image, Long>{
	
}
