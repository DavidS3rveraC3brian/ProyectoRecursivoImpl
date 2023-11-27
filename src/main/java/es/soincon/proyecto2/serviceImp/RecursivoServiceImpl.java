/*
package es.soincon.proyecto2.serviceImp;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import es.soincon.proyecto2.entity.AttributeTypeValue;
import es.soincon.proyecto2.entity.Config;
import es.soincon.proyecto2.repository.IConfigRepository;
import es.soincon.proyecto2.service.IRecursividadService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RecursivoServiceImpl implements IRecursividadService {

	private final IConfigRepository configRepository;
	private final ObjectMapper objectMapper;
	private ObjectNode raiz = null;

	public RecursivoServiceImpl(IConfigRepository configRepository, ObjectMapper objectMapper) {
		super();
		this.configRepository = configRepository;
		this.objectMapper = objectMapper;
	}

	@Override
	public String getRecursiveConfigs() throws JsonProcessingException {

		// // Creas la raiz
		raiz = objectMapper.createObjectNode();

		// obtenemos padre Maestro
		List<Config> entities = configRepository.findByParentIsNull();
		log.info("Obtenemos los padres maestros: "
				+ entities.stream().map(c -> c.getAttribute().getName()).collect(Collectors.toList()));

		// Recorres los padres
		for (Config p : entities) {
			log.info("att raizp: " + p.getAttribute().getName());
			// si tiene hijos, realiza esto
			if (!p.getChildren().isEmpty()) {

				raiz.set(p.getAttribute().getName(), rellenarJson(p, objectMapper.createObjectNode()));

				// si no tiene hijos, realiza esto otro.
			} else {

				raiz.put(p.getAttribute().getName(), p.getDefaultValue());
			}
		}

		return objectMapper.writeValueAsString(raiz);
	}

	// crear un metodo "rellenarDTO" que meta hijos mientras me rellena el JSON
	private ObjectNode rellenarJson(Config entity, ObjectNode node) {
		log.info(entity.getAttribute().getName());

		if (entity.getAttribute().getAttributeType().getType().equals("NODE")) {

			for (Config e : entity.getChildren()) {

				// pregunto al hijo de mi hijo si tiene mas hijos
				// Anadido 31/08 // pregunto ademas si el Nodo de Config es Lista
				if (e.getAttribute().getAttributeType().getType().equals("NODE")
						&& !(e.getAttribute().getAttributeType().getIsList())) {

					// si tiene hijos, creo un nodo para que te añadas
					node.set(e.getAttribute().getName(), rellenarJson(e, objectMapper.createObjectNode()));

				} else if (e.getAttribute().getAttributeType().getType().equals("NODE")
						&& e.getAttribute().getAttributeType().getIsList()) {

					validarNodeList(e, node);

				} else {
					// si no tienes hijo, eres hoja, devuelveme el valor por defecto
					node.putPOJO(e.getAttribute().getName(), validacionTipoDato(e));
				}
			}
		}
		return node;
	}

	// #################################################################
	// # 															   #
	// # 					Metodos validadores 					   #
	// # 															   #
	// #################################################################

	private Object validacionTipoDato(Config config) {
		Object obj = null;

		switch (config.getAttribute().getAttributeType().getType().toUpperCase()) {

		case "STRING":
			obj = validarString(config);

			break;

		case "NUMERIC":
			obj = validarNumerico(config);

			break;

		case "BOOLEAN":
			obj = validarBoolean(config);

			break;

		case "IS_ENUM":
			obj = validarIsEnum(config);

		default:
			obj = config.getDefaultValue();
		}
		return obj;
	}

	// #################################################################
	// # 															   #
	// # 					Metodos Auxiliares 						   #
	// #															   #
	// #################################################################

	// #################### Tratamiento Typo Listas ####################

	// ######################### String ################################
	
	private Object listaStringsMethod(Config config) {
		String esLista = config.getDefaultValue();
		log.info("lista de DefaultValues de config : " + config.getDefaultValue());
		String[] arrayDefaultValues = StringUtils.tokenizeToStringArray(esLista, ";");
		log.info("Array de defaultValues sin el separador ';' : " + arrayDefaultValues);

		return arrayDefaultValues;
	}

	// ######################### Numeric ##############################
	
	private Object listaNumericMethod(Config config) {
		String defaultValue = config.getDefaultValue();
		String[] arrayDefaultValues = StringUtils.tokenizeToStringArray(defaultValue, ";");

		List<Long> listaDeLongs = new ArrayList<>(arrayDefaultValues.length);

		for (String o : arrayDefaultValues) {

			listaDeLongs.add(Long.parseLong(o));
		}

		return listaDeLongs;
	}

	// ######################### Boolean ##############################
	
	private Object listaBooleansMethod(Config config) {
		String defaultValue = config.getDefaultValue();
		String[] arrayDefaultValues = StringUtils.tokenizeToStringArray(defaultValue, ";");

		List<Boolean> listaDeBooleans = new ArrayList<>(arrayDefaultValues.length);

		for (String o : arrayDefaultValues) {
			listaDeBooleans.add(Boolean.parseBoolean(o));
		}

		return listaDeBooleans;
	}

	// ######################### Metodo asignar CLAVE - VALOR ##############################

	private Object asignarClaveValor(Config config, ObjectNode node, List<Long> defaultValuesId,
			List<String> defaultValuesName, ObjectMapper objectMapper, List<ObjectNode> objectList) {
		for (int i = 0; i < defaultValuesId.size(); i++) {// necesito recorrer las id y los names de las
															// variables Lista
			// almacenar la clave y valor en el map
			Long clave = defaultValuesId.get(i);
			String valor = defaultValuesName.get(i);

			// Crear un nuevo objeto JSON en cada iteración

			ObjectNode objectNode = objectMapper.createObjectNode();
			objectNode.put("id", clave);
			objectNode.put("name", valor);

			// Agregar el objeto JSON a la lista
			objectList.add(objectNode);

		}
		// Devuelve el objeto con clave - valor
		return node.putPOJO(config.getAttribute().getName(), objectList);
	}

	// ###################### Validar String ###########################

	private Object validarString(Config config) {
		// El NODO PADRE ES LISTA.

		// ES ENUM
		if (config.getAttribute().getAttributeType().getIsEnum()) {

			log.info("Es un enum, entramos a su logica: ");

			return validarIsEnum(config);

		} else {

			return listaStringsMethod(config);
		}

	}

	// ###################### Validar Enum ###########################

	private Object validarIsEnum(Config config) {

		Set<AttributeTypeValue> listaAttributeTypeValues = config.getAttribute().getAttributeType()
				.getAttributeTypeValues();

		log.info("Lista de los tipos de valores contenidos en attributeTypeValues : " + listaAttributeTypeValues);

		List<String> configAcceptedValues = new ArrayList<>();

		for (AttributeTypeValue attributesTypeValues : listaAttributeTypeValues) {

			log.info("en cada sucession del bucle, evaluo un valor de atributeTypeValues distinto : "
					+ attributesTypeValues);
			String value = attributesTypeValues.getValue();

			log.info("Almaceno estos valores en : " + value);

			configAcceptedValues.add(value);

			log.info("Los valores validos almacenados son : " + configAcceptedValues);

			return configAcceptedValues;
		}

		if (configAcceptedValues.contains(config.getDefaultValue())) {

			log.info("Todos los valores contenidos validos : " + configAcceptedValues);

			return configAcceptedValues;

		} else {

			return "No contiene los valores validos";
		}
	}

	// ###################### Validar Numerico ###########################

	public Object validarNumerico(Config config) {
		if (config.getAttribute().getAttributeType().getIsList()) {

			return listaNumericMethod(config);

		} else {

			return Long.parseLong(config.getDefaultValue());
		}
	}

	private Object validarBoolean(Config config) {

		Object obj;

		if (config.getAttribute().getAttributeType().getIsList()) {

			String defaultValue = config.getDefaultValue();
			String[] listaDeDefaultValue = StringUtils.tokenizeToStringArray(defaultValue, ";");

			List<Boolean> booleans = new ArrayList<>(listaDeDefaultValue.length);

			for (String b : listaDeDefaultValue) {
				booleans.add(Boolean.parseBoolean(b));
			}

			obj = booleans;

		} else {

			obj = Boolean.parseBoolean(config.getDefaultValue());
		}

		return obj;
	}

	// ###################### Validar Node ###########################

	private void validarNodeList(Config config, ObjectNode node) {

		Object obj = null;

		String defaultValueType;
		Boolean defaultValueBoolean;
		List<Long> defaultValuesId = new ArrayList<>();
		Long defaultValueLong = null;
		List<String> defaultValuesName = new ArrayList<>();
		String defaultValueString = null;
		List<String> defaultValuesType = new ArrayList<>();
		List<String> defaultValuesBoolean = new ArrayList<>();
		Set<Config> listaDeHijos = config.getChildren();
		ObjectMapper objectMapper = new ObjectMapper();
		ObjectNode nodePadre = objectMapper.createObjectNode();

		for (Config h : listaDeHijos) {

			if (h.getAttribute().getAttributeType().getType().equals("NUMERIC")) {

				defaultValuesId = (List<Long>) listaNumericMethod(h);

				for (Long l : defaultValuesId) {
					defaultValuesId.add(l);
				}

			} else if (h.getAttribute().getAttributeType().getType().equals("STRING")) {

				defaultValuesName = (List<String>) listaStringsMethod(h);

				for (String s : defaultValuesName) {
					defaultValuesName.add(s);
				}

			} else if (h.getAttribute().getAttributeType().getType().equals("type")) {

				defaultValuesType = (List<String>) listaStringsMethod(h);

				for (String s : defaultValuesType) {
					defaultValuesType.add(s);
				}

			} else if (h.getAttribute().getAttributeType().getType().equals("BOOLEAN")) {

				defaultValuesBoolean = (List<String>) listaBooleansMethod(h);
				
				for (String s : defaultValuesBoolean) {
					defaultValuesBoolean.add(s);
				}
				
			} else {

				System.out.println("No es de tipo Numeric ni String");
				defaultValueString = h.getDefaultValue();
				System.out.println();
			}

		}

		List<ObjectNode> objectList = new ArrayList<>();
		asignarClaveValor(config, node, defaultValuesId, defaultValuesName, objectMapper, objectList);

	}

}
*/