package com.patientmanagement.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.patientmanagement.main.model.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Integer> {

}
