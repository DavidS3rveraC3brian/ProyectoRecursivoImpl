package es.soincon.proyecto2.serviceImp;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import es.soincon.proyecto2.assemblers.AttributeAssembler;
import es.soincon.proyecto2.dtos.AttributeDto;
import es.soincon.proyecto2.repository.IAttributeRepository;
import es.soincon.proyecto2.service.IAttributeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AttributeServiceImpl implements IAttributeService {
	private final IAttributeRepository attributeRepository;

	@Override
	public List<AttributeDto> getAttributes() {
		log.info("Datos de la implementacion del servicio AttributeServiceImpl: {}");
		return Optional.ofNullable(attributeRepository.findAll().stream().map(AttributeAssembler::buildDtoFromEntity)
				.collect(Collectors.toList())).orElse(Collections.emptyList());

	}

}
