package com.example.demo.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Table;




@Entity
@Table(name = "token")
public class Token {
	@javax.persistence.Id
    @javax.persistence.GeneratedValue(strategy =javax.persistence.GenerationType.IDENTITY)
	  @javax.persistence.Column(name = "id")

	private int id;
    @javax.persistence.Column(name = "token",nullable = false, unique = true)

	private String token;
    @javax.persistence.Column(name = "expirationDateTime")

	private LocalDateTime expirationDateTime;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public LocalDateTime getExpirationDateTime() {
		return expirationDateTime;
	}
	public void setExpirationDateTime(LocalDateTime expirationDateTime) {
		this.expirationDateTime = expirationDateTime;
	}
	public Token()
	{
		
	}
	public Token(int id, String token, LocalDateTime expirationDateTime) {
		this.id = id;
		this.token = token;
		this.expirationDateTime = expirationDateTime;
	}
	
}
