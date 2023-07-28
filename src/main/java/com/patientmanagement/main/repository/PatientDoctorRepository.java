package com.patientmanagement.main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.patientmanagement.main.model.PatientDoctor;

public interface PatientDoctorRepository extends JpaRepository<PatientDoctor, Integer> {
	
	@Query("select pd from PatientDoctor pd where pd.doctor.id=?1")
	List<PatientDoctor> findByDocId(int docId);
	
	@Query("select pd from PatientDoctor pd where pd.patient.id=?1")
	List<PatientDoctor> findByPatId(int patId);
	
	@Query("select pd from PatientDoctor pd where pd.patient.id=?1 and pd.approvalStatus=?2")
	List<PatientDoctor> findByPatIdStatus(int patId, String status);
	
	@Query("select pd from PatientDoctor pd where pd.doctor.id=?1 and pd.approvalStatus=?2")
	List<PatientDoctor> findByDocIdStatus(int docId, String status);
	
}
