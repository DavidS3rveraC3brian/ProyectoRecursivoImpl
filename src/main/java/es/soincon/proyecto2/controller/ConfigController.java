package es.soincon.proyecto2.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import es.soincon.proyecto2.service.IRecursividadService;

@RestController
@RequestMapping(path = "/config", produces = MediaType.APPLICATION_JSON_VALUE)
public class ConfigController {

	private final IRecursividadService recursividadService;

	public ConfigController(IRecursividadService recursividadService) {
		this.recursividadService = recursividadService;
		
	}
	
	@GetMapping
	public ResponseEntity<String> getAllConfigs() throws JsonProcessingException {
		return ResponseEntity.ok(recursividadService.obtenerConfigs());
	}
}
