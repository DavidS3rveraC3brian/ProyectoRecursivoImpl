package es.soincon.proyecto2.assemblers;

import org.springframework.beans.BeanUtils;

import es.soincon.proyecto2.dtos.AttributeDto;
import es.soincon.proyecto2.entity.Attribute;

public class AttributeAssembler {
	
	private AttributeAssembler() {
		throw new UnsupportedOperationException("No puede iniciarse la clase assembler AttributeAssembler");
	}
	
	public static AttributeDto buildDtoFromEntity(Attribute attribute) {
		AttributeDto attributeDto = new AttributeDto();
		BeanUtils.copyProperties(attribute, attributeDto);
		attributeDto.setAttributeTypeDto(AttributeTypeAssembler.buildDtoFromEntity(attribute.getAttributeType()));
		return attributeDto;
	}
	
	public static Attribute buildEntityFromDto(AttributeDto attributeDto) {
		Attribute attribute = new Attribute();
		BeanUtils.copyProperties(attributeDto, attribute);
		return attribute;
	}
}
