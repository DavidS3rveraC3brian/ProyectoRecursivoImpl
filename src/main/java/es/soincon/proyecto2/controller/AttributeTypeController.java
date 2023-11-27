package es.soincon.proyecto2.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.soincon.proyecto2.dtos.AttributeTypeDto;
import es.soincon.proyecto2.service.IAttributeTypeService;

@RestController
@RequestMapping(path = "/attribute_type")
public class AttributeTypeController {

	private final IAttributeTypeService attributeTypeService;

	public AttributeTypeController(IAttributeTypeService attributeTypeService) {
		this.attributeTypeService = attributeTypeService;
	}

	@GetMapping
	public ResponseEntity<List<AttributeTypeDto>> getAllAttributesType() {
		// Retorna la lista de attribute_type en formato JSON
		return ResponseEntity.ok(attributeTypeService.getAttributeTypes());

	}
}
