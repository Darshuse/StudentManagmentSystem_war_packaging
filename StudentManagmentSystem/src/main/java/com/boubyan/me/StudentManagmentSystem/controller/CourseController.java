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
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.boubyan.me.StudentManagmentSystem.entity.Course;
import com.boubyan.me.StudentManagmentSystem.entity.User;
import com.boubyan.me.StudentManagmentSystem.entity.UserCourse;
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
		List<Course> courseList = service.findAll();
		memcachedClient.set("all_courses", 0, courseList.get(0));
		return courseList;
	}

	@GetMapping(path = "/{id}")
	public Course findById(@PathVariable int id) {
		return service.findById(id);
	}

	@PostMapping
	public ResponseEntity<Course> save(@RequestBody @Valid Course course) {

		Course savedCourse = service.save(course);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedCourse.getId()).toUri();
		Course courseList = (Course) memcachedClient.get("all_courses");
//        courseList.stream().forEach(c->System.out.print(c.getCourseDescription()));
		System.out.print(courseList.getCourseDescription());
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

	@GetMapping(path = "/{id}/students")
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

//	@GetMapping(path = "/{id}/download")
//	public ResponseEntity<InputStreamResource> download(@PathVariable int id) throws IOException {
//	
//	    // ... F:\course-schedule\Math.pdf
//		System.out.print(path);
//        Course course=findById(id);
//        
//        String fullPath=path+course.getCourseSchedule()+".pdf";
//        File file = new File(fullPath);
//
//	    InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
//        
//	    return ResponseEntity.ok()
//	            .header(HttpHeaders.CONTENT_DISPOSITION, "attchment:filename=\""+course.getCourseSchedule()+"\"")
//	            .contentType(MediaType.APPLICATION_PDF).contentLength(file.length())
//	            .body(resource);
//	}
	@GetMapping(path = "/{id}/download")
	public StreamingResponseBody getSteamingFile(@PathVariable int id, HttpServletResponse response)
			throws IOException {
		Course course = findById(id);

		String fullPath = path + course.getCourseSchedule() + ".pdf";
		File file = new File(fullPath);

		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attchment:filename=\"" + course.getCourseSchedule() + "\"");
		InputStream inputStream = new FileInputStream(file);
		return outputStream -> {
			int nRead;
			byte[] data = new byte[1024];
			while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
				System.out.println("Writing some bytes..");
				outputStream.write(data, 0, nRead);
			}
		};
	}
}
