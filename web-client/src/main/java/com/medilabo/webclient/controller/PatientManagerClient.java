package com.medilabo.webclient.controller;

import com.medilabo.webclient.model.Patient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(value = "patient-manager", url = "localhost:9100/patient-manager")
public interface PatientManagerClient
{
	@GetMapping("/patients")
	List<Patient> getPatients();
}
