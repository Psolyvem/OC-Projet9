package com.medilabo.webclient.controller;

import com.medilabo.webclient.model.Patient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "patient-manager", url = "localhost:9100/patient-manager")
public interface PatientManagerClient
{
	@GetMapping("/patients")
	List<Patient> getPatients();

	@GetMapping("/patient?id={id}")
	Patient getPatientById(@PathVariable("id") int id);

	@PostMapping("/patient")
	ResponseEntity<String> createPatient(@RequestBody Patient patient);

	@PutMapping("/patient")
	ResponseEntity<String> updatePatient(@RequestBody Patient patient);

	@DeleteMapping("/patient?id={id}")
	ResponseEntity<String> deletePatient(@PathVariable int id);
}
