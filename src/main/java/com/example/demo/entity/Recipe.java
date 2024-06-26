package com.example.demo.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity
@Table(name="recipes")
public class Recipe {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@Column(name="category_id")
	private Integer categoryId;
	@Column(name="user_name")
	private String userName;
	@Column(name="recipe_name")
	private String name;
	private String materials;
	private String contents;
	public Recipe() {};
	
	
	public Recipe(Integer categoryId, String userName, String name, String materials, String contents) {
		super();
		this.categoryId = categoryId;
		this.userName = userName;
		this.name = name;
		this.materials = materials;
		this.contents = contents;
	}


	public Recipe(Integer id, Integer categoryId, String userName, String name, String materials,
			String contents) {
		super();
		this.id = id;
		this.categoryId = categoryId;
		this.userName = userName;
		this.name = name;
		this.materials = materials;
		this.contents = contents;
	}
	public Integer getId() {
		return id;
	}
	public Integer getCategoryId() {
		return categoryId;
	}
	public String getUserName() {
		return userName;
	}
	public String getName() {
		return name;
	}
	public String getMaterial() {
		return materials;
	}
	public String getContents() {
		return contents;
	}
}
