package com.boubyan.me.StudentManagmentSystem.service;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.boubyan.me.StudentManagmentSystem.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.jsonwebtoken.lang.Objects;
import org.springframework.security.core.userdetails.UserDetails;
public class UserDetailsImpl implements UserDetails {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String firstname;
	private String familyname;
	private String email;
	@JsonIgnore
	private String password;
	private List<GrantedAuthority> authorities;
	public UserDetailsImpl(int id, String firstname,String familyname, String email, String password,
			List<GrantedAuthority> authorities) {
		this.id = id;
		this.firstname = firstname;
		this.familyname=familyname;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
	}
	public static UserDetailsImpl build(User user) {
		List<GrantedAuthority> authorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getRoleName().toString()))
				.collect(Collectors.toList());
		return new UserDetailsImpl(
				user.getId(), 
				user.getFirstName(), 
				user.getFamilyName(),
				user.getEmail(),
				user.getPassword(), 
				authorities);
	}
	public List<GrantedAuthority> getAuthorities() {
		return authorities;
	}
	public int getId() {
		return id;
	}
	public String getEmail() {
		return email;
	}
	public String getPassword() {
		return password;
	}
	public String getFirstname() {
		return firstname;
	}
	public boolean isAccountNonExpired() {
		return true;
	}
	public boolean isAccountNonLocked() {
		return true;
	}
	public boolean isCredentialsNonExpired() {
		return true;
	}
	public boolean isEnabled() {
		return true;
	}
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserDetailsImpl user = (UserDetailsImpl) o;
		return Objects.nullSafeEquals(id, user.getId());
	}
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return null;
	}

}
