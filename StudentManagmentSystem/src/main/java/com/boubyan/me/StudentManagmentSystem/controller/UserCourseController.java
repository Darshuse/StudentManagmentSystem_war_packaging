package com.boubyan.me.StudentManagmentSystem.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.boubyan.me.StudentManagmentSystem.entity.UserCourse;
import com.boubyan.me.StudentManagmentSystem.service.UserCourseServiceImpl;

@RestController
@RequestMapping(path = "userCourse")
public class UserCourseController {
	
	@Autowired
	UserCourseServiceImpl service;
	
//	@PostMapping("/register")
//	public UserCourse save(@RequestBody @Valid UserCourse userCourse ) {
//		
//		return service.register(userCourse);
//	}
//	
//	@DeleteMapping("/cancel")
//	public void cancel(@RequestParam(name = "userId") int userId,
//			@RequestParam(name = "coursId") int courseId) {
//		service.cancel(userId,courseId);
//	}

}
