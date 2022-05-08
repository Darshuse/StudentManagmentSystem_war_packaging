package com.boubyan.me.StudentManagmentSystem.entity;

import java.io.Serializable;

import javax.persistence.Column;

public class UserCoursePK implements Serializable {

	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private int id;

	@Column(name = "user_id", insertable = false, updatable = false)
	private int userId;

	@Column(name = "course_id", insertable = false, updatable = false)
	private int courseId;

	public UserCoursePK() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public boolean equals(Object other) {
			if (this == other) {
				return true;
			}
			if (!(other instanceof UserCoursePK)) {
				return false;
			}
			UserCoursePK castOther = (UserCoursePK)other;
			return 
				(this.id == castOther.id)
				&& (this.userId == castOther.userId)
				&& (this.courseId == castOther.courseId);
		}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.id;
		hash = hash * prime + this.userId;
		hash = hash * prime + this.courseId;

		return hash;
	}
}
