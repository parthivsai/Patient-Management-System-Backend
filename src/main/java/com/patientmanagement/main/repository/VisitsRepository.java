package com.patientmanagement.main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.patientmanagement.main.model.Visits;

public interface VisitsRepository extends JpaRepository<Visits, Integer> {
	
	@Query("select v from Visits v where v.doctor.id=?1")
	List<Visits> getVisitByDoc(int id);

	@Query("select v from Visits v where v.patient.id=?1")
	List<Visits> getVisitByPat(int id);

	@Query("select v from Visits v where v.medicine.id=?1")
	List<Visits> getVisitByMed(int id);

}
