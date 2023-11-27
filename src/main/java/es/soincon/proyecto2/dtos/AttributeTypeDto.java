package es.soincon.proyecto2.dtos;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AttributeTypeDto {

	private Long id;

	private LocalDate createdAt;

	private Boolean deleted;
	
	private LocalDate modifiedAt;

	private Integer versionLock;

	private String description;

	private Boolean isCustom;

	private String defaultValue;
	
	private Long attributeTypeId;
}
