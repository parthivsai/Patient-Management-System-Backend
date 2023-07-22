package com.patientmanagement.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.patientmanagement.main.model.Medicine;

public interface MedicineRepository extends JpaRepository<Medicine, Integer> {

}
