package com.boubyan.me.StudentManagmentSystem.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.boubyan.me.StudentManagmentSystem.entity.Course;
import com.boubyan.me.StudentManagmentSystem.entity.User;
import com.boubyan.me.StudentManagmentSystem.entity.UserCourse;
import com.boubyan.me.StudentManagmentSystem.exception.CourseNotFoundException;
import com.boubyan.me.StudentManagmentSystem.repository.CourseRepository;


@Service
public class CourseServiceImpl implements CoursService {
	@Autowired
	CourseRepository repo;
	@Autowired
	UserServiceImpl uservice;
	@Autowired
	UserCourseServiceImpl userCourseService;

	@Override
	public List<Course> findAll() {
		// TODO Auto-generated method stub
		List<Course> courses = repo.findAll();
		if (courses == null || courses.isEmpty())
			throw new CourseNotFoundException("no cours found");
		return courses;
	}

	@Override
	public Course findById(int id) {
		// TODO Auto-generated method stub
		Optional<Course> cours = repo.findById(id);

		return cours.orElseThrow(() -> new CourseNotFoundException("Error: Course is not found with id: " + id));
	}

	@Override
	public Course update(Course course) {
		// TODO Auto-generated method stub
		Optional<Course> updatedCourse = repo.findById(course.getId());
		if (updatedCourse.orElse(null) != null) {
			updatedCourse.get().setCourseDescription(course.getCourseDescription());
			updatedCourse.get().setCourseSchedule(course.getCourseSchedule());
			updatedCourse.get().setId(course.getId());

			save(updatedCourse.get());
		}
		return updatedCourse
				.orElseThrow(() -> new CourseNotFoundException("Error: Course is not found to be updated."));
	}

	@Override
	public Course save(Course course) {
		// TODO Auto-generated method stub
		return repo.save(course);
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		Course deletedCourse = findById(id);

		repo.delete(deletedCourse);
	}

	@Override
	public UserCourse register(Course course) {
		// TODO Auto-generated method stub
		if (Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
			return null;
		}
		UserDetailsImpl currentUser = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		String userName = currentUser.getFirstname();
		UserCourse userCourse = new UserCourse();
		User user = uservice.findByUserName(userName);
		Course registeredCourse=findById(course.getId());
		userCourse.setUser(user);
		userCourse.setCourse(registeredCourse);
		return userCourseService.register(userCourse);
	}

	@Override
	public List<User> findUserList(int courseId) {
		// TODO Auto-generated method stub

		return userCourseService.findByCourse(courseId);
	}

	@Override
	public void cancel(int courseId) {
		// TODO Auto-generated method stub
		if (Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
			// handle Authenticated user
		}
		UserDetailsImpl currentUser = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		String userName = currentUser.getFirstname();
		User user = uservice.findByUserName(userName);
		Course course = repo.findById(courseId)
				.orElseThrow(() -> new CourseNotFoundException("Error: Course is not found with id: "+courseId));
		userCourseService.cancel(course, user);
	}

}
