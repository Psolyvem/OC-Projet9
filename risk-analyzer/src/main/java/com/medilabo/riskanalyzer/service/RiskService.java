package com.medilabo.riskanalyzer.service;

import com.medilabo.riskanalyzer.controller.NoteManagerClient;
import com.medilabo.riskanalyzer.controller.PatientManagerClient;
import com.medilabo.riskanalyzer.model.Note;
import com.medilabo.riskanalyzer.model.Patient;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Service
public class RiskService
{
	PatientManagerClient patientManagerClient;
	NoteManagerClient noteManagerClient;

	public RiskService(PatientManagerClient patientManagerClient, NoteManagerClient noteManagerClient)
	{
		this.patientManagerClient = patientManagerClient;
		this.noteManagerClient = noteManagerClient;
	}

	public String calculateRisk(int id)
	{
		Patient patient = patientManagerClient.getPatientById(id);
		List<Note> notes = noteManagerClient.getNotesByPatientId(id);
		int age = Period.between(patient.getBirthdate(), LocalDate.now()).getYears();

		int termCount = termCounter(notes);

		if ((patient.getGender().equals("M") && termCount >= 5 && age < 30) || (patient.getGender().equals("F") && age < 30 && termCount >= 7) || (age >= 30 && termCount >= 8))
		{
			return "Early Onset";
		}
		else if ((patient.getGender().equals("M") && age < 30 && termCount >= 3) || (patient.getGender().equals("F") && age < 30 && termCount >= 4) || (age >= 30 && termCount >= 6))
		{
			return "In Danger";
		}
		else if (age >= 30 && termCount >= 2)
		{
			return "Borderline";
		}
		else
		{
			return "None";
		}
	}

	private int termCounter(List<Note> notes)
	{
		int result = 0;

		List<String> terms = new ArrayList<>();
		terms.add("Hémoglobine A1C");
		terms.add("Microalbumine");
		terms.add("Taille");
		terms.add("Poids");
		terms.add("Fumeur");
		terms.add("Fumeuse");
		terms.add("Anormal");
		terms.add("Cholestérol");
		terms.add("Vertiges");
		terms.add("Rechute");
		terms.add("Réaction");
		terms.add("Anticorps");

		for (Note note : notes)
		{
			for (String term : terms)
			{
				if (note.getContent().toLowerCase().contains(term.toLowerCase()))
				{
					result++;
				}
			}
		}
		return result;
	}
}
