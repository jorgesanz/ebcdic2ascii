package com.capgemini.poc.ebcdic2ascii.config;

import com.capgemini.poc.ebcdic2ascii.classifier.CrudOperationClassifier;
import com.capgemini.poc.ebcdic2ascii.dto.CrudOperation;
import com.capgemini.poc.ebcdic2ascii.entity.Client;
import com.capgemini.poc.ebcdic2ascii.entity.Contract;
import com.capgemini.poc.ebcdic2ascii.writer.DeleteJdbcWriter;
import com.capgemini.poc.ebcdic2ascii.writer.JpaItemDeleter;
import com.capgemini.poc.ebcdic2ascii.writer.UpsertJdbcWriter;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.integration.launch.JobLaunchingMessageHandler;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.filters.SimplePatternFileListFilter;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.io.File;

@EnableIntegration
@EnableBatchProcessing
@Configuration
public class BatchIntegrationConfig {

    @Autowired
    EntityManagerFactory emf;

    @Bean
    public JpaPagingItemReader<Client> ClientReader(){

        JpaPagingItemReader reader = new JpaPagingItemReader();
        reader.setEntityManagerFactory(emf);
        reader.setQueryString("SELECT p from Client p");
        return reader;
    }

    @Bean
    public JpaPagingItemReader<Contract> ContractReader(){

        JpaPagingItemReader reader = new JpaPagingItemReader();
        reader.setEntityManagerFactory(emf);
        reader.setQueryString("SELECT p from Contract p");
        return reader;
    }

}
