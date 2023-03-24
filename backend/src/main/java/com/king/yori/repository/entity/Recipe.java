package com.king.yori.repository.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ColumnDefault;

import lombok.Getter;

@Getter
@Entity
public class Recipe implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer recipeId;
	
	@ManyToOne
	@JoinColumn(name="USER_ID")
	private User user;
	
	private String recipeName;
	
	private String recipeIngredinets;		
	// 원래는 json타입이여서 converter를 만들어 주는 것이 맞지만,json으로 성분이 날라오면 그냥 쓰면 된다.
	private String recipeOrder;
	// 나중에 이미지 url과 해당 순서당 설명을 같이 매핑해서 저장해두면 된다!
	@Column
	@ColumnDefault("0")
	private Boolean recipeIsVegi;
	public Recipe(User user, String recipeName, String recipeIngredinets, String recipeOrder, Boolean recipeIsVegi) {
		super();
		this.user = user;
		this.recipeName = recipeName;
		this.recipeIngredinets = recipeIngredinets;
		this.recipeOrder = recipeOrder;
		this.recipeIsVegi = recipeIsVegi;
	}
	
	
	
	
}
