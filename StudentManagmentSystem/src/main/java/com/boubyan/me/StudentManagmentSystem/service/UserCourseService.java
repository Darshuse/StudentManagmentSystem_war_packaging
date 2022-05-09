package com.boubyan.me.StudentManagmentSystem.service;

import java.util.List;

import com.boubyan.me.StudentManagmentSystem.entity.Course;
import com.boubyan.me.StudentManagmentSystem.entity.User;
import com.boubyan.me.StudentManagmentSystem.entity.UserCourse;
import com.boubyan.me.StudentManagmentSystem.entity.UserCoursePK;

public interface UserCourseService {
	public List<Course> findByUser(User user);

	public UserCourse register(UserCourse userCourse);
	
	public UserCourse findById( int userCourse);

	public void cancel(Course course,User user);

	public List<User> findByCourse(int course);
	
    
}