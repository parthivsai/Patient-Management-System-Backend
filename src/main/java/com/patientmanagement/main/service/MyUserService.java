package com.patientmanagement.main.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.patientmanagement.main.JwtTokenProvider;
import com.patientmanagement.main.model.Doctor;
import com.patientmanagement.main.model.Patient;
import com.patientmanagement.main.model.User;
import com.patientmanagement.main.repository.UserRepository;

@Service
public class MyUserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JwtTokenProvider jwtProvider;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private DoctorService doctorService;
	
	@Autowired
	private PatientService patientService;
	

	public User insert(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}
	
	public List<Object> generateJwtToken(String userName, String password) {
        User user = userRepository.getUserByUsername(userName);
//        System.out.println(user);
       
        if (user != null && bCryptPasswordEncoder.matches(password, user.getPassword())) {
            String token =  jwtProvider.generateToken(userName);
            
            List<Object> userDetails = new ArrayList<>();
            userDetails.add(token);
            userDetails.add(user.getRole());
            
//            System.out.println("Token: " + token + " Role: " + user.getRole());
            
            if(user.getRole().equals("DOCTOR")) {
//            	System.out.println("Check for username " + userName);
            	Doctor doc = doctorService.getByUsername(userName);
            	userDetails.add(doc);
//            	System.out.println("in doc loop" + doc);
            	return userDetails;
            }else if(user.getRole().equals("PATIENT")) {
            	Patient patient = patientService.getByUsername(userName);
            	userDetails.add(patient);
//            	System.out.println(patient);
            	return userDetails;
            }
        }
        return null;
    }

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.getUserByUsername(username);
		return user;
	}

	public User getById(int userId) {
		Optional<User> optional= userRepository.findById(userId);
		if(!optional.isPresent()) {
			return null; 
		}
		return optional.get();
	}

	public void deleteUser(User user) {
		userRepository.delete(user);
	}
	
	public List<User> getAll(){
		return userRepository.findAll();
	}

}

