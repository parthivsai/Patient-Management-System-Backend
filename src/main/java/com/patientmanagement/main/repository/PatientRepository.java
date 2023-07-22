package com.patientmanagement.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.patientmanagement.main.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, Integer> {

}
