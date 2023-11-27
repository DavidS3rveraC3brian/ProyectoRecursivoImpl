package es.soincon.proyecto2.assemblers;

import org.springframework.beans.BeanUtils;

import es.soincon.proyecto2.dtos.ConfigDto;
import es.soincon.proyecto2.entity.Config;

public class ConfigAssembler {
	
	private ConfigAssembler() {
		throw new UnsupportedOperationException("No puede iniciarse la clase assembler ConfigAssembler");
		
	}
	
	public static ConfigDto buildDtoFromEntity(Config config) {
		ConfigDto configDto = new ConfigDto();
		BeanUtils.copyProperties(config, configDto);
		configDto.setAttributeDto(AttributeAssembler.buildDtoFromEntity(config.getAttribute()));
		return configDto;
	}
	
	public static Config buildEntityFromDto(ConfigDto configDto) {
		Config config = new Config();
		BeanUtils.copyProperties(configDto, config);
		return config;
	}
}
