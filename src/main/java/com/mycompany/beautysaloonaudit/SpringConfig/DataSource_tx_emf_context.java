/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.beautysaloonaudit.SpringConfig;

import com.mycompany.beautysaloonaudit.DAO.Order.OrderServiceImpl;
import com.mycompany.beautysaloonaudit.DAO.Service.CatalogServiceImpl;
import com.mycompany.beautysaloonaudit.DAO.User.UserServiceImpl;
import java.util.Properties;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *
 * @author Artur
 */
@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = "com.mycompany.beautysaloonaudit.DAO")
@EnableJpaRepositories(basePackageClasses = {UserServiceImpl.class,OrderServiceImpl.class,CatalogServiceImpl.class},entityManagerFactoryRef = "emf",transactionManagerRef = "transactionManager")
@PropertySource("classpath:/properties/DatabaseInfo.properties")
public class DataSource_tx_emf_context {
    
    @Autowired
    Environment env;
    
    @Bean
    protected DataSource dataSource(){
        DriverManagerDataSource dataSource=new DriverManagerDataSource(env.getProperty("DAO.url"), env.getProperty("DAO.username"), env.getProperty("DAO.password"));
        dataSource.setDriverClassName(env.getProperty("DAO.driverClassName"));
        return dataSource;
    }
    
    @Bean
    protected JpaTransactionManager transactionManager(){
        return new JpaTransactionManager(emf().getObject());
    }
    
    @Bean
    protected LocalContainerEntityManagerFactoryBean emf(){
        LocalContainerEntityManagerFactoryBean emf=new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource());
        Properties properties=new Properties();
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL10Dialect");
        properties.setProperty("hibernate.max_fetch_depth", "3");
        properties.setProperty("hibernate.jdbc.fetch_size", "50");
        properties.setProperty("hibernate.jdbc.batch_size", "10");
        properties.setProperty("hibernate.show_sql", "true");
        emf.setJpaProperties(properties);
        emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        emf.setPackagesToScan("com.mycompany.beautysaloonaudit.Entities");
        return emf;
    }
    
}
