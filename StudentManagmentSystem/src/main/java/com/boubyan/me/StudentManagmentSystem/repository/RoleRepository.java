package com.boubyan.me.StudentManagmentSystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boubyan.me.StudentManagmentSystem.entity.ERole;
import com.boubyan.me.StudentManagmentSystem.entity.Role;

public interface RoleRepository  extends JpaRepository<Role, Integer>{

	Optional<Role> findByRoleName(ERole roleName);
}
