package com.boubyan.me.StudentManagmentSystem.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.boubyan.me.StudentManagmentSystem.entity.Course;
import com.boubyan.me.StudentManagmentSystem.entity.User;
import com.boubyan.me.StudentManagmentSystem.exception.CourseNotFoundException;
import com.boubyan.me.StudentManagmentSystem.exception.UserNotFoundException;
import com.boubyan.me.StudentManagmentSystem.service.UserService;

@RestController
@RequestMapping(path = "/users")
public class UserController {

	@Autowired
	UserService service;

	@GetMapping
	public List<User> findAll() {
		List<User> userList = service.findAll();
		if (userList == null || userList.isEmpty())
			throw new UserNotFoundException("user list empty");
		return userList;
	}

	@GetMapping(path = "/{id}")
	public User findById(@PathVariable int id) {
		return service.findById(id);

	}

	@PostMapping
	public ResponseEntity<User> save(@RequestBody @Valid User user) {

		User savedUser = service.save(user);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId())
				.toUri();
		return ResponseEntity.created(location).build();
	}

	@PutMapping
	public User update(@RequestBody @Valid User user) {
		User updatedUser = service.update(user);

		return updatedUser;
	}

	@GetMapping(path = "/{id}/courses")
	public List<Course> findCourseList(@PathVariable int id) {
		User user = service.findById(id);
		List<Course> userCourseList = service.findCourseList(user);
		if (userCourseList == null || userCourseList.isEmpty())
			throw new CourseNotFoundException(" no courses found");

		return userCourseList;

	}

//	@PutMapping(path = "/{id}/courses/{courseId}")
//	public void cancelCourse(@PathVariable int id, @PathVariable int courseId) {
//		service.cancelCourse(id, courseId);
//
//	}

}
