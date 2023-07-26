package com.patientmanagement.main.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.patientmanagement.main.model.Visits;
import com.patientmanagement.main.repository.VisitsRepository;

@Service
public class VisitsService {
	
	@Autowired
	private VisitsRepository visitsRepository;

	public List<Visits> getallVisits() {
		return visitsRepository.findAll();
	}

	public Visits getVisits(int id) {
		Optional<Visits> optional = visitsRepository.findById(id);
		if(!optional.isPresent()) {
			return null;
		}
		return optional.get();
	}

	public Visits insert(Visits visit) {
		return visitsRepository.save(visit);
	}

	public void deleteVisit(Visits visit) {
		visitsRepository.delete(visit);
		
	}

	public List<Visits> getVisitByDoc(int id) {
		return visitsRepository.getVisitByDoc(id);
	}

	public List<Visits> getVisitByPat(int id) {
		return visitsRepository.getVisitByPat(id);
	}
	
}
