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

    @Transformer(inputChannel = "fileInputChannel", outputChannel = "jobChannel")
    public JobLaunchRequest transform(File aFile) {

        String fileName = aFile.getName();

        JobParameters jobParameters = new JobParametersBuilder()
                .addString("fileName", fileName)
                .addDate("dateTime", new Date())
                .toJobParameters();

        JobLaunchRequest request = new JobLaunchRequest(transformationJob(fileName), jobParameters);

        return request;
    }

    public Job transformationJob(String fileName) {

        JobFilePaths jobFilePaths = jobFilePathsBuilder.get(fileName);


        return jobBuilderFactory.get("importUserJob")
//                .incrementer(new RunIdIncrementer())
				.listener(listener)
                //comparation before CRUD operations
                .start(databaseClientsToCSVStep.get(jobFilePaths.getMysqlClientsBeforeLoad()))
                .next(csvComparatorStep.get(jobFilePaths.getMysqlClientsBeforeLoad(),jobFilePaths.getDb2ClientsBeforeLoad(), jobFilePaths.getClientsReportBeforeLoad()))
                .next(databaseContractsToCSVStep.get(jobFilePaths.getMysqlContractsBeforeLoad()))
                .next(csvComparatorStep.get(jobFilePaths.getMysqlContractsBeforeLoad(),jobFilePaths.getDb2ContractsBeforeLoad(), jobFilePaths.getContractsReportBeforeLoad()))
                //CRUD operations

//                .next(moveTranslatedFileStep.get(jobFilePaths.getInputBinaryLocation(), jobFilePaths.getInputTransformedLocation()))
                .next(crudOperationStep.get(jobFilePaths.getInputBinaryLocation()))
                //comparation after CRUD operations
                .next(databaseClientsToCSVStep.get(jobFilePaths.getMysqlClientsAfterLoad()))
                .next(csvComparatorStep.get(jobFilePaths.getMysqlClientsAfterLoad(),jobFilePaths.getDb2ClientsAfterLoad(), jobFilePaths.getClientsReportAfterLoad()))
                .next(databaseContractsToCSVStep.get(jobFilePaths.getMysqlContractsAfterLoad()))
                .next(csvComparatorStep.get(jobFilePaths.getMysqlClientsAfterLoad(),jobFilePaths.getDb2ClientsAfterLoad(), jobFilePaths.getContractsReportAfterLoad()))
                .build();
    }


}
