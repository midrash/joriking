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
@IdClass(Recipe.class)
public class RecipeRComment implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@ManyToOne
	@JoinColumn(name="RECIPE_ID")
	private Recipe recipe;
	
	@ManyToOne
	@JoinColumn(name="COMMENT_ID")
	private RComment comment;

	public RecipeRComment(Recipe recipe, RComment comment) {
		super();
		this.recipe = recipe;
		this.comment = comment;
	}
	
//	@ManyToOne
//	@JoinColumn(name="userId")
//	private User user;
	
	
	
}
