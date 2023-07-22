package com.patientmanagement.main.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.patientmanagement.main.model.Medicine;
import com.patientmanagement.main.repository.MedicineRepository;

@Service
public class MedicineService {
	
	@Autowired
	private MedicineRepository medicineRepository;
	
	public List<Medicine> getallMedicines() {
		return medicineRepository.findAll();
	}

	public Medicine getMedicine(int id) {
		Optional<Medicine> optional = medicineRepository.findById(id);
		if(!optional.isPresent()) {
			return null;
		}
		return optional.get();
	}

	public Medicine insert(Medicine medicine) {
		return medicineRepository.save(medicine);
	}

	public void deleteMedicine(Medicine medicine) {
		medicineRepository.delete(medicine);
	}
	
}
