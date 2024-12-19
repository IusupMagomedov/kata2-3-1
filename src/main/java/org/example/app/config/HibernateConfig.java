package org.example.app.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

@Configuration
@EnableJpaRepositories("org.example.app.dao")
@EnableTransactionManagement
@PropertySource("classpath:db.properties")
@ComponentScan("org.example.app")
public class HibernateConfig {
    private final Environment env;

    @Autowired
    public HibernateConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public DataSource getDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(env.getProperty("db.url"));
        dataSource.setDriverClassName(env.getProperty("db.driver"));
        dataSource.setUsername(env.getProperty("db.username"));
        dataSource.setPassword(env.getProperty("db.password"));

        dataSource.setInitialSize(Integer.parseInt(Objects.requireNonNull(env.getProperty("db.initialize"))));
        dataSource.setMinIdle(Integer.parseInt(Objects.requireNonNull(env.getProperty("db.minIdle"))));
        dataSource.setMaxIdle(Integer.parseInt(Objects.requireNonNull(env.getProperty("db.maxIdle"))));
        dataSource.setTimeBetweenEvictionRunsMillis(Integer.parseInt(Objects.requireNonNull(env.getProperty("db.timeBetweenEvictionRunsMillis"))));
        dataSource.setMinEvictableIdleTimeMillis(Integer.parseInt(Objects.requireNonNull(env.getProperty("db.minEvictableIdleTimeMillis"))));
        dataSource.setTestOnBorrow(Boolean.parseBoolean(env.getProperty("db.testOnBorrow")));
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

    @Bean
    public PlatformTransactionManager getTransactionManager2() {
        JpaTransactionManager manager = new JpaTransactionManager();
        manager.setEntityManagerFactory(entityManagerFactory().getObject());
        return manager;
    }
}
