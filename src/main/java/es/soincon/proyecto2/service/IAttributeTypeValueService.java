package es.soincon.proyecto2.service;

import java.util.List;

import es.soincon.proyecto2.dtos.AttributeTypeValueDto;
import es.soincon.proyecto2.entity.AttributeTypeValue;

public interface IAttributeTypeValueService {


	List<AttributeTypeValueDto> getAttributeTypeValuesDto();
	List<AttributeTypeValue> getAttributeTypeValues();
}
