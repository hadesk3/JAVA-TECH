package com.example.demo.model;


@javax.persistence.Entity
@javax.persistence.Table(name = "authorities")
public class Role {
  @javax.persistence.Id
   @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    private Long id;

    @javax.persistence.ManyToOne
    @javax.persistence.JoinColumn(name = "user_id", nullable = false)
    private User user;

    @javax.persistence.Column(nullable = false)
    private String authority;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}
	public Role()
	{
		
	}
	public Role(Long id, User user, String authority) {

		this.id = id;
		this.user = user;
		this.authority = authority;
	}

    // constructors, getters, setters
}