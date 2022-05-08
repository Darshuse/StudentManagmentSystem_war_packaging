package com.boubyan.me.StudentManagmentSystem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.boubyan.me.StudentManagmentSystem.entity.Course;

import com.boubyan.me.StudentManagmentSystem.entity.User;
import com.boubyan.me.StudentManagmentSystem.entity.UserCourse;
import com.boubyan.me.StudentManagmentSystem.entity.UserCoursePK;
@Repository
@Transactional
public interface UserCourseRepository extends JpaRepository<UserCourse, UserCoursePK> {

	@Query("SELECT sc.course FROM UserCourse sc join User u on  sc.id.userId = u.id where u.id = ?1")
	public List<Course> findByUser(int userId);
	
	@Query("SELECT sc.user FROM UserCourse sc join Course c on sc.id.courseId = c.id where c.id = ?1")
	public List<User> findByCourse(int courseId);
	
	@Query(
			  value = "SELECT * FROM user_courses u WHERE u.course_id = ?1 and u.user_id =?2 ", 
			  nativeQuery = true)
	    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT)
	public Optional<UserCourse> findByCourseAndUser(int course,int user);
}
