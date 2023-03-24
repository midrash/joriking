package com.king.yori.repository.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.king.yori.repository.entity.User;

public interface UserDao extends JpaRepository<User, Long>{

	User findByLoginIdAndPassword(String loginId,String password);
	
	@Query("select count(u) from User u where u.userId >= :start ")
	Long findByStart(@Param("start") Long start );
	
}
