package com.patientmanagement.main.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.patientmanagement.main.model.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Integer> {

	Optional<Doctor> findByEmail(String email);

	@Query("select d from Doctor d where d.user.username=?1")
	Doctor getByUserName(String userName);

}
