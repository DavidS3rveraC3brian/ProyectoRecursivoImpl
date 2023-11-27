package es.soincon.proyecto2.entity;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "attribute")
public class Attribute {
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

	@Column(name = "NAME")
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ATTRIBUTE_TYPE", nullable = false, foreignKey = @ForeignKey(name = "FK_ATTRIBUTE_ATTRIBUTE_TYPE_ID"))
	private AttributeType attributeType;
	
	@EqualsAndHashCode.Exclude // Excluye estos campos en las comparaciones de igualdad y hash.
    @ToString.Exclude // Excluye estos campos en el método toString.
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "attribute", orphanRemoval = false)
    private Set<Config> configs = new HashSet<Config>(); // Relación con la entidad Config
}
