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

import com.patientmanagement.main.model.Doctor;
import com.patientmanagement.main.service.DoctorService;
import com.patientmanagement.main.service.MyUserService;
import com.patientmanagement.main.service.VisitsService;

@RestController
@RequestMapping("/doctor")
public class DoctorController {
	
	@Autowired
	private DoctorService doctorService;
	
	@Autowired
	private MyUserService userService;
	
	@Autowired
	private VisitsService visitsService;
	
	@GetMapping("/getAll")
	public List<Doctor> getAllDoctors(){
		List<Doctor> doctors = doctorService.getallDoctors();
		return doctors;
	}
	
	@GetMapping("/get/{id}")
	public ResponseEntity<?> getDoctor(@PathVariable("id") int id) {
		Doctor doctor = doctorService.getDoctor(id);
		if(doctor == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid ID given");
		}
		return ResponseEntity.status(HttpStatus.OK).body(doctor);
	}
	
	@PostMapping("/add/{userId}")
	public Doctor addDoctor(@PathVariable("userId") int userId, @RequestBody Doctor doctor) {	
		doctor.setUser(userService.getById(userId));
		doctor.getUser().setRole("DOCTOR");
		return doctorService.insert(doctor); 
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteDoctor(@PathVariable("id") int id){
		Doctor doctor = doctorService.getDoctor(id);
		if(doctor == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Doctor Id given!");
		}
		visitsService.deleteByDocId(id);
		doctorService.deleteDoctor(doctor);
		return ResponseEntity.status(HttpStatus.OK).body("Doctor deleted Successfully!");
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateDoctor(@RequestBody Doctor newDoctor,@PathVariable("id") int id) {
		Doctor oldDoctor = doctorService.getDoctor(id);
		if(oldDoctor == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Doctor Id given!");
		}
		newDoctor.setId(oldDoctor.getId());
		newDoctor = doctorService.insert(newDoctor);
		return ResponseEntity.status(HttpStatus.OK).body(newDoctor);
	}
	
}
