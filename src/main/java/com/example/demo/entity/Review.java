package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="reviews")
public class Review {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id; 
	
	@Column(name="recipe_id")
	private Integer recipeId;
	
	@Column(name="review_name")
	private String name;
	
	private String comments;
	
	public Review() {};
	public Review(Integer recipeId,String name, String comments) {
		this.recipeId=recipeId;
		this.name=name;
		this.comments=comments;
	}
	public Integer getId() {
		return id;
	}
	public Integer getRecipeId() {
		return recipeId;
	}
	
	public String getName() {
		return name;
	}
	public String getComments() {
		return comments;
	}
	
	

}
