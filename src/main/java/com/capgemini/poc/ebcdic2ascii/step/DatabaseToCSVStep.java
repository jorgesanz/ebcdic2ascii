package com.capgemini.poc.ebcdic2ascii.step;

import com.capgemini.poc.ebcdic2ascii.entity.Client;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

import static com.capgemini.poc.ebcdic2ascii.writer.CsvItemWriter.databaseCsvItemWriter;

@Component
public class DatabaseToCSVStep {

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private JpaPagingItemReader<Client> itemReader;

    @Value("${csv.file.location}")
    private String targetLocation;

    public Step get(String fileName) {

        return stepBuilderFactory.get("CRUD operations")
                .<Client, Client>chunk(10)
                .reader(itemReader)
                .writer(databaseCsvItemWriter(targetLocation + File.separator +fileName))
                .build();
    }


}