package com.capgemini.poc.ebcdic2ascii.transformer;


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
        return jobBuilderFactory.get("importUserJob")
//                .incrementer(new RunIdIncrementer())
				.listener(listener)
                .start(moveTranslatedFileStep.get(fileName))
                .next(crudOperationStep.get(fileName))
                .next(databaseClientsToCSVStep.get(fileName))
                .next(databaseContractsToCSVStep.get(fileName))
                .next(csvComparatorStep.get(fileName))
                .build();
    }


}
