package com.capgemini.poc.ebcdic2ascii.builder;

import com.capgemini.poc.ebcdic2ascii.dto.JobFilePaths;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class JobFilePathsBuilder {

    @Value("${origin.file.location}")
    private String sourceLoadLocation;

    public JobFilePaths get(String inputFileName){

        JobFilePaths jobFilePaths = new JobFilePaths();

        jobFilePaths.setInputFileLocation(sourceLoadLocation + File.separator + inputFileName);

        return jobFilePaths;
    }
}
