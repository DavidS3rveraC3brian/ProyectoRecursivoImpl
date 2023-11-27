package es.soincon.proyecto2.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.soincon.proyecto2.dtos.AttributeTypeValueDto;
import es.soincon.proyecto2.service.IAttributeTypeValueService;

@RestController
@RequestMapping(path = "/attribute_type_value")
public class AttributeTypeValueController {

	private final IAttributeTypeValueService attributeTypeValueService;
	
	public AttributeTypeValueController(IAttributeTypeValueService attributeTypeValueService) {
		this.attributeTypeValueService = attributeTypeValueService;
		
	}
	
	@GetMapping
	public ResponseEntity<List<AttributeTypeValueDto>> getAllAttributesTypeValues(){
		return ResponseEntity.ok(attributeTypeValueService.getAttributeTypeValuesDto());
	}
}
