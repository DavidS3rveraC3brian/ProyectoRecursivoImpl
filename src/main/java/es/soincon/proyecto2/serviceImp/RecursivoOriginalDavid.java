package es.soincon.proyecto2.serviceImp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import es.soincon.proyecto2.configValdationException.ConfigValidationException;
import es.soincon.proyecto2.entity.Config;
import es.soincon.proyecto2.repository.IConfigRepository;
import es.soincon.proyecto2.service.IRecursividadService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RecursivoOriginalDavid implements IRecursividadService {

	private final ObjectMapper objectMapper;
	private final IConfigRepository configRepository;

	public RecursivoOriginalDavid(IConfigRepository configRepository, ObjectMapper objectMapper) {
		super();
		this.configRepository = configRepository;
		this.objectMapper = objectMapper;
	}

	// ######################### Obtener Configs #####################################################

	public String obtenerConfigs() throws JsonProcessingException {

		ObjectNode nodePadre = objectMapper.createObjectNode();
		List<Config> entidades = configRepository.findByParentIsNull();

		log.info("Padres maestros: "
				+ entidades.stream().map(c -> c.getAttribute().getName()).collect(Collectors.toList()));

		for (Config padreNull : entidades) {
			recursiveMethod(padreNull, nodePadre);
		}

		return objectMapper.writeValueAsString(nodePadre);
	}

	// ######################### Metodo Recursivo ####################################################

	private void recursiveMethod(Config config, ObjectNode nodePadre) throws JsonProcessingException {

		ObjectNode nodeConfig = objectMapper.createObjectNode();
		List<Config> listaHijos = config.getChildren().stream().collect(Collectors.toList());

		if (config.getAttribute().getAttributeType().getIsList()
				&& config.getAttribute().getAttributeType().getType().equals("NODE")) {
			log.info(config.getDefaultValue());
			validarNodeList(config, nodePadre);

		} else if (config.getAttribute().getAttributeType().getType().equals("NODE")) {

			for (Config hijo : listaHijos) {
				log.info(hijo.getDefaultValue());
				if(hijo.getChildren().isEmpty() && hijo.getAttribute().getAttributeType().getIsEnum()) {

					nodeConfig.putPOJO(hijo.getAttribute().getName(), validarIsEnum(hijo));

				}else if (!hijo.getChildren().isEmpty()) {
					recursiveMethod(hijo, nodeConfig);

				} else {

					nodeConfig.putPOJO(hijo.getAttribute().getName(), validarType(hijo));
				}

			}

			nodePadre.putPOJO(config.getAttribute().getName(), nodeConfig);

		} else {
			log.info(config.getDefaultValue());
			nodePadre.putPOJO(config.getAttribute().getName(), config.getDefaultValue());
		}
	}

	// ######################### Metodo ValidarType ###################################################

	private Object validarType(Config config) {
		Object obj = null;

		switch (config.getAttribute().getAttributeType().getType().toUpperCase()) {

		case "STRING":
			obj = validarString(config);
			break;

		case "NUMERIC":
			obj = validarLong(config);
			break;

		case "BOOLEAN":
			obj = validarBoolean(config);
			break;

		default:
			config.getDefaultValue();

		}

		return obj;
	}

	// ######################### Metodo validarString ##################################################

	private Object validarString(Config config) {
		List<String> listaDefaultValues = new ArrayList<String>();
		// Añadido post
		List<Config> listaHijos = config.getChildren().stream().collect(Collectors.toList());

		// Añadido post
		for (Config hijo : listaHijos) {
			if (hijo.getAttribute().getAttributeType().getIsList()) {
				hijo.getDefaultValue().toString();
			}

		}

		if (config.getAttribute().getAttributeType().getIsList()) {

			List<String> values = valueToList(config);

			for (String v : values) {
				listaDefaultValues.add(v);
			}

		} else {

			Object obj = config.getDefaultValue();
			return obj;
		}

		return listaDefaultValues;

	}

	// ######################### Metodo validarLong ##################################################

	private Object validarLong(Config config) {
		List<Long> listaDeLongs = new ArrayList<>();

		if (config.getAttribute().getAttributeType().getIsList()) {

			List<String> listaDefaultValues = valueToList(config);

			for (String l : listaDefaultValues) {
				if(l == null) {
					listaDeLongs.add(null);
				}else {
					listaDeLongs.add(Long.parseLong(l));
				}
			}

			return listaDeLongs;

		} else {

			Object obj = Long.parseLong(config.getDefaultValue());
			return obj;
		}

	}

	// ######################### Metodo validarEnum #################################################

	// ###################### Validar Enum ###########################

	private Object validarIsEnum(Config config) {
		// Aqui tendre todos los Values de AttributeTypeValues
		List<String> configAcceptedValues = config.getAttribute()
				.getAttributeType()
				.getAttributeTypeValues()
				.stream().map(a -> a.getValue()).collect(Collectors.toList());
		//		AttributeTypeValue::getValue
		
		// Lista de Default values del config sin puntos ni comas
		List<String> defaultValuesConfig = valueToList(config);

		for(String defaultValue : defaultValuesConfig) {
			if(!configAcceptedValues.contains(defaultValue)) 
				throw new ConfigValidationException(config);

		}
		//		defaultValuesConfig.stream().map(dv -> {
		//			if(!configAcceptedValues.contains(dv)) throw new ConfigValidationException();
		//		});
		return defaultValuesConfig;
	}

	// ######################### Metodo Boolean #####################################################

	private Object validarBoolean(Config config) {

		Object obj;

		List<Boolean> listaDeBooleans = new ArrayList<>();

		if (config.getAttribute().getAttributeType().getIsList()) {

			List<String> listaDefaultValues = valueToList(config);

			for (String l : listaDefaultValues) {
				listaDeBooleans.add(Boolean.parseBoolean(l));
			}
			return listaDeBooleans;

		} else {

			obj = Boolean.parseBoolean(config.getDefaultValue());

		}
		return obj;
	}

	// #################################################################
	// # 															   #
	// # 					Metodos Auxiliares 						   #
	// # 															   #
	// #################################################################

	// ######################### Metodo valueToList ###############################################

	private List<String> valueToList(Config config) {
		List<String> listaDefaultValues = new ArrayList<String>();
		String childrenDefaultValue = config.getDefaultValue();
		if(childrenDefaultValue != null) {
			String[] arrayDefaultValues = childrenDefaultValue.split(";");

			for (String lista : arrayDefaultValues) {
				if(!StringUtils.hasText(lista)) {
					listaDefaultValues.add(null);

				}else {
					listaDefaultValues.add(lista);
				}
			}

		}
		return listaDefaultValues;
	}

	
	//ESTE MUESTRA LOS NODOS INCOMPLETOS, SI ALGUN CONFIG TIENE ALGUN DEFVALUE NULO, NO SE CREA, EL RESTO SI
	private void validarNodeList(Config config, ObjectNode nodeConfig) {
		// lista h1, lista h2
		Map<String, List<Object>> mapaClaveValor = new HashMap<>();

		for (Config hijo : config.getChildren()) {
			List<String> listaHijo = valueToList(hijo);
			List<Object> listaObjetosHijo = new LinkedList<Object>();

			if (hijo.getAttribute().getAttributeType().getType().equals("NUMERIC")) {

				for (String o : listaHijo) {
					if(o == null) {
						listaObjetosHijo.add(null);
					}else {
						listaObjetosHijo.add(Long.parseLong(o));
					}
				}

			} else if (hijo.getAttribute().getAttributeType().getType().equals("BOOLEAN")) {
				for (String o : listaHijo) {

					listaObjetosHijo.add(Boolean.parseBoolean(o));
				}

			} else if(hijo.getAttribute().getAttributeType().getIsEnum()) {
				@SuppressWarnings("unchecked")
				List<String> enumerados = (List<String>) validarIsEnum(hijo);
				for( String enumerado : enumerados) {

					listaObjetosHijo.add(enumerado);
				}

			} else {
				for (String o : listaHijo) {

					listaObjetosHijo.add(o);
				}
			}
			mapaClaveValor.put(hijo.getAttribute().getName(), listaObjetosHijo);
		}

		List<ObjectNode> objectNodes = new ArrayList<>();
		List<Object> listaKeys = mapaClaveValor.keySet().stream().collect(Collectors.toList());

		for (int i = 0; i < mapaClaveValor.get(listaKeys.get(0)).size(); i++) { // indice defValues

			ObjectNode nodo = objectMapper.createObjectNode();

			for (int j = 0; j < listaKeys.size(); j++) { // indice registros
				if(mapaClaveValor.get(listaKeys.get(j).toString()).get(i) != null) {
					nodo.putPOJO(listaKeys.get(j).toString(), mapaClaveValor.get(listaKeys.get(j)).get(i));
				}
			}
			objectNodes.add(nodo);
		}

		ArrayNode arrayNode = objectMapper.valueToTree(objectNodes);
		nodeConfig.putPOJO(config.getAttribute().getName(), arrayNode);

	}
}
