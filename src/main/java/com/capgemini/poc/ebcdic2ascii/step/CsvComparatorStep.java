package com.capgemini.poc.ebcdic2ascii.step;

import com.capgemini.poc.ebcdic2ascii.tasklet.CsvComparatorTasklet;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class CsvComparatorStep {

    @Value("${csv.file.db2.location}")
    private String db2Csv;

    @Value("${csv.file.mysql.location}")
    private String mysqlCsv;

    @Value("${report.file.location}")
    private String reportLocation;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    public Step get(String mysqlCsvFileName) {
        return stepBuilderFactory.get("csvComparatorStep")
                .tasklet(new CsvComparatorTasklet(mysqlCsv+ File.separator+mysqlCsvFileName, db2Csv, reportLocation)).build();
    }


}
