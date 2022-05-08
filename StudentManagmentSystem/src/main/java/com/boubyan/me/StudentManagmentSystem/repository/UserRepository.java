package com.boubyan.me.StudentManagmentSystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.boubyan.me.StudentManagmentSystem.entity.User;

@Repository
@Transactional
public interface UserRepository  extends JpaRepository<User, Integer>{
	Optional<User> findByFirstName(String firstName);
	Optional<User> findByEmail(String email);
	Boolean existsByFirstName(String firstName);
	Boolean existsByEmail(String email);

}
