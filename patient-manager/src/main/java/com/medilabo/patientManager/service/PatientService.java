package com.medilabo.patientManager.service;

import com.medilabo.patientManager.model.Patient;
import com.medilabo.patientManager.repositories.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PatientService
{
	PatientRepository patientRepository;

	public PatientService(PatientRepository patientRepository)
	{
		this.patientRepository = patientRepository;
	}

	public Iterable<Patient> getPatients()
	{
		return patientRepository.findAll();
	}
	public Optional<Patient> getPatientById(int id)
	{
		return patientRepository.findById(id);
	}

	public Patient getPatientByFullName(String firstname, String lastname)
	{
		return patientRepository.findByFirstnameAndLastname(firstname, lastname);
	}

	public void createPatient(Patient patient)
	{
		patient.setId(0);
		patientRepository.save(patient);
	}

	public void updatePatient(Patient patient)
	{
		patientRepository.save(patient);
	}

	public void deletePatient(Patient patient)
	{
		patientRepository.delete(patient);
	}
}
