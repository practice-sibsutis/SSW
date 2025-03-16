package com.sibsutis.study.helloworld.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import jakarta.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

//The @EnableJpaRepositories annotation enables scanning of the package
//received as an argument for Spring Data repositories.
@EnableJpaRepositories("com.sibsutis.study.helloworld.repositories")
public class SpringDataConfiguration {
    //Create a data source bean.
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        //Specify the JDBC properties—the driver.
        dataSource.setDriverClassName("org.postgresql.Driver");
        //The URL of the database.
        dataSource.setUrl("jdbc:postgresql://localhost:5432/exampleshibernate");
        dataSource.setUsername("postgres");
        dataSource.setPassword("742613103");
        return dataSource;
    }

    //Create a transaction manager bean based on an entity manager factory. Every
    //interaction with the database should occur within transaction boundaries, and
    //Spring Data needs a transaction manager bean.
    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

    //Create the JPA vendor adapter bean needed by JPA to interact with Hibernate.
    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        //Configure this vendor adapter to access a PostgreSQL database.
        jpaVendorAdapter.setDatabase(Database.POSTGRESQL);
        //Show the SQL code while it is executed.
        jpaVendorAdapter.setShowSql(true);
        return jpaVendorAdapter;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        //Create a LocalContainerEntityManagerFactoryBean. This is a factory bean that
        //produces an EntityManagerFactory following the JPA standard container bootstrap
        //contract.
        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean =
                new LocalContainerEntityManagerFactoryBean();
        //Set the data source.
        localContainerEntityManagerFactoryBean.setDataSource(dataSource());
        Properties properties = new Properties();
        //Set the database to be created from scratch every time the program is executed.
        properties.put("hibernate.hbm2ddl.auto", "create");
        localContainerEntityManagerFactoryBean.setJpaProperties(properties);
        //Set the vendor adapter.
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter());
        //Set the packages to scan for entity classes. As the Message entity is located in
        //com.sibsutis.study, we set this package to be scanned.
        localContainerEntityManagerFactoryBean.setPackagesToScan("com.sibsutis.study.helloworld");
        return localContainerEntityManagerFactoryBean;
    }
}
