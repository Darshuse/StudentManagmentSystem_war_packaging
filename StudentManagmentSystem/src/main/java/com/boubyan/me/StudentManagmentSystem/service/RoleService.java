package com.boubyan.me.StudentManagmentSystem.service;


import java.util.List;


import com.boubyan.me.StudentManagmentSystem.entity.ERole;
import com.boubyan.me.StudentManagmentSystem.entity.Role;

public interface RoleService {
	
	public Role findByRoleName(ERole roleName);
	
	public List<Role> findAll();

	public Role findById(int id);

	public Role update(Role role);

	public Role save(Role role);

	public void delete(Integer id);

}
