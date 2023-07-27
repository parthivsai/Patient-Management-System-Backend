package com.patientmanagement.main.service;

import java.util.ArrayList;
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
	
	public List<Visits> getVisitByMed(int id){
		return visitsRepository.getVisitByMed(id);
	}

	public void deleteByPatientId(int id) {
		
		List<Integer> idList = getVisitIds(id);
		for(Integer i: idList) {
			visitsRepository.deleteById(i);
		}
	}

	private List<Integer> getVisitIds(int id) {
		
		List<Visits> v= visitsRepository.getVisitByPat(id);
		List<Integer> l = new ArrayList();
		for(Visits i: v) {
			l.add(i.getId());
		}
		return l;
	}

	public void deleteByMedicineId(int id) {
		List<Integer> idList = getVisitByMedIds(id);
		for(Integer i: idList) {
			visitsRepository.deleteById(i);
		}
		
	}

	private List<Integer> getVisitByMedIds(int id) {
		List<Visits> v= visitsRepository.getVisitByMed(id);
		List<Integer> l = new ArrayList();
		for(Visits i: v) {
			l.add(i.getId());
		}
		return l;
	}

	public void deleteByDocId(int id) {
		List<Integer> idList = getVisitByDocIds(id);
		for(Integer i: idList) {
			visitsRepository.deleteById(i);
		}
		
	}

	private List<Integer> getVisitByDocIds(int id) {
		List<Visits> v= visitsRepository.getVisitByDoc(id);
		List<Integer> l = new ArrayList();
		for(Visits i: v) {
			l.add(i.getId());
		}
		return l;
	}
	
}
