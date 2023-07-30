package com.patientmanagement.main.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.patientmanagement.main.model.Medicine;
import com.patientmanagement.main.service.MedicineService;
import com.patientmanagement.main.service.VisitsService;

@RestController
@RequestMapping("/medicine")
public class MedicineController {
	
	@Autowired
	private MedicineService medicineService;
	
	@Autowired
	private VisitsService visitsService;
	
	@GetMapping("/getAll")
	public List<Medicine> getAllMedicines(){
		List<Medicine> medicineList = medicineService.getallMedicines();
		return medicineList;
	}
	
	@GetMapping("/get/{id}")
	public ResponseEntity<?> getMedicine(@PathVariable("id") int id) {
		Medicine medicine = medicineService.getMedicine(id);
		if(medicine == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid ID given");
		}
		return ResponseEntity.status(HttpStatus.OK).body(medicine);
	}
	
	@PostMapping("/add")
	public Medicine addMedicine(@RequestBody Medicine medicine) {
		return medicineService.insert(medicine); 
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteMedicine(@PathVariable("id") int id){
		Medicine medicine = medicineService.getMedicine(id);
		if(medicine == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Medicine Id given!");
		}
		visitsService.deleteByMedicineId(id);
		medicineService.deleteMedicine(medicine);
		return ResponseEntity.status(HttpStatus.OK).body("Medicine deleted Successfully!");
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateMedicine(@RequestBody Medicine newMedicine,@PathVariable("id") int id) {
		Medicine oldMedicine = medicineService.getMedicine(id);
		if(oldMedicine == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Medicine Id given!");
		}
		newMedicine.setId(oldMedicine.getId());
		newMedicine = medicineService.insert(newMedicine);
		return ResponseEntity.status(HttpStatus.OK).body(newMedicine);
	}
	
}
