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
import com.patientmanagement.main.model.Medicine;
import com.patientmanagement.main.model.Patient;
import com.patientmanagement.main.model.Visits;
import com.patientmanagement.main.service.DoctorService;
import com.patientmanagement.main.service.MedicineService;
import com.patientmanagement.main.service.PatientService;
import com.patientmanagement.main.service.VisitsService;

@RestController
@RequestMapping("/visits")
public class VisitsController {
	
	@Autowired
	private VisitsService visitsService;
	
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private DoctorService doctorService;
	
	@Autowired
	private MedicineService medicineService;
	
	@GetMapping("/getAll")
	public List<Visits> getAllVisits(){
		List<Visits> visits = visitsService.getallVisits();
		return visits;
	}
	
	@GetMapping("/getByDoc/{id}")
	public ResponseEntity<?> getVistByDoc(@PathVariable("id") int id) {
		List<Visits> visits = visitsService.getVisitByDoc(id);
		if(visits == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid ID given");
		}
		return ResponseEntity.status(HttpStatus.OK).body(visits);
	}
	
	@GetMapping("/getByPat/{id}")
	public ResponseEntity<?> getVistByPat(@PathVariable("id") int id) {
		List<Visits> visits = visitsService.getVisitByPat(id);
		if(visits.size() == 0) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid ID given");
		}
		return ResponseEntity.status(HttpStatus.OK).body(visits);
	}
	
	@GetMapping("/get/{id}")
	public ResponseEntity<?> getVisit(@PathVariable("id") int id) {
		Visits visits = visitsService.getVisits(id);
		if(visits == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid ID given");
		}
		return ResponseEntity.status(HttpStatus.OK).body(visits);
	}
	
	@PostMapping("/add/{patientId}/{doctorId}/{medicineId}")
	public ResponseEntity<?> addVisit(@PathVariable("patientId") int patientId,
			@PathVariable("doctorId") int doctorId,
			@PathVariable("medicineId") int medicineId,
			@RequestBody Visits visit) {
		
		// Checking if the given Id's are valid
		Patient patient = patientService.getPatient(patientId);
		if(patient == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid patient ID given");
		}
		Doctor doctor = doctorService.getDoctor(doctorId);
		if(doctor == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid doctor ID given");
		}
		Medicine medicine = medicineService.getMedicine(medicineId);
		if(medicine == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid medicine ID given");
		}
		
		visit.setPatient(patient);
		visit.setDoctor(doctor);
		visit.setMedicine(medicine);
		
		visit = visitsService.insert(visit);
		return ResponseEntity.status(HttpStatus.OK).body(visit);
	}
	
	@PutMapping("/update/{visitId}/{patientId}/{doctorId}/{medicineId}")
	public ResponseEntity<?> updateVisit(@PathVariable("patientId") int patientId,
			@PathVariable("doctorId") int doctorId,
			@PathVariable("medicineId") int medicineId,
			@PathVariable("visitId") int visitId,
			@RequestBody Visits newVisit) {
		
		// Checking if the given Id's are valid
		Visits oldVisit = visitsService.getVisits(visitId);
		if(oldVisit == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid visit ID given");
		}
		Patient patient = patientService.getPatient(patientId);
		if(patient == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid patient ID given");
		}
		Doctor doctor = doctorService.getDoctor(doctorId);
		if(doctor == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid doctor ID given");
		}
		Medicine medicine = medicineService.getMedicine(medicineId);
		if(medicine == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid medicine ID given");
		}
		
		newVisit.setId(oldVisit.getId());
		newVisit.setPatient(patient);
		newVisit.setDoctor(doctor);
		newVisit.setMedicine(medicine);
		
		newVisit = visitsService.insert(newVisit);
		return ResponseEntity.status(HttpStatus.OK).body(newVisit);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteVisit(@PathVariable("id") int id){
		Visits visit = visitsService.getVisits(id);
		if(visit == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Visit Id given!");
		}
	
		visitsService.deleteVisit(visit);
		return ResponseEntity.status(HttpStatus.OK).body("Visit deleted Successfully!");
	}
	
}
