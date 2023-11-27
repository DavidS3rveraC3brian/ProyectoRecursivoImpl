package es.soincon.proyecto2.serviceImp;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import es.soincon.proyecto2.assemblers.AttributeTypeValueAssembler;
import es.soincon.proyecto2.dtos.AttributeTypeValueDto;
import es.soincon.proyecto2.entity.AttributeTypeValue;
import es.soincon.proyecto2.repository.IAttributeTypeValueRepository;
import es.soincon.proyecto2.service.IAttributeTypeValueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AttributeTypeValueServiceImpl implements IAttributeTypeValueService {
	
	private final IAttributeTypeValueRepository attributeTypeValueRepository;

	@Override
	public List<AttributeTypeValueDto> getAttributeTypeValuesDto() {
		log.info("Datos de la implementacion del servicio AttributeTypeValueImpl: {}");
		return Optional
				.ofNullable(attributeTypeValueRepository.findAll().stream()
				.map(AttributeTypeValueAssembler::buildDtoFromEntity).collect(Collectors.toList()))
				.orElse(Collections.emptyList());
	}

	@Override
	public List<AttributeTypeValue> getAttributeTypeValues() {
		
		return Optional
				.ofNullable(attributeTypeValueRepository.findAll().stream().collect(Collectors.toList()))
				.orElse(Collections.emptyList());
	}

	

}
