package com.capgemini.poc.ebcdic2ascii.transformer;

import com.capgemini.poc.ebcdic2ascii.listener.JobCompletionNotificationListener;
import com.capgemini.poc.ebcdic2ascii.step.MoveTranslatedFileStep;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.integration.launch.JobLaunchRequest;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.integration.annotation.Transformer;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Date;

@Component
public class FileToJobTransformer implements ApplicationContextAware {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private MoveTranslatedFileStep moveTranslatedFileStep;

    @Autowired
    private JobCompletionNotificationListener listener;


    private ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    @Transformer(inputChannel = "fileInputChannel", outputChannel = "jobChannel")
    public JobLaunchRequest transform(File aFile) {

        String fileName = aFile.getAbsolutePath();

        JobParameters jobParameters = new JobParametersBuilder()
                .addString("fileName", fileName)
//                .addDate("dateTime", new Date())
                .toJobParameters();

        JobLaunchRequest request = new JobLaunchRequest(transformationJob(fileName), jobParameters);

        return request;
    }

    public Job transformationJob(String fileName) {
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
				.listener(listener)
                .flow(moveTranslatedFileStep.get(fileName))
                .end()
                .build();
    }


}
