package es.soincon.proyecto2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import es.soincon.proyecto2.entity.Config;

//@Repository
//@Transactional(propagation=Propagation.MANDATORY)
public interface IConfigRepository extends JpaRepository<Config, Long>{

	//Metodo para buscar el attribute por su attribute_id
	List<Config> findByAttributeId(Long attributeId);
	
	//Metodo para buscar clases hijas de Config
	List<Config> findByParent(Long parent);
	
//	@Query (value= "Select * from config where parent is null", nativeQuery = true)
	List<Config> findByParentIsNull();
	
}
