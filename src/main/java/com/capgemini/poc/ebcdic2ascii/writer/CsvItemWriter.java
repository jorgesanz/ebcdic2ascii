package com.capgemini.poc.ebcdic2ascii.writer;

import com.capgemini.poc.ebcdic2ascii.entity.Client;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.FieldExtractor;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.core.io.FileSystemResource;

public class CsvItemWriter {

    ItemWriter<Client> databaseCsvItemWriter(String exportFilePath) {
        FlatFileItemWriter<Client> csvFileWriter = new FlatFileItemWriter<>();

        String exportFileHeader = "NAME;EMAIL_ADDRESS;PACKAGE";
        StringHeaderWriter headerWriter = new StringHeaderWriter(exportFileHeader);
        csvFileWriter.setHeaderCallback(headerWriter);

        csvFileWriter.setResource(new FileSystemResource(exportFilePath));

        LineAggregator<Client> lineAggregator = createStudentLineAggregator();
        csvFileWriter.setLineAggregator(lineAggregator);

        return csvFileWriter;
    }

    private LineAggregator<Client> createStudentLineAggregator() {
        DelimitedLineAggregator<Client> lineAggregator = new DelimitedLineAggregator<>();
        lineAggregator.setDelimiter(";");

        FieldExtractor<Client> fieldExtractor = createStudentFieldExtractor();
        lineAggregator.setFieldExtractor(fieldExtractor);

        return lineAggregator;
    }

    private FieldExtractor<Client> createStudentFieldExtractor() {
        BeanWrapperFieldExtractor<Client> extractor = new BeanWrapperFieldExtractor<>();
        extractor.setNames(new String[] {"name", "emailAddress", "purchasedPackage"});
        return extractor;
    }
}
