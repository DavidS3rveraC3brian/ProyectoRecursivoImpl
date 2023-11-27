package es.soincon.proyecto2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import es.soincon.proyecto2.entity.AttributeType;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface IAttributeTypeRepository extends JpaRepository<AttributeType, Long> {
	
	//metodo para buscar Attribute_type por el attribute_type que contiene
//	List<Attribute_type> findByAttribute_type(Long attribute_type);
	
	//Añadido 16/08
	List<AttributeType> findAllById(Long id);

}
