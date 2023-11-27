package es.soincon.proyecto2.configValdationException;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import es.soincon.proyecto2.entity.AttributeTypeValue;
import es.soincon.proyecto2.entity.Config;
import es.soincon.proyecto2.repository.IAttributeTypeValueRepository;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler({ ConfigValidationException.class })
	public ResponseEntity<String> handleConfigValidationException(ConfigValidationException exception) {
		List<String> valoresAceptados = exception.getConfig().getAttribute().getAttributeType().getAttributeTypeValues().stream()
				.map(AttributeTypeValue::getValue).collect(Collectors.toList());

		String errorMessage = "Error! Los valores del typo no válidos, los tipos aceptados son: \n" + valoresAceptados
				+ "\nPara el config con ID: " + exception.getConfig().getId();

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);

	}
}
