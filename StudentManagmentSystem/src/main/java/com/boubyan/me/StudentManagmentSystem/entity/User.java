package com.boubyan.me.StudentManagmentSystem.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

//	@EmbeddedId
//	private UserPK id;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private int age;

//	@NotBlank(message = "email should not be empty")
//	@Size(max = 50, message = "not allowed more than 50 characters ")
//	@Email
	private String email;

//	@NotBlank(message = "family name should not be empty ")
//	@Size(min = 3, max = 20)
	@Column(name = "family_name")
	private String familyName;

//	@NotBlank(message = "username should not be empty ")
//	@Size(min = 3, max = 20, message = "username should be more than 2 characters and less than 21 characters")
	@Column(name = "first_name")
	private String firstName;

	@JsonIgnore
//	@NotBlank(message = "password should not be empty ")
//	@Size(min = 8, message = "password should be more than 8 characters")
	@Column(name = "password")
	private String password;

	@JsonIgnore
	// bi-directional many-to-one association to User Course
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<UserCourse> userCourses;

	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	public User( String firstName,String email, String password) {
		super();
		this.email = email;
		this.firstName = firstName;
		this.password = password;
	}

	public User() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAge() {
		return this.age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFamilyName() {
		return this.familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<UserCourse> getUserCourses() {
		return this.userCourses;
	}

	public void setUserCourses(List<UserCourse> userCourses) {
		this.userCourses = userCourses;
	}

	public UserCourse addUserCourse(UserCourse userCourse) {
		getUserCourses().add(userCourse);
		userCourse.setUser(this);

		return userCourse;
	}

	public UserCourse removeUserCours(UserCourse userCourse) {
		getUserCourses().remove(userCourse);
		userCourse.setUser(null);

		return userCourse;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	
}