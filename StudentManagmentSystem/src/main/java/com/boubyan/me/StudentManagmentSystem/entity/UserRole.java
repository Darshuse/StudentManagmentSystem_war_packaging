package com.boubyan.me.StudentManagmentSystem.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the user_roles database table.
 * 
 */
@Entity
@Table(name="user_roles")
@NamedQuery(name="UserRole.findAll", query="SELECT u FROM UserRole u")
public class UserRole implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private UserRolePK id;
	
	
	@ManyToOne(cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="user_id",insertable=false, updatable=false)
	private User user;

	//bi-directional many-to-one association to Student
	
	@Column(name = "role_id",insertable=false, updatable=false)
	private Role role;

	public UserRole() {
	}

	public UserRolePK getId() {
		return this.id;
	}

	public void setId(UserRolePK id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	
	
}