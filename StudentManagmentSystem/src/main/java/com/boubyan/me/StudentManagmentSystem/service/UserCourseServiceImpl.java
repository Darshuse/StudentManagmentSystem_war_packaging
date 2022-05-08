package com.boubyan.me.StudentManagmentSystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boubyan.me.StudentManagmentSystem.entity.Course;
import com.boubyan.me.StudentManagmentSystem.entity.User;
import com.boubyan.me.StudentManagmentSystem.entity.UserCourse;
import com.boubyan.me.StudentManagmentSystem.entity.UserCoursePK;
import com.boubyan.me.StudentManagmentSystem.repository.UserCourseRepository;

@Service
public class UserCourseServiceImpl implements UserCourseService {

	@Autowired
	UserCourseRepository repo;
//	@Autowired
//	UserServiceImpl userService;
//	@Autowired
//	CourseServiceImpl courseService;

	@Override
	public List<Course> findByUser(User user) {
		// TODO Auto-generated method stub
		List<Course> studentCourseList = repo.findByUser(user.getId());
		return studentCourseList;
	}
	
	

	@Override
	public UserCourse register(UserCourse userCourse) {
		// TODO Auto-generated method stub
//		UserCoursPK id=new UserCoursPK();
//		id.setCourseId(userCourse.getCourse().getId());
//		id.setUserId(userCourse.getUser().getId());
//		SecurityContextHolder.getContext().getAuthentication().getName();
//		userCourse.setId(id);
//		return repo.save(userCourse);
		return  repo.save(userCourse);
	}

	@Override
	public void cancel(Course course,User user) {
		// TODO Auto-generated method stub
		Optional<UserCourse> userCourse=repo.findByCourseAndUser(course.getId(),user.getId());
       repo.delete(userCourse.get());
	}

	@Override
	public List<User> findByCourse(int courseId) {
		// TODO Auto-generated method stub
		List<User> userList = repo.findByCourse(courseId);
		return userList;
	}



	@Override
	public UserCourse findById(UserCoursePK userCourse) {
		// TODO Auto-generated method stub
		return repo.findById(userCourse).orElse(null);
	}




}
