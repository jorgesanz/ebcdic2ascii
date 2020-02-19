package com.capgemini.poc.ebcdic2ascii.step;

import com.capgemini.poc.ebcdic2ascii.entity.Contract;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.capgemini.poc.ebcdic2ascii.writer.CsvContractItemWriter.csvContractItemWriter;

@Component
public class DatabaseContractsToCSVStep {

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private JpaPagingItemReader<Contract> contractItemReader;




    public Step get(String fileName) {

        return stepBuilderFactory.get("CRUD operations")
                .<Contract, Contract>chunk(10)
                .reader(contractItemReader)
                .writer(csvContractItemWriter(fileName))
                .build();
    }

}
