package com.medilabo.patientManager.repositories;

import com.medilabo.patientManager.model.Patient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends CrudRepository<Patient, Integer>
{
	public Patient findByFirstnameAndLastname(String firstname, String lastname);
}
