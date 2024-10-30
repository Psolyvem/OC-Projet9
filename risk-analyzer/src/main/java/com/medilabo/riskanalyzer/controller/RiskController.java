package com.medilabo.riskanalyzer.controller;

import com.medilabo.riskanalyzer.service.RiskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.tinylog.Logger;

import java.util.NoSuchElementException;

@RestController
public class RiskController
{
	RiskService riskService;

	public RiskController(RiskService riskService)
	{
		this.riskService = riskService;
	}

	@GetMapping("/risk/{id}")
	public ResponseEntity<String> getRisk(@PathVariable int id)
	{
		try
		{
			String risk = riskService.calculateRisk(id);
			return ResponseEntity.status(HttpStatus.OK).body(risk);
		}
		catch (IllegalArgumentException e)
		{
			Logger.error("Unable to calculate risk : Bad Request");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		catch (NoSuchElementException e)
		{
			Logger.error("Unable to calculate risk : Not Found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		catch (Exception e)
		{
			Logger.error("Unable to calculate risk : Unknown error");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}
