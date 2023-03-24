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
@IdClass(Tag.class)
public class RecipeTag implements Serializable{
	
	private static final long serialVersionUID = 1L;


	@Id
	@ManyToOne
	@JoinColumn(name = "TAG_ID")
	private Tag tag;
	
	
//	@ManyToOne
//	@JoinColumn(name = "user_id")
//	private User user;
	
	@ManyToOne
	@JoinColumn(name = "RECIPE_ID")
	private Recipe recipe;

	public RecipeTag() {}

	public RecipeTag(Tag tag, Recipe recipe) {
		super();
		this.tag = tag;
		this.recipe = recipe;
	}
	
	
	
	
	
}
