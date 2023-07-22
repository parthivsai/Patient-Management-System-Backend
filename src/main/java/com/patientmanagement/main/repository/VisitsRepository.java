package com.patientmanagement.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.patientmanagement.main.model.Visits;

public interface VisitsRepository extends JpaRepository<Visits, Integer> {

}
