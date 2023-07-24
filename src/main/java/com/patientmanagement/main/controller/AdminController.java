package com.patientmanagement.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.patientmanagement.main.model.User;
import com.patientmanagement.main.service.MyUserService;



@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private BCryptPasswordEncoder encoder; 

	@Autowired
	private MyUserService userService;

	@PostMapping("/add")
	public User add(@RequestBody User user) {
		user.setPassword(encoder.encode(user.getPassword()));
		user.setRole("ADMIN");
		return userService.insert(user);
	}
	

	@DeleteMapping("/delete/{userId}")
	public ResponseEntity<?> deleteUser(@PathVariable("userId") int userId){
		
		User user = userService.getById(userId);
		if(user == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid userId!!");
		}
	
		userService.deleteUser(user);
		return ResponseEntity.status(HttpStatus.OK).body("Deleted Successfully!");
	}
}
