package es.soincon.proyecto2;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.TimeZone;

import javax.sql.DataSource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication(scanBasePackages = { "es.soincon.proyecto2" })
@Slf4j
public class Proyecto2Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        
        // Crea y configura el contexto de la aplicación Spring Boot
        ConfigurableApplicationContext context = new SpringApplicationBuilder(Proyecto2Application.class).run(args);
        
        // Obtiene una instancia de DataSource del contexto de la aplicación
        DataSource dataSource = context.getBean(DataSource.class);
        try {
        	// Establece conexion a la base de datos
            Connection connection = dataSource.getConnection();
            // Obtiene metadatos de la base de datos
            DatabaseMetaData metaData = connection.getMetaData();
            // Utiliza SLF4J para registrar la información en un archivo de registro
            log.info("Connected to database: {}", metaData.getURL());
            // Cierra la conexion
            connection.close();
        } catch (SQLException e) {
            log.error("Error connection to the database",e);
        }
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Proyecto2Application.class);
    }
}

