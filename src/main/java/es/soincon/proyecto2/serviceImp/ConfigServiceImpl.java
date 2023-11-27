package es.soincon.proyecto2.serviceImp;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import es.soincon.proyecto2.assemblers.ConfigAssembler;
import es.soincon.proyecto2.dtos.ConfigDto;
import es.soincon.proyecto2.repository.IConfigRepository;
import es.soincon.proyecto2.service.IConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConfigServiceImpl implements IConfigService {
	private final IConfigRepository configRespository;
	

	@Override
	public List<ConfigDto> getConfigs() {
		log.info("Datos de la implementacion del servicio ConfigServiceImpl: {}");
		return Optional.ofNullable(configRespository.findAll().stream().map(ConfigAssembler::buildDtoFromEntity)
				.collect(Collectors.toList())).orElse(Collections.emptyList());
	}

}
