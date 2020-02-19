package com.capgemini.poc.ebcdic2ascii.step;

import com.capgemini.poc.ebcdic2ascii.entity.Client;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.capgemini.poc.ebcdic2ascii.writer.CsvClientItemWriter.csvClientItemWriter;

@Component
public class DatabaseClientsToCSVStep {

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private JpaPagingItemReader<Client> clientItemReader;

    public Step get(String fileName) {

        return stepBuilderFactory.get("CRUD operations")
                .<Client, Client>chunk(10)
                .reader(clientItemReader)
                .writer(csvClientItemWriter(fileName))
                .build();
    }

}
