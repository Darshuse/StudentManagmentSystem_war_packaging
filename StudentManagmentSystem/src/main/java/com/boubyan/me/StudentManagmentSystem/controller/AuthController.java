package com.boubyan.me.StudentManagmentSystem.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boubyan.me.StudentManagmentSystem.config.security.jwt.JwtUtils;
import com.boubyan.me.StudentManagmentSystem.entity.ERole;
import com.boubyan.me.StudentManagmentSystem.entity.Role;
import com.boubyan.me.StudentManagmentSystem.entity.User;
import com.boubyan.me.StudentManagmentSystem.payload.request.LoginRequest;
import com.boubyan.me.StudentManagmentSystem.payload.request.SignupRequest;
import com.boubyan.me.StudentManagmentSystem.payload.response.JwtResponse;
import com.boubyan.me.StudentManagmentSystem.payload.response.MessageResponse;
import com.boubyan.me.StudentManagmentSystem.service.UserDetailsImpl;
import com.boubyan.me.StudentManagmentSystem.service.RoleServiceImpl;
import com.boubyan.me.StudentManagmentSystem.service.UserServiceImpl;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	UserServiceImpl userService;
	@Autowired
	RoleServiceImpl roleservice;
	@Autowired
	PasswordEncoder encoder;
	@Autowired
	@Qualifier("jwtUtil")
	JwtUtils jwtUtils;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());
		return ResponseEntity.ok(
				new JwtResponse(jwt, userDetails.getId(), userDetails.getFirstname(), userDetails.getEmail(), roles));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (userService.existsByFirstName(signUpRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
		}
		if (userService.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}
		// Create new user's account
		User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
				encoder.encode(signUpRequest.getPassword()));
		user.setAge(signUpRequest.getAge());
		user.setFamilyName(signUpRequest.getFamilyName());
		user.setUserCourses(null);
		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();
		if (strRoles == null) {
			Role userRole = roleservice.findByRoleName(ERole.ROLE_USER);
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleservice.findByRoleName(ERole.ROLE_ADMIN);
					roles.add(adminRole);
					break;
				case "mod":
					Role modRole = roleservice.findByRoleName(ERole.ROLE_MODERATOR);
					roles.add(modRole);
					break;
				default:
					Role userRole = roleservice.findByRoleName(ERole.ROLE_USER);

					roles.add(userRole);
				}
			});
		}
		user.setRoles(roles);
		userService.save(user);
		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
}
