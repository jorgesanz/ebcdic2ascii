package com.capgemini.poc.ebcdic2ascii.step;

import com.capgemini.poc.ebcdic2ascii.entity.Client;
import org.apache.commons.io.FilenameUtils;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

import static com.capgemini.poc.ebcdic2ascii.writer.CsvClientItemWriter.csvClientItemWriter;

@Component
public class DatabaseClientsToCSVStep {

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private JpaPagingItemReader<Client> clientItemReader;


    @Value("${csv.file.mysql.location}")
    private String targetLocation;

    public Step get(String fileName) {

        return stepBuilderFactory.get("CRUD operations")
                .<Client, Client>chunk(10)
                .reader(clientItemReader)
                .writer(csvClientItemWriter(targetLocation + File.separator +changeExtension("client"+fileName,"csv")))
                .build();
    }

    private String changeExtension(String fileName, String extension) {
        return FilenameUtils.removeExtension(fileName)+"."+extension;
    }


}
