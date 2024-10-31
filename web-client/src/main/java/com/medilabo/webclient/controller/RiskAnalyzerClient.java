package com.medilabo.webclient.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "risk-analyzer", url = "localhost:9100/risk-analyzer")
public interface RiskAnalyzerClient
{
	@GetMapping("/risk/{id}")
	String getRisk(@PathVariable int id);
}
