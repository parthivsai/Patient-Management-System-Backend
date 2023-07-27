package com.patientmanagement.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.patientmanagement.main.model.Patient;
import com.patientmanagement.main.model.User;
import com.patientmanagement.main.service.MyUserService;
import com.patientmanagement.main.service.PatientService;
import com.patientmanagement.main.service.VisitsService;

@RestController
@RequestMapping("/patient")
public class PatientController {
	
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private MyUserService userService;
	
	@Autowired
	private VisitsService visitsService;
	
	
	@GetMapping("/getAll")
	public List<Patient> getAllPatients(){
		List<Patient> patientList = patientService.getallPatients();
		return patientList;
	}
	
//	@GetMapping("/get/{email}/{password}")
//	public ResponseEntity<?> getByEmailAndPassword(@PathVariable("email") String email,
//			@PathVariable("password") String password) {
//		System.out.println("Email Entered: " + email+" Password Entered: " + password);
//		Patient patient = patientService.getByEmailAndPassword(email,password);
//		if(patient == null) {
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Email/Password. Try again with correct details!");
//		}
//		return ResponseEntity.status(HttpStatus.OK).body(patient);
//	}
	
	@GetMapping("/get/{id}")
	public ResponseEntity<?> getPatient(@PathVariable("id") int id) {
		Patient patient = patientService.getPatient(id);
		if(patient == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid ID given");
		}
		return ResponseEntity.status(HttpStatus.OK).body(patient);
	}
	
	@PostMapping("/add/{userId}")
	public Patient addPatient(@PathVariable("userId") int userId,@RequestBody Patient patient) {
		User user = userService.getById(userId);
	    patient.setUser(user);
	    patient.getUser().setRole("PATIENT");
		return patientService.insert(patient); 
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deletePatient(@PathVariable("id") int id){
		Patient patient = patientService.getPatient(id);
		if(patient == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Patient Id given!");
		}
		visitsService.deleteByPatientId(id);
		patientService.deletePatient(patient);
		return ResponseEntity.status(HttpStatus.OK).body("Patient deleted Successfully!");
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updatePatient(@RequestBody Patient newPatient,@PathVariable("id") int id) {
		Patient oldPatient = patientService.getPatient(id);
		if(oldPatient == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Patient Id given!");
		}
		newPatient.setUser(oldPatient.getUser());
		newPatient.setId(oldPatient.getId());
		newPatient = patientService.insert(newPatient);
		return ResponseEntity.status(HttpStatus.OK).body(newPatient);
	}
	
}
