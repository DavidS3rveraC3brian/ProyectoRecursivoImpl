package es.soincon.proyecto2.assemblers;

import org.springframework.beans.BeanUtils;

import es.soincon.proyecto2.dtos.AttributeTypeValueDto;
import es.soincon.proyecto2.entity.AttributeTypeValue;

public class AttributeTypeValueAssembler {
	private AttributeTypeValueAssembler() {
		throw new UnsupportedOperationException("No puede iniciarse el assembler de AttributeTypeValue");
	}
	
	public static AttributeTypeValueDto buildDtoFromEntity(AttributeTypeValue attributeTypeValue) {
		AttributeTypeValueDto attributeTypeValueDto = new AttributeTypeValueDto();
		BeanUtils.copyProperties(attributeTypeValue, attributeTypeValueDto);
		return attributeTypeValueDto;
	}
	
	public static AttributeTypeValue buildEntityFromDto(AttributeTypeValueDto attributeTypeValueDto) {
		AttributeTypeValue attributeTypeValue = new AttributeTypeValue();
		BeanUtils.copyProperties(attributeTypeValueDto, attributeTypeValue);
		return attributeTypeValue;
	}
}
