package com.king.yori.repository.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;

@Getter
@Entity
@IdClass(User.class)
public class RecipeLike implements Serializable{
	
	@Id
	@ManyToOne
	@JoinColumn(name="USER_ID")
	private User user;
	
	@ManyToOne
	@JoinColumn(name="RECIPE_ID")
	private Recipe recipe;

	public RecipeLike(User user, Recipe recipe) {
		super();
		this.user = user;
		this.recipe = recipe;
	}
	

}
