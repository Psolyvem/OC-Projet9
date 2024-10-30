package com.medilabo.webclient.controller;

import com.medilabo.webclient.model.Note;
import com.medilabo.webclient.model.Patient;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.tinylog.Logger;

import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
public class WebInterfaceController
{
	private final PatientManagerClient patientManagerClient;
	private final NoteManagerClient noteManagerClient;

	public WebInterfaceController(PatientManagerClient patientManagerClient, NoteManagerClient noteManagerClient)
	{
		this.patientManagerClient = patientManagerClient;
		this.noteManagerClient = noteManagerClient;
	}

	@GetMapping("/")
	public String home(Model model)
	{
		model.addAttribute("patients", patientManagerClient.getPatients());
		return "home";
	}

	@GetMapping("/patient/{id}")
	public String viewPatient(@PathVariable("id") Integer id, Model model)
	{
		try
		{
			model.addAttribute("patient", patientManagerClient.getPatientById(id));
			model.addAttribute("notes", noteManagerClient.getNotesByPatientId(id));
		}
		catch (IllegalArgumentException e)
		{
			Logger.error("Invalid patient id : " + id + ", unable to update.");
			model.addAttribute("patients", patientManagerClient.getPatients());
			return "home";
		}
		catch (NoSuchElementException e)
		{
			Logger.error("Patient id not found");
			model.addAttribute("patients", patientManagerClient.getPatients());
			return "home";
		}

		return "patient";
	}

	@GetMapping("/patient/add")
	public String createPatient(Model model)
	{
		model.addAttribute("patient", new Patient());
		return "add";
	}

	@PostMapping("/patient/add")
	public String validateCreate(Patient patient, BindingResult result, Model model)
	{
		Logger.info("Infos are " + patient.getFirstname() + " " + patient.getLastname() + ", born on " + patient.getBirthdate() + ", " + patient.getGender() + ", " + patient.getAddress() + ", " + patient.getPhoneNumber());
		if (!result.hasErrors())
		{
			try
			{
				patientManagerClient.createPatient(patient);
				model.addAttribute("patients", patientManagerClient.getPatients());
				return "home";
			}
			catch (IllegalArgumentException e)
			{
				Logger.error("Patient data invalid");
			}

		}
		model.addAttribute("error", "true");
		return "add";
	}


	@GetMapping("/patient/update/{id}")
	public String updatePatient(@PathVariable("id") Integer id, Model model)
	{
		try
		{
			model.addAttribute("patient", patientManagerClient.getPatientById(id));
		}
		catch (IllegalArgumentException e)
		{
			Logger.error("Invalid patient id : " + id + ", unable to update.");
			model.addAttribute("patients", patientManagerClient.getPatients());
			return "home";
		}
		return "edit";
	}

	@PostMapping("/patient/update/{id}")
	public String validateUpdate(@PathVariable("id") Integer id, Patient patient, BindingResult result, Model model)
	{
		Logger.info("trying to update patient");
		Logger.info("Infos are " + patient.getFirstname() + " " + patient.getLastname() + ", born on " + patient.getBirthdate() + ", " + (patient.getGender().equals("M") ? "Male" : "Female") + ", " + patient.getAddress() + ", " + patient.getPhoneNumber());
		if (!result.hasErrors())
		{
			patient.setId(id);
			try
			{
				patientManagerClient.updatePatient(patient);
				model.addAttribute("patient", patientManagerClient.getPatientById(id));
				model.addAttribute("notes", noteManagerClient.getNotesByPatientId(id));
				return "patient";
			}
			catch (IllegalArgumentException e)
			{
				Logger.error("Patient data invalid");
			}
		}
		model.addAttribute("error", "true");
		return "edit";
	}

	@GetMapping("/patient/delete/{id}")
	public String deletePatient(@PathVariable("id") Integer id, Model model)
	{
		try
		{
			patientManagerClient.deletePatient(id);
		}
		catch (IllegalArgumentException | NoSuchElementException e)
		{
			Logger.error("Invalid patient id : " + id + ", unable to delete.");
		}
		model.addAttribute("patients", patientManagerClient.getPatients());
		return "home";
	}

	@GetMapping("/note/add/{id}")
	public String addNote(@PathVariable int id, Model model)
	{
		Note note = new Note();
		note.setPatientId(id);
		model.addAttribute("note", note);
		return "addnote";
	}

	@PostMapping("/note/add/{id}")
	public String validateCreateNote(@PathVariable int id, Note note, Model model)
	{
		Logger.info("Creating note for patient " + id + " : " + note.getContent());
		try
		{
			note.setPatientId(id);
			noteManagerClient.createNote(note);
			model.addAttribute("patient", patientManagerClient.getPatientById(note.getPatientId()));
			model.addAttribute("notes", noteManagerClient.getNotesByPatientId(note.getPatientId()));
			return "patient";
		}
		catch (IllegalArgumentException e)
		{
			Logger.error("Note is invalid");
		}
		model.addAttribute("error", "true");
		return "addnote";
	}

	@GetMapping("note/update/{id}")
	public String updateNote(@PathVariable String id, Model model)
	{
		try
		{
			model.addAttribute("note", noteManagerClient.getNoteById(id));
			return "updatenote";
		}
		catch (IllegalArgumentException e)
		{
			Logger.error("Invalid note id : " + id + ", unable to update.");
			model.addAttribute("patient", patientManagerClient.getPatients());
			return "home";
		}
	}

	@PostMapping("/note/update/{id}")
	public String validateUpdateNote(@PathVariable String id, Note note, Model model)
	{
		Logger.info("Updating note for patient " + id + " : " + note.getContent());
		note.setId(id);
		try
		{
			noteManagerClient.updateNote(id, note);
			model.addAttribute("patient", patientManagerClient.getPatientById(note.getPatientId()));
			model.addAttribute("notes", noteManagerClient.getNotesByPatientId(note.getPatientId()));
			return "patient";
		}
		catch (IllegalArgumentException e)
		{
			Logger.error("Note invalid");
		}
		model.addAttribute("error", "true");
		return "updatenote";
	}

	@GetMapping("/note/delete/{id}")
	public String deleteNote(@PathVariable String id, Model model)
	{
		try
		{
			Patient patient = patientManagerClient.getPatientById(noteManagerClient.getNoteById(id).getPatientId());
			noteManagerClient.deleteNote(id);
			model.addAttribute("patient", patient);
			model.addAttribute("notes", noteManagerClient.getNotesByPatientId(patient.getId()));
			return "patient";
		}
		catch (IllegalArgumentException | NoSuchElementException e)
		{
			Logger.error("Unable to delete note");
			model.addAttribute("patients", patientManagerClient.getPatients());
			return "home";
		}
	}
}
