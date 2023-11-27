package es.soincon.proyecto2.entity;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
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
@Table(name = "config")
public class Config {

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

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "IS_CUSTOM")
	private Boolean isCustom;

	@Column(name = "DEFAULT_VALUE")
	private String defaultValue;

	@Column(name = "APPLICATION_NODE")
	private Long applicationNode;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ATTRIBUTE_ID", nullable = false, foreignKey = @ForeignKey(name = "FK_CONFIG_ATTRIBUTE_ID"))
	private Attribute attribute;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PARENT", nullable = true, foreignKey = @ForeignKey(name = "FK_CONFIG_CONFIG_PARENT"))
	private Config parent;

	@EqualsAndHashCode.Exclude // Excluye estos campos en las comparaciones de igualdad y hash.
	@ToString.Exclude // Excluye estos campos en el método toString.
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "parent", orphanRemoval = false, cascade = { CascadeType.ALL })
	private Set<Config> children = new HashSet<Config>(); // Relación con la entidad Config a la cual se auto_referencia

}
