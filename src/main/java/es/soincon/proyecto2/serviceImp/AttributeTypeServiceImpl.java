package es.soincon.proyecto2.serviceImp;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import es.soincon.proyecto2.assemblers.AttributeTypeAssembler;
import es.soincon.proyecto2.dtos.AttributeTypeDto;
import es.soincon.proyecto2.repository.IAttributeTypeRepository;
import es.soincon.proyecto2.service.IAttributeTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AttributeTypeServiceImpl implements IAttributeTypeService {
	private final IAttributeTypeRepository attributeTypeRepository;

	@Override
	public List<AttributeTypeDto> getAttributeTypes() {
		log.info("Datos de la implementacion del servicio AttributeTypeImpl: {}");
		return Optional.ofNullable(attributeTypeRepository.findAll().stream()
				.map(AttributeTypeAssembler::buildDtoFromEntity).collect(Collectors.toList()))
				.orElse(Collections.emptyList());
	}

}
