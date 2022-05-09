package com.boubyan.me.StudentManagmentSystem.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the courses database table.
 * 
 */
@Entity
@Table(name="courses")
@NamedQuery(name="Course.findAll", query="SELECT c FROM Course c")
public class Course implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotBlank(message = "description should not be empty ")
	@Size(min = 10, message = "description should be at least 10 characters ")
	@Column(name = "course_description")
	private String courseDescription;

	@NotBlank(message = "course schedule name should not be empty ")
	@Size(min = 5, message = "course schedule name should be at least 5 characters ")

	@Column(name = "course_schedule")
	private String courseSchedule;

	@JsonIgnore
	// bi-directional many-to-one association to StudentCours
	@OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "course_id")
	private List<UserCourse> userCourses;

	public Course() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCourseDescription() {
		return this.courseDescription;
	}

	public void setCourseDescription(String courseDescription) {
		this.courseDescription = courseDescription;
	}

	public String getCourseSchedule() {
		return this.courseSchedule;
	}

	public void setCourseSchedule(String courseSchedule) {
		this.courseSchedule = courseSchedule;
	}

	public List<UserCourse> getUserCourses() {
		return this.userCourses;
	}

	public void setUserCourses(List<UserCourse> userCourses) {
		this.userCourses = userCourses;
	}

	public UserCourse addUserCours(UserCourse userCourse) {
		getUserCourses().add(userCourse);
		userCourse.setCourse(this);

		return userCourse;
	}

	public UserCourse removeStudentCours(UserCourse userCourse) {
		getUserCourses().remove(userCourse);
		userCourse.setCourse(null);

		return userCourse;
	}

}