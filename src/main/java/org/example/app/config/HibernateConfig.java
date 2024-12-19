package org.example.app.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Configuration
@EnableJpaRepositories("org.example.app.dao")
@EnableTransactionManagement
@PropertySource("classpath:db.properties")
@ComponentScan("org.example.app")
public class HibernateConfig {
    @Resource
    private Environment env;

//    @Autowired
//    public void setEnv(Environment env) {
//        this.env = env;
//    }

    @Bean
    public DataSource getDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(env.getProperty("db.url"));
        dataSource.setDriverClassName(env.getProperty("db.driver"));
        dataSource.setUsername(env.getProperty("db.username"));
        dataSource.setPassword(env.getProperty("db.password"));

        dataSource.setInitialSize(Integer.valueOf(env.getProperty("db.initialSize")));
        dataSource.setMinIdle(Integer.valueOf(env.getProperty("db.minIdle")));
        dataSource.setMaxIdle(Integer.valueOf(env.getProperty("db.maxIdle")));
        dataSource.setTimeBetweenEvictionRunsMillis(Integer.valueOf(env.getProperty("db.evictionTimeMilli")));
        dataSource.setMinEvictableIdleTimeMillis(Integer.valueOf(env.getProperty("db.minEvictableIdleTimeMillis")));
        dataSource.setTestOnBorrow(Boolean.valueOf(env.getProperty("db.testOnBorrow")));
        dataSource.setValidationQuery(env.getProperty("db.validationQuery"));
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(getDataSource());
        em.setPackagesToScan(env.getProperty("db.packagesToScan"));
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setJpaProperties(getHibernateProperties());
        return em;
    }

    private Properties getHibernateProperties() {
        try {
            Properties properties = new Properties();
            InputStream is = getClass().getClassLoader().getResourceAsStream("hibernate.properties");
            properties.load(is);
            return properties;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

//    @Bean
//    public LocalSessionFactoryBean getSessionFactory() {
//        LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
//        factoryBean.setDataSource(getDataSource());
//
//        Properties props = new Properties();
//        props.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
//        props.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
//
//        factoryBean.setHibernateProperties(props);
//        factoryBean.setPackagesToScan("hiber.model");
//        return factoryBean;
//    }

//    @Bean
//    public HibernateTransactionManager getTransactionManager() {
//        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
//        transactionManager.setSessionFactory(getSessionFactory().getObject());
//        return transactionManager;
//    }

    @Bean
    public PlatformTransactionManager getTransactionManager2() {
        JpaTransactionManager manager = new JpaTransactionManager();
        manager.setEntityManagerFactory(entityManagerFactory().getObject());
        return manager;
    }
}
