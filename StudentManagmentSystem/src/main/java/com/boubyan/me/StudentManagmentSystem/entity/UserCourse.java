package com.boubyan.me.StudentManagmentSystem.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the user_courses database table.
 * 
 */
@Entity
@Table(name="user_courses")
@NamedQuery(name="UserCourse.findAll", query="SELECT u FROM UserCourse u")
public class UserCourse implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private UserCoursePK id;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="course_id",insertable=false, updatable=false)
	private Course course;

	//bi-directional many-to-one association to Student
	
	@Column(name = "user_id",insertable=false, updatable=false)
	private User user;


	public UserCourse() {
	}

	public UserCoursePK getId() {
		return this.id;
	}

	public void setId(UserCoursePK id) {
		this.id = id;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	

}