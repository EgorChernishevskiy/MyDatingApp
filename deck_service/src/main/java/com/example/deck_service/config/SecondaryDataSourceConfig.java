package com.example.deck_service.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.example.deck_service.repository.swipe",
        entityManagerFactoryRef = "swipeEntityManager",
        transactionManagerRef = "swipeTransactionManager"
)
public class SecondaryDataSourceConfig {

    @Bean
    @ConfigurationProperties("spring.secondary-datasource")
    public DataSourceProperties swipeDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource swipeDataSource() {
        return swipeDataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean swipeEntityManager(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(swipeDataSource())
                .packages("com.example.deck_service.entity.swipe")
                .persistenceUnit("swipe")
                .build();
    }

    @Bean
    public PlatformTransactionManager swipeTransactionManager(
            @Qualifier("swipeEntityManager") EntityManagerFactory factory) {
        return new JpaTransactionManager(factory);
    }
}

