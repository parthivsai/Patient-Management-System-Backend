package com.patientmanagement.main.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.patientmanagement.main.model.Patient;
import com.patientmanagement.main.repository.PatientRepository;

@Service
public class PatientService {
	
	@Autowired
	private PatientRepository patientRepository;

	public List<Patient> getallPatients() {
		return patientRepository.findAll();
	}

	public Patient getPatient(int id) {
		Optional<Patient> optional = patientRepository.findById(id);
		if(!optional.isPresent()) {
			return null;
		}
		return optional.get();
	}

	public Patient insert(Patient patient) {
		return patientRepository.save(patient);
	}

	public void deletePatient(Patient patient) {
		patientRepository.delete(patient);
	}
	
}
