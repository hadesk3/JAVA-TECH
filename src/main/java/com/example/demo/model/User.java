package com.example.demo.model;

import java.util.Set;






@javax.persistence.Entity
@javax.persistence.Table(name = "users")
public class User {
    @javax.persistence.Id
    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    private Long id;

    @javax.persistence.Column(nullable = false, unique = true)
    private String username;

    @javax.persistence.Column(nullable = false)
    private String password;
    private String fullName;
    private String avatar;


    @javax.persistence.Column(nullable = true)
    private boolean enabled;

    @javax.persistence.OneToMany(mappedBy = "user", cascade = javax.persistence.CascadeType.ALL, fetch =javax.persistence.FetchType.EAGER)
    private Set<Role> roles;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	public User()
	{
		
	}
	public User(Long id, String username, String password, boolean enabled, Set<Role> roles) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.roles = roles;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	

	
    // constructors, getters, setters
}