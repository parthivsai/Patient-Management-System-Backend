package com.patientmanagement.main.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.patientmanagement.main.JwtTokenProvider;
import com.patientmanagement.main.model.Doctor;
import com.patientmanagement.main.repository.DoctorRepository;

@Service
public class DoctorService {
	
	@Autowired
	private DoctorRepository doctorRepository;
	
	@Autowired
	private JwtTokenProvider jwtProvider;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public Doctor insert(Doctor doctor) {
		String password = doctor.getPassword();
		password = bCryptPasswordEncoder.encode(password);
		doctor.setPassword(password);
		doctor.setRole("DOCTOR");
		return doctorRepository.save(doctor);
	}
	
	public List<Doctor> getallDoctors() {
		return doctorRepository.findAll();
	}
	
	public Doctor getDoctor(int id) {
		Optional<Doctor> optional = doctorRepository.findById(id);
		if(!optional.isPresent()) {
			return null;
		}
		return optional.get();
	}
	
	public void deleteDoctor(Doctor doctor) {
		doctorRepository.delete(doctor);
	}
	
	public String generateJwtToken(String email, String password) {
        Optional<Doctor> optional = doctorRepository.findByEmail(email);
        Doctor doctor = optional.get();
        if (doctor != null && bCryptPasswordEncoder.matches(password, doctor.getPassword())) {
            return jwtProvider.generateToken(email);
        }
        return null;
    }
	
}
