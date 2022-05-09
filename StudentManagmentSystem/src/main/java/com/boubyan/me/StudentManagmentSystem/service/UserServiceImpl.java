package com.boubyan.me.StudentManagmentSystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boubyan.me.StudentManagmentSystem.entity.Course;
import com.boubyan.me.StudentManagmentSystem.entity.User;
import com.boubyan.me.StudentManagmentSystem.exception.UserNotFoundException;
import com.boubyan.me.StudentManagmentSystem.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository repo;

	@Autowired
	UserCourseServiceImpl userCourseService;

//	@Autowired
//	CourseServiceImpl coursService;
	// Student student, Course course

	@Override
	public List<User> findAll() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public User findById(int userId) {
		// TODO Auto-generated method stub
		Optional<User> user = repo.findById(userId);
		return user.orElseThrow(() -> new UserNotFoundException("Error: User is not found with id: " + userId));
	}

	@Override
	public User update(User user) {
		// TODO Auto-generated method stub

		User updatedStudent = findById(user.getId());
		if (updatedStudent != null) {
			updatedStudent.setId(user.getId());
			updatedStudent.setAge(user.getAge());
			updatedStudent.setEmail(user.getEmail());
			updatedStudent.setFirstName(user.getFirstName());
			updatedStudent.setFamilyName(user.getFamilyName());
			save(updatedStudent);
		}
		return updatedStudent;
	}

	@Override
	public User save(User user) {
		// TODO Auto-generated method stub
		return repo.save(user);
	}

	@Override
	public List<Course> findCourseList(User user) {
		// TODO Auto-generated method stub
		return userCourseService.findByUser(user);
	}

	@Override
	public User findByUserName(String userName) {
		// TODO Auto-generated method stub
		return repo.findByFirstName(userName)
				.orElseThrow(() -> new UserNotFoundException("Error: User is not found with userName: " + userName));
	}

	@Override
	public User findByEmail(String email) {
		// TODO Auto-generated method stub
		return repo.findByEmail(email)
				.orElseThrow(() -> new UserNotFoundException("Error: User is not found with email: " + email));
	}

	@Override
	public Boolean existsByFirstName(String firstName) {
		// TODO Auto-generated method stub
		return repo.existsByFirstName(firstName);
	}

	@Override
	public Boolean existsByEmail(String email) {
		// TODO Auto-generated method stub
		return repo.existsByEmail(email);
	}

}
