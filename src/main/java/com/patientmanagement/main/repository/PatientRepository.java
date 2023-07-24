package com.patientmanagement.main.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.patientmanagement.main.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, Integer> {
	
	Optional<Patient> findByEmail(String email);

	@Query("select p from Patient p where p.user.username=?1")
	Patient getByuserName(String userName);
}
