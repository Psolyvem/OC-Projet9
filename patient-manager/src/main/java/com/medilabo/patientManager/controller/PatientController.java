package com.medilabo.patientManager.controller;

import com.medilabo.patientManager.model.Patient;
import com.medilabo.patientManager.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.tinylog.Logger;

import java.util.Optional;

@RestController
public class PatientController
{
	PatientService patientService;

	public PatientController(PatientService patientService)
	{
		this.patientService = patientService;
	}

	@GetMapping("/patients")
	public Iterable<Patient> getPatients()
	{
		Logger.info("Requested all patients");
		return patientService.getPatients();
	}

	@GetMapping(path = "/patient", params = "id")
	public ResponseEntity<Optional<Patient>> getPatientById(@RequestParam(name = "id") int id)
	{
		Optional<Patient> patient = patientService.getPatientById(id);

		if (patient.isEmpty())
		{
			Logger.info("Requested patient with id " + id + " : not found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		Logger.info("Requested patient " + patient.get().getFirstname() + " " + patient.get().getLastname());
		return ResponseEntity.status(HttpStatus.OK).body(patientService.getPatientById(id));
	}

	@GetMapping("/patient")
	public ResponseEntity<Patient> getPatientByFullname(@RequestParam String firstname, @RequestParam String lastname)
	{
		Patient patient = patientService.getPatientByFullName(firstname, lastname);

		if (patient == null)
		{
			Logger.info("Requested patient " + firstname + " " + lastname + " : not found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		Logger.info("Requested patient " + patient.getFirstname() + " " + patient.getLastname());
		return ResponseEntity.status(HttpStatus.OK).body(patientService.getPatientByFullName(firstname, lastname));
	}

	@PostMapping("/patient")
	public ResponseEntity<String> postPatient(@RequestBody @Valid Patient patient, BindingResult result)
	{
		if (!result.hasErrors())
		{
			if (patientService.getPatientByFullName(patient.getFirstname(), patient.getLastname()) != null)
			{
				Logger.info("Unable to create patient " + patient.getFirstname() + " " + patient.getLastname() + " : already exists with id " + patient.getId());
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unable to create patient data : already exists");
			}

			patientService.createPatient(patient);
			Logger.info("Created patient " + patient.getFirstname() + " " + patient.getLastname());
			return ResponseEntity.status(HttpStatus.CREATED).body("Patient data created");
		}
		else
		{
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@PutMapping("/patient")
	public ResponseEntity<String> updatePatient(@RequestBody @Valid Patient patient, BindingResult result)
	{
		if (!result.hasErrors())
		{
			if (patientService.getPatientById(patient.getId()).isEmpty())
			{
				Logger.info("Unable to update patient " + patient.getFirstname() + " " + patient.getLastname() + " : does not exist");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Unable to update patient data : not found");
			}
			if (patientService.getPatientByFullName(patient.getFirstname(), patient.getLastname()) != null && patient.getId() != patientService.getPatientByFullName(patient.getFirstname(), patient.getLastname()).getId())
			{
				Logger.info("Unable to update patient " + patient.getFirstname() + " " + patient.getLastname() + " : wrong id");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unable to update patient data : patient exist but not under this id");
			}

			patientService.updatePatient(patient);
			Logger.info("Updated patient " + patient.getFirstname() + " " + patient.getLastname());
			return ResponseEntity.status(HttpStatus.OK).body("Patient data updated");
		}
		else
		{
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

	}

	@DeleteMapping(path = "/patient", params = "id")
	public ResponseEntity<String> deletePatient(@RequestParam(name = "id") int id)
	{
		Optional<Patient> patient = patientService.getPatientById(id);
		if (patient.isEmpty())
		{
			Logger.info("Unable to delete patient : does not exist");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Unable to delete patient data : not found");
		}

		Logger.info("Deleted patient " + patient.get().getFirstname() + " " + patient.get().getLastname());
		patientService.deletePatient(patientService.getPatientById(id).get());
		return ResponseEntity.status(HttpStatus.OK).body("Patient data deleted");
	}
}
