package es.soincon.proyecto2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import es.soincon.proyecto2.entity.AttributeTypeValue;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface IAttributeTypeValueRepository extends JpaRepository<AttributeTypeValue, Long>{

	List<AttributeTypeValue> findByAttributeTypeId(Long id);
	
}
