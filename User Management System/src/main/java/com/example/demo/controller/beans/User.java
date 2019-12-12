package com.example.demo.controller.beans;

public class User {
	
	int key;
	String name;
	String lastname;
	public User(int key,String name,String lastname) {
		// TODO Auto-generated constructor stub
		this.key = key;
		this.name = name;
		this.lastname = lastname;
	}
	
	public int getKey() {
		return key;
	}
	
	public void setKey(int key) {
		this.key = key;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getLastname() {
		return lastname;
	}
	
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
}
