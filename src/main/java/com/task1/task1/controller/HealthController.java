package com.task1.task1.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

	@GetMapping("/")
	public Map<String, String> root() {
		return Map.of("status", "ok");
	}
}


