package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id; 
	
	@Column(name="user_name")
	private String name; 
	
	private String password;
	
	public User() {}
	
	public User(Integer id, String name, String password) {
		this.id=id;
		this.name=name;
		this.password=password;
	}
	
	public User(String name, String password) {
		this.name=name;
		this.password=password;
	}

	public String getPassword() {
		return password;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}