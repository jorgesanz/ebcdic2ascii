package com.capgemini.poc.ebcdic2ascii.config;

import com.capgemini.poc.ebcdic2ascii.builder.JobFilePathsBuilder;
import com.capgemini.poc.ebcdic2ascii.classifier.CrudOperationClassifier;
import com.capgemini.poc.ebcdic2ascii.dto.CrudOperation;
import com.capgemini.poc.ebcdic2ascii.dto.JobFilePaths;
import com.capgemini.poc.ebcdic2ascii.entity.Client;
import com.capgemini.poc.ebcdic2ascii.entity.Contract;
import com.capgemini.poc.ebcdic2ascii.listener.JobCompletionNotificationListener;
import com.capgemini.poc.ebcdic2ascii.step.CsvComparatorStep;
import com.capgemini.poc.ebcdic2ascii.step.DatabaseClientsToCSVStep;
import com.capgemini.poc.ebcdic2ascii.step.DatabaseContractsToCSVStep;
import com.capgemini.poc.ebcdic2ascii.writer.DeleteJdbcWriter;
import com.capgemini.poc.ebcdic2ascii.writer.JpaItemDeleter;
import com.capgemini.poc.ebcdic2ascii.writer.UpsertJdbcWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
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

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private JobFilePathsBuilder jobFilePathsBuilder;

    @Autowired
    private DatabaseClientsToCSVStep databaseClientsToCSVStep;

    @Autowired
    private DatabaseContractsToCSVStep databaseContractsToCSVStep;

    @Autowired
    private CsvComparatorStep csvComparatorStep;

    @Autowired
    private JobCompletionNotificationListener listener;


    @Bean
    public Job transformationJob() {

        JobFilePaths jobFilePaths = jobFilePathsBuilder.get();


        return jobBuilderFactory.get("importUserJob")
                .listener(listener)
                .start(databaseClientsToCSVStep.get(jobFilePaths.getMysqlClients()))
                .next(csvComparatorStep.get(jobFilePaths.getMysqlClients(),jobFilePaths.getDb2Clients(), jobFilePaths.getClientsReport()))
                .next(databaseContractsToCSVStep.get(jobFilePaths.getMysqlContracts()))
                .next(csvComparatorStep.get(jobFilePaths.getMysqlClients(),jobFilePaths.getDb2Clients(), jobFilePaths.getContractsReport()))
                .build();
    }



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
