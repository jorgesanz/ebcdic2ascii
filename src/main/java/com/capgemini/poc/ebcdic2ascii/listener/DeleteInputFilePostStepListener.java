package com.capgemini.poc.ebcdic2ascii.listener;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterWrite;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;
import org.springframework.stereotype.Component;

import javax.batch.api.listener.StepListener;

@Component
public class DeleteInputFilePostStepListener extends StepExecutionListenerSupport {

    @Override
    @AfterWrite
    public ExitStatus afterStep(StepExecution stepExecution) {
        String fileToDelete = stepExecution.getJobParameters().getString("fileName");
        return ExitStatus.COMPLETED;
    }
}
