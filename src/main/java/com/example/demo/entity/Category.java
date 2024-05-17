package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "categories")
public class Category {
	@Id
	private Integer id; // カテゴリーID
	
	@Column(name="category_name")

	private String name; // カテゴリー名

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}