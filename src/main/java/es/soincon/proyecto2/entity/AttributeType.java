package es.soincon.proyecto2.entity;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "attribute_type")
public class AttributeType {

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

	@Column(name = "IS_ENUM")
	private Boolean isEnum;

	@Column(name = "IS_LIST")
	private Boolean isList;

	@Column(name = "TYPE")
	private String type;

	@EqualsAndHashCode.Exclude // Excluye estos campos en las comparaciones de igualdad y hash.
    @ToString.Exclude // Excluye estos campos en el método toString.
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "attributeType", orphanRemoval = false)
    private Set<Attribute> attributes = new HashSet<>(); // Relación con la entidad Attribute
	
	@EqualsAndHashCode.Exclude // Excluye estos campos en las comparaciones de igualdad y hash.
    @ToString.Exclude // Excluye estos campos en el método toString.
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "attributeType", orphanRemoval = false)
    private Set<AttributeTypeValue> attributeTypeValues = new HashSet<>(); // Relación con la entidad AttributeType
}
