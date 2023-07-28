package com.patientmanagement.main.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.patientmanagement.main.model.PatientDoctor;
import com.patientmanagement.main.repository.PatientDoctorRepository;

@Service
public class PatientDoctorService {
	
	@Autowired
	private PatientDoctorRepository pdRepository;

	public List<PatientDoctor> getallPatients() {
		return pdRepository.findAll();
	}

	public PatientDoctor save(PatientDoctor pdRecord) {
		
		return pdRepository.save(pdRecord);
	}

	public List<PatientDoctor> getRecordsByDocId(int docId) {
		return pdRepository.findByDocId(docId);
	}
	
	public List<PatientDoctor> getRecordsByDocIdApproved(int docId) {
		String status = "APPROVED";
		return pdRepository.findByDocIdStatus(docId,status);
	}
	
	public List<PatientDoctor> getRecordsByDocIdPending(int docId) {
		String status = "PENDING";
		return pdRepository.findByDocIdStatus(docId,status);
	}
	
	public List<PatientDoctor> getRecordsByDocIdRejected(int docId) {
		String status = "REJECTED";
		return pdRepository.findByDocIdStatus(docId,status);
	}
	
	public List<PatientDoctor> getRecordsByPatId(int patId) {
		return pdRepository.findByPatId(patId);
	}
	
	public List<PatientDoctor> getRecordsByPatIdApproved(int patId) {
		String status = "APPROVED";
		return pdRepository.findByPatIdStatus(patId,status);
	}
	
	public List<PatientDoctor> getRecordsByPatIdPending(int patId) {
		String status = "PENDING";
		return pdRepository.findByPatIdStatus(patId,status);
	}
	
	public List<PatientDoctor> getRecordsByPatIdRejected(int patId) {
		String status = "REJECTED";
		return pdRepository.findByPatIdStatus(patId,status);
	}

	public PatientDoctor getById(int id) {
		Optional<PatientDoctor> optional = pdRepository.findById(id);
		if(optional.isEmpty()) {
			return null;
		}
		return optional.get();
	}
	
}
