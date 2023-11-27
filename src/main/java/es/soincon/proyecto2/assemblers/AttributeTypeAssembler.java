package es.soincon.proyecto2.assemblers;

import org.springframework.beans.BeanUtils;

import es.soincon.proyecto2.dtos.AttributeTypeDto;
import es.soincon.proyecto2.entity.AttributeType;

public class AttributeTypeAssembler {
	private AttributeTypeAssembler() {
		throw new UnsupportedOperationException("No puede iniciarse la clase assembler ConfigAssembler");
	}
	
	public static AttributeTypeDto buildDtoFromEntity(AttributeType attributeType) {
		AttributeTypeDto attributeTypeDto = new AttributeTypeDto();
		BeanUtils.copyProperties(attributeType, attributeTypeDto);
		return attributeTypeDto;
	}
	
	public static AttributeType buildEntityFromDto(AttributeTypeDto attributeTypeDto) {
		AttributeType attributeType = new AttributeType();
		BeanUtils.copyProperties(attributeTypeDto, attributeType);
		return attributeType;
	}
}
