package es.soincon.proyecto2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import es.soincon.proyecto2.entity.Attribute;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface IAttributeRepository extends JpaRepository<Attribute, Long> {
	//Metodo par buscar attribute por su attribute_type
	List<Attribute> findByAttributeType(Long id);

}
