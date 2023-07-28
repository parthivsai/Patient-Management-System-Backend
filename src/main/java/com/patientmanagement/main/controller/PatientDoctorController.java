package com.patientmanagement.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.patientmanagement.main.model.Doctor;
import com.patientmanagement.main.model.Patient;
import com.patientmanagement.main.model.PatientDoctor;
import com.patientmanagement.main.service.DoctorService;
import com.patientmanagement.main.service.PatientDoctorService;
import com.patientmanagement.main.service.PatientService;

@RestController
@RequestMapping("/status")
public class PatientDoctorController {
	
	@Autowired
	private PatientDoctorService pdService;
	
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private DoctorService doctorService;
	
	@GetMapping("/getAll")
	public List<PatientDoctor> getAllPatients(){
		List<PatientDoctor> pdList = pdService.getallPatients();
		return pdList;
	}
	
	@GetMapping("/getByDoc/{docId}")
	public ResponseEntity<?> getRecordsByDocId(@PathVariable int docId){
		List<PatientDoctor> pdList = pdService.getRecordsByDocId(docId);
		if(pdList.size()<=0) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid DocID given");
		}
		return ResponseEntity.status(HttpStatus.OK).body(pdList);
	}
	@GetMapping("/getByPat/{patId}")
	public ResponseEntity<?> getRecordsByPatId(@PathVariable int patId){
		List<PatientDoctor> pdList = pdService.getRecordsByPatId(patId);
		if(pdList.size()<=0) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid PatID given");
		}
		return ResponseEntity.status(HttpStatus.OK).body(pdList);
	}
	
	@GetMapping("/getByPat/approved/{patId}")
	public ResponseEntity<?> getRecordsByPatIdApproved(@PathVariable int patId){
		List<PatientDoctor> pdList = pdService.getRecordsByPatIdApproved(patId);
		if(pdList.size()<=0) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid PatID given");
		}
		return ResponseEntity.status(HttpStatus.OK).body(pdList);
	}
	
	@GetMapping("/getByPat/pending/{patId}")
	public ResponseEntity<?> getRecordsByPatIdPending(@PathVariable int patId){
		List<PatientDoctor> pdList = pdService.getRecordsByPatIdPending(patId);
		if(pdList.size()<=0) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid PatID given");
		}
		return ResponseEntity.status(HttpStatus.OK).body(pdList);
	}
	
	@GetMapping("/getByPat/rejected/{patId}")
	public ResponseEntity<?> getRecordsByPatIdRejected(@PathVariable int patId){
		List<PatientDoctor> pdList = pdService.getRecordsByPatIdRejected(patId);
		if(pdList.size()<=0) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid PatID given");
		}
		return ResponseEntity.status(HttpStatus.OK).body(pdList);
	}
	
	@GetMapping("/getByDoc/approved/{docId}")
	public ResponseEntity<?> getRecordsByDocIdApproved(@PathVariable int docId){
		List<PatientDoctor> pdList = pdService.getRecordsByDocIdApproved(docId);
		if(pdList.size()<=0) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid DocID given");
		}
		return ResponseEntity.status(HttpStatus.OK).body(pdList);
	}
	
	@GetMapping("/getByDoc/pending/{docId}")
	public ResponseEntity<?> getRecordsByDocIdPending(@PathVariable int docId){
		List<PatientDoctor> pdList = pdService.getRecordsByDocIdPending(docId);
		if(pdList.size()<=0) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid DocID given");
		}
		return ResponseEntity.status(HttpStatus.OK).body(pdList);
	}
	
	@GetMapping("/getByDoc/rejected/{docId}")
	public ResponseEntity<?> getRecordsByDocIdRejected(@PathVariable int docId){
		List<PatientDoctor> pdList = pdService.getRecordsByDocIdRejected(docId);
		if(pdList.size()<=0) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid DocID given");
		}
		return ResponseEntity.status(HttpStatus.OK).body(pdList);
	}
	
	@PostMapping("/add/{patientId}/{doctorId}")
	public ResponseEntity<?> addRecord(@RequestBody PatientDoctor pdRecord, @PathVariable int patientId,
			@PathVariable int doctorId) {
		// Check for doctorId and 
		Patient patient = patientService.getPatient(patientId);
		if(patient == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Patient ID given");
		}
		Doctor doctor = doctorService.getDoctor(doctorId);
		if(doctor == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Doctor ID given");
		}
		
		pdRecord.setDoctor(doctor);
		pdRecord.setPatient(patient);
		pdRecord.setApprovalStatus("PENDING");
		pdRecord = pdService.save(pdRecord);
		return ResponseEntity.status(HttpStatus.OK).body(pdRecord);
	}
	
	@PutMapping("/updateStatus/{id}")
	public ResponseEntity<?> updateStatus(@RequestBody String status, @PathVariable int id){
		PatientDoctor pd = pdService.getById(id);
		if(pd == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid ID given");
		}
		pd.setApprovalStatus(status);
		pd = pdService.save(pd);
		return ResponseEntity.status(HttpStatus.OK).body(pd);
	}
}
