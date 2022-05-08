package com.boubyan.me.StudentManagmentSystem.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the student_courses database table.
 * 
 */
@Embeddable
public class UserRolePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private int id;

	@Column(name="user_id", insertable=false, updatable=false)
	private int userId;

	@Column(name="role_id", insertable=false, updatable=false)
	private int roleId;

	public UserRolePK() {
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
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof UserRolePK)) {
			return false;
		}
		UserRolePK castOther = (UserRolePK)other;
		return 
			(this.id == castOther.id)
			&& (this.userId == castOther.userId)
			&& (this.roleId == castOther.roleId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.id;
		hash = hash * prime + this.userId;
		hash = hash * prime + this.roleId;
		
		return hash;
	}
}