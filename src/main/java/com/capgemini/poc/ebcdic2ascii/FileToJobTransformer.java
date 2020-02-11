package com.capgemini.poc.ebcdic2ascii;


import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.integration.launch.JobLaunchRequest;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.integration.annotation.Transformer;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Date;
import java.util.logging.Logger;

@Component
public class FileToJobTransformer implements ApplicationContextAware {

    @Autowired
    private Job job;

    private ApplicationContext context;

    @Transformer(inputChannel = "fileToJobProcessor", outputChannel = "jobChannel")
    public JobLaunchRequest transform(File aFile) {

        String fileName = aFile.getAbsolutePath();

        JobParameters jobParameters = new JobParametersBuilder()
                .addString("fileName", fileName)
                .addDate("dateTime", new Date())
                .toJobParameters();

        JobLaunchRequest request = new JobLaunchRequest(job, jobParameters);

        return request;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}
