package com.medilabo.webclient.controller;

import com.medilabo.webclient.model.Patient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
			return "redirect:/web-client/";
		}
		catch (NoSuchElementException e)
		{
			Logger.error("Patient id not found");
			return "redirect:/web-client/";
		}

		return "patient";
	}

	@GetMapping("/patient/add")
	public String createPatient(Model model)
	{
		return "add";
	}

	@PostMapping("/patient/add")
	public String validateCreate(Patient patient, BindingResult result, Model model)
	{
		Logger.info("trying to create patient");
		Logger.info("Infos are " + patient.getFirstname() + " " + patient.getLastname() + ", born on " + patient.getBirthdate() + ", " + (patient.getGender().equals("M") ? "Male" : "Female") + ", " + patient.getAddress() + ", " + patient.getPhoneNumber());
		if (!result.hasErrors())
		{
			return "redirect:/patient";
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
			return "redirect:/patients";
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
}
