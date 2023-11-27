package es.soincon.proyecto2.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.soincon.proyecto2.dtos.AttributeDto;
import es.soincon.proyecto2.service.IAttributeService;

@RestController
@RequestMapping(path = "/attribute")
public class AttributeController {
	
	private final IAttributeService attributeService;

	public AttributeController(IAttributeService attributeService) {
		this.attributeService = attributeService;
	}
	
	@GetMapping
	public ResponseEntity<List<AttributeDto>> getAllAttributes() {
		//Retorna la lista de todos los Attribute en formato JSON
		return ResponseEntity.ok(attributeService.getAttributes());
	}

}
