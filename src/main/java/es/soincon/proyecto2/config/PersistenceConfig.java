package es.soincon.proyecto2.config;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = {"es.soincon.proyecto2.*"},
        transactionManagerRef = "tmSyncDB",
        entityManagerFactoryRef = "emfSyncDB")
@PropertySource("classpath:/datasource.properties")
public class PersistenceConfig {

 

    
    // #############
    // # Constants #
    // #############

    private static final String CONNECTION_POOL_NAME = "poolSyncDB";


    // ##############
    // # Properties #
    // ##############

    // Database connection params
    private @Value("${connection.driverClassName}") String dbDriverClassName;
    private @Value("${connection.url}") String dbUrl;
    private @Value("${connection.username}") String dbUsername;
    private @Value("${connection.password}") String dbPassword;

    // Hibernate params
    private @Value("${hibernate.dialect}") String hbDialect;
    private @Value("${hibernate.hbm2ddl.auto}") String hbMode;
    private @Value("${hibernate.orderInserts}") String hbOrderInserts;
    private @Value("${hibernate.orderUpdates}") String hbOrderUpdates;    

 

    
    // ##################
     // # Public methods #
     // ##################

    @Bean(name = "dsSyncDB")
    @Primary
    public DataSource dataSource() {

        HikariConfig config = new HikariConfig();        

        // JDBC pool connection params
        config.setDriverClassName(dbDriverClassName);
        config.setJdbcUrl(dbUrl);
        config.setUsername(dbUsername);
        config.setPassword(dbPassword);
        config.setPoolName(CONNECTION_POOL_NAME);     

        return new HikariDataSource(config);

    }

    @Bean(name = "emfSyncDB")
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        final JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();

        entityManagerFactory.setDataSource(dataSource());
        entityManagerFactory.setPersistenceUnitName("persistence-unit");
        entityManagerFactory.setPackagesToScan("es.soincon.proyecto2.*");
        entityManagerFactory.setJpaVendorAdapter(vendorAdapter);
        entityManagerFactory.setJpaProperties(hibernateProperties());

        return entityManagerFactory;

    }

    /**
     * Creates and returns a transaction manager
     * 
     * @return the new transaction manager
     */
    @Bean(name = "tmSyncDB")
    @Primary
    public PlatformTransactionManager transactionManager() {

 

        final var transactionManager = new JpaTransactionManager();

 

        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());

 

        return transactionManager;

    }


    // ###################
    // # Private methods #
    // ###################

    /**
     * Sets every Hibernate database connection parameter
     * 
     * @return Hibernate properties to be use during the database connection setup
     */
    private final Properties hibernateProperties() {

 

        final Properties hibernateProperties = new Properties();

 

        hibernateProperties.setProperty("hibernate.dialect", hbDialect);            
        hibernateProperties.setProperty("hibernate.order_inserts", hbOrderInserts);
        hibernateProperties.setProperty("hibernate.order_updates", hbOrderUpdates);                
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", hbMode);

 

        return hibernateProperties;

    }
}