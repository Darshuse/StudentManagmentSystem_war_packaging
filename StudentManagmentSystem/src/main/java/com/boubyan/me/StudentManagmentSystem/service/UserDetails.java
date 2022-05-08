package com.boubyan.me.StudentManagmentSystem.service;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import com.mysql.cj.x.protobuf.MysqlxCrud.Collection;

public interface UserDetails {

	public List<GrantedAuthority> getAuthorities();

	public String getPassword();

	public String getFirstname();

	public boolean isAccountNonExpired();

	public boolean isAccountNonLocked();

	public boolean isCredentialsNonExpired();

	public boolean isEnabled();

	public boolean equals(Object o);
}
