package com.medilabo.webclient.controller;

import com.medilabo.webclient.model.Patient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.tinylog.Logger;

import java.util.NoSuchElementException;

@Controller
public class WebInterfaceController
{
	private final PatientManagerClient patientManagerClient;

	public WebInterfaceController(PatientManagerClient patientManagerClient)
	{
		this.patientManagerClient = patientManagerClient;
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
				Logger.info("Patient data invalid");
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
				return "patient";
			}
			catch (IllegalArgumentException e)
			{
				Logger.info("Patient data invalid");
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
		catch (IllegalArgumentException e)
		{
			Logger.error("Invalid patient id : " + id + ", unable to delete.");
		}
		catch (NoSuchElementException e)
		{
			Logger.error("Invalid patient id : " + id + ", unable to delete.");
		}
		model.addAttribute("patients", patientManagerClient.getPatients());
		return "home";
	}
}
