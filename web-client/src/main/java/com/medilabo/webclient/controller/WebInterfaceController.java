package com.medilabo.webclient.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
}
