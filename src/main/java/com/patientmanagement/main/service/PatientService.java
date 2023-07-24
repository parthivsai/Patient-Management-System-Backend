package com.patientmanagement.main.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.patientmanagement.main.JwtTokenProvider;
import com.patientmanagement.main.model.Patient;
import com.patientmanagement.main.repository.PatientRepository;

@Service
public class PatientService {
	
	@Autowired
	private PatientRepository patientRepository;
	
	@Autowired
	private JwtTokenProvider jwtProvider;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

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
		String password = patient.getPassword();
		password = bCryptPasswordEncoder.encode(password);
		patient.setPassword(password);
		patient.setRole("PATIENT");
		return patientRepository.save(patient);
	}

	public void deletePatient(Patient patient) {
		patientRepository.delete(patient);
	}

//	public Patient getByEmailAndPassword(String email, String password) {
//		Optional<Patient> patient = patientRepository.findByEmail(email);
//		System.out.println(patient.get());
//		if(patient.isPresent()) {
//			if(patient.get().getPassword() == password) {
//				return patient.get();
//			}
//		}
//		return null;
//	}
	
	public String generateJwtToken(String email, String password) {
        Optional<Patient> optional = patientRepository.findByEmail(email);
        Patient patient = optional.get();
        if (patient != null && bCryptPasswordEncoder.matches(password, patient.getPassword())) {
            return jwtProvider.generateToken(email);
        }
        return null;
    }
	
}
