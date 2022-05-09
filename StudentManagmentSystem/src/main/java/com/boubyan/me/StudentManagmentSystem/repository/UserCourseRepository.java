package com.boubyan.me.StudentManagmentSystem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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
public interface UserCourseRepository extends JpaRepository<UserCourse, Integer> {

	@Query("SELECT sc.course FROM UserCourse sc  where sc.user.id = ?1")
	public List<Course> findByUser(int userId);
	
	@Query("SELECT sc.user FROM UserCourse sc  where sc.course.id = ?1")
	public List<User> findByCourse(int courseId);
	
	@Query("SELECT sc FROM UserCourse sc   where  sc.user.id = ?1 and sc.course.id = ?2 ")
	public Optional<UserCourse> findByUserAndCourse(int userId,int courseId);
	@Modifying
	@Query(value = "DELETE  FROM UserCourse uc   where  uc.user.id = ?1 and uc.course.id = ?2 ")
	public void deleteByUserAndCourse(int userId,int courseId);
}
