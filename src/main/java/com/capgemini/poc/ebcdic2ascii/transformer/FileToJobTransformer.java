package com.capgemini.poc.ebcdic2ascii.transformer;


import com.capgemini.poc.ebcdic2ascii.builder.JobFilePathsBuilder;
import com.capgemini.poc.ebcdic2ascii.dto.JobFilePaths;
import com.capgemini.poc.ebcdic2ascii.listener.JobCompletionNotificationListener;
import com.capgemini.poc.ebcdic2ascii.step.*;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.integration.launch.JobLaunchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.Transformer;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Date;

@Component
public class FileToJobTransformer {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private MoveTranslatedFileStep moveTranslatedFileStep;

    @Autowired
    private CrudOperationStep crudOperationStep;

    @Autowired
    private DatabaseClientsToCSVStep databaseClientsToCSVStep;

    @Autowired
    private DatabaseContractsToCSVStep databaseContractsToCSVStep;

    @Autowired
    private CsvComparatorStep csvComparatorStep;

    @Autowired
    private JobCompletionNotificationListener listener;

    @Autowired
    private JobFilePathsBuilder jobFilePathsBuilder;



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


}
