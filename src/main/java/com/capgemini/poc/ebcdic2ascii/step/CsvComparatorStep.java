package com.capgemini.poc.ebcdic2ascii.step;

import com.capgemini.poc.ebcdic2ascii.tasklet.CsvComparatorTasklet;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CsvComparatorStep {

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    public Step get(String mysqlCsvFile, String db2CsvFile, String outputReport) {
        return stepBuilderFactory.get("csvComparatorStep")
                .tasklet(new CsvComparatorTasklet(mysqlCsvFile, db2CsvFile, outputReport)).build();
    }

}
