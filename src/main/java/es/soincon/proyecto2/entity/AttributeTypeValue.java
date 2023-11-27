package es.soincon.proyecto2.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "attribute_type_value")
public class AttributeTypeValue {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "CREATED_AT")
	private LocalDate createdAt;

	@Column(name = "DELETED")
	private Boolean deleted;

	@Column(name = "MODIFIED_AT")
	private LocalDate modifiedAt;

	@Column(name = "VERSION_LOCK")
	private Integer versionLock;

	@Column(name = "ENUM_DESCRIPTION")
	private String enumDescription;

	@Column(name = "VALUE")
	private String value;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ATTRIBUTE_TYPE", nullable = false, foreignKey = @ForeignKey(name = "FK_ATTRIBUTETYPEVALUE_ATTRIBUTE_ID"))
	private AttributeType attributeType;
	
	
}
