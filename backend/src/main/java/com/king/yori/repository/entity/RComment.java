package com.king.yori.repository.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;

@Getter
@Entity
public class RComment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long commentId;
	
	@ManyToOne
	@JoinColumn(name="USER_ID")
	private User user;
	
	private String commentContent;

	public RComment(User user, String commentContent) {
		super();
		this.user = user;
		this.commentContent = commentContent;
	}
	
	
}
