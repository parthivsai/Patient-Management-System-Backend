package com.patientmanagement.main.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.patientmanagement.main.model.User;
import com.patientmanagement.main.service.MyUserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private MyUserService userService;
	
	@GetMapping("/getAll")
	public List<User> getAll(){
		List<User> users = userService.getAll();
		return users;
	}
	
	@GetMapping("/login") 
	public UserDetails login(Principal principal) {
		String username = principal.getName();
		
		UserDetails user = userService.loadUserByUsername(username);
		
		return user;
		
	}
	
	@PostMapping("/login")
	public List<Object> authenticateDoctor(@RequestBody User user) {
        List<Object> userDetails = userService.generateJwtToken(user.getUsername(), user.getPassword());
//        System.out.println(userDetails);
        return userDetails;
    }
	
	@PostMapping("/add")
	public User addUser(@RequestBody User user) {
		return userService.insert(user);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable("id") int id){
		User user = userService.getById(id);
		if(user == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid User Id given!");
		}
		userService.deleteUser(user);
		return ResponseEntity.status(HttpStatus.OK).body("User deleted Successfully!");
	}
	
}

