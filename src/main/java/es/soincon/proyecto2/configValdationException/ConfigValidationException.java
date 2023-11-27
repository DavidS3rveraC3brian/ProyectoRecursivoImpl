package es.soincon.proyecto2.configValdationException;

import es.soincon.proyecto2.entity.Config;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public final class ConfigValidationException extends RuntimeException {

	private static final long serialVersionUID = 546730621631788602L;
	private final Config config;

}
