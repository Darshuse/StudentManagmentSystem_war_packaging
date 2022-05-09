package com.boubyan.me.StudentManagmentSystem.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.boubyan.me.StudentManagmentSystem.entity.Course;
import com.boubyan.me.StudentManagmentSystem.entity.User;
import com.boubyan.me.StudentManagmentSystem.entity.UserCourse;
import com.boubyan.me.StudentManagmentSystem.exception.CourseNotFoundException;
import com.boubyan.me.StudentManagmentSystem.service.CourseServiceImpl;

import net.spy.memcached.MemcachedClient;

@RestController
@RequestMapping("/courses")
public class CourseController {

	@Autowired
	CourseServiceImpl service;

	@Autowired
	private MemcachedClient memcachedClient;

	@Value("${courseSchedule.path}")
	String path;

	@GetMapping
	public List<Course> findAll() {
		List<Course> cachedCourseList;
		;
		if ( memcachedClient.get("all_courses") != null ) {
			cachedCourseList= (List<Course>) memcachedClient.get("all_courses");
			return cachedCourseList;
		}

		List<Course> courseList = service.findAll();
		if (courseList == null || courseList.isEmpty())
			throw new CourseNotFoundException("empt course list");
		cachedCourseList = (List<Course>) memcachedClient.set("all_courses", 0, courseList);
		return courseList;
	}

	@GetMapping(path = "/{id}")
	public Course findById(@PathVariable int id) {
		Course course = service.findById(id);
		return course;
	}

	@PostMapping
	public ResponseEntity<Course> save(@RequestBody @Valid Course course) {

		Course savedCourse = service.save(course);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedCourse.getId()).toUri();
		List<Course> courseList = (List<Course>) memcachedClient.get("all_courses");
		courseList.add(savedCourse);
		memcachedClient.set("all_courses", 0, courseList);
//        courseList.stream().forEach(c->System.out.print(c.getCourseDescription()));

		return ResponseEntity.created(location).build();
	}

	@PutMapping
	public Course update(@RequestBody @Valid Course course) {
		Course updatedCourse = service.update(course);

		return updatedCourse;
	}

	@DeleteMapping(path = "/{id}")
	public void delete(@PathVariable int id) {

		service.delete(id);
	}

	@GetMapping(path = "/{id}/users")
	public List<User> findUserList(@PathVariable int id) {
		return service.findUserList(id);
	}

	@PostMapping("/register")
	public UserCourse register(@RequestBody @Valid Course Course) {

		return service.register(Course);
	}

	@PutMapping("/cancel")
	public void cancel(@RequestParam(name = "coursId") int courseId) {
		service.cancel(courseId);
	}

	@GetMapping(path = "/{id}/download")
	public void getSteamingFile(@PathVariable int id, HttpServletResponse response) throws IOException {
		Course course = service.findById(id);

		String fullPath = path + course.getCourseSchedule() + ".pdf";
		File file = new File(fullPath);

		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attchment:filename=\"" + course.getCourseSchedule() + "\"");
		InputStream inputStream = new FileInputStream(file);
		org.apache.commons.io.IOUtils.copy(inputStream, response.getOutputStream());
		response.flushBuffer();

	}
}
