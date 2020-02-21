package com.capgemini.poc.ebcdic2ascii.config;

import com.capgemini.poc.ebcdic2ascii.builder.JobFilePathsBuilder;
import com.capgemini.poc.ebcdic2ascii.dto.JobFilePaths;
import com.capgemini.poc.ebcdic2ascii.entity.Client;
import com.capgemini.poc.ebcdic2ascii.entity.Contract;
import com.capgemini.poc.ebcdic2ascii.listener.JobCompletionNotificationListener;
import com.capgemini.poc.ebcdic2ascii.step.CsvComparatorStep;
import com.capgemini.poc.ebcdic2ascii.step.DatabaseClientsToCSVStep;
import com.capgemini.poc.ebcdic2ascii.step.DatabaseContractsToCSVStep;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnableIntegration;

import javax.persistence.EntityManagerFactory;

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

    @Autowired
    private JobLauncher jobLauncher;


    @Bean
    public JobExecution transformationJob() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {

        JobFilePaths jobFilePaths = jobFilePathsBuilder.get();




        Job job =  jobBuilderFactory.get("LOAD AND COMPARATION CSV")
                .listener(listener)
                .start(databaseClientsToCSVStep.get(jobFilePaths.getMysqlClients()))
                .next(csvComparatorStep.get(jobFilePaths.getMysqlClients(),jobFilePaths.getDb2Clients(), jobFilePaths.getClientsReport()))
                .next(databaseContractsToCSVStep.get(jobFilePaths.getMysqlContracts()))
                .next(csvComparatorStep.get(jobFilePaths.getMysqlContracts(),jobFilePaths.getDb2Contracts(), jobFilePaths.getContractsReport()))
                .next(csvComparatorStep.get(jobFilePaths.getReport1(),jobFilePaths.getReport2(), jobFilePaths.getReportComparationReport()))
                .build();

        JobParametersBuilder jobBuilder= new JobParametersBuilder();

//        JobParametersBuilder jobBuilder= new JobParametersBuilder();
        jobBuilder.addString("timestamp", String.valueOf(System.currentTimeMillis()));
        JobParameters jobParameters =jobBuilder.toJobParameters();
       return jobLauncher.run(job, jobParameters);


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
