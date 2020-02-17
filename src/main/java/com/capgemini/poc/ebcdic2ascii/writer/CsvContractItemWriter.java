package com.capgemini.poc.ebcdic2ascii.writer;

import com.capgemini.poc.ebcdic2ascii.entity.Contract;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.FieldExtractor;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.core.io.FileSystemResource;

public class CsvContractItemWriter {


    public static ItemWriter<Contract> csvContractItemWriter(String exportFilePath) {
        FlatFileItemWriter<Contract> csvFileWriter = new FlatFileItemWriter<>();

        String exportFileHeader = "NAME;EMAIL_ADDRESS;PACKAGE";
        StringHeaderWriter headerWriter = new StringHeaderWriter(exportFileHeader);
        csvFileWriter.setHeaderCallback(headerWriter);

        csvFileWriter.setResource(new FileSystemResource(exportFilePath));

        LineAggregator<Contract> lineAggregator = createStudentLineAggregator();
        csvFileWriter.setLineAggregator(lineAggregator);

        return csvFileWriter;
    }

    private static LineAggregator<Contract> createStudentLineAggregator() {
        DelimitedLineAggregator<Contract> lineAggregator = new DelimitedLineAggregator<>();
        lineAggregator.setDelimiter(";");

        FieldExtractor<Contract> fieldExtractor = createStudentFieldExtractor();
        lineAggregator.setFieldExtractor(fieldExtractor);

        return lineAggregator;
    }

    private static FieldExtractor<Contract> createStudentFieldExtractor() {
        BeanWrapperFieldExtractor<Contract> extractor = new BeanWrapperFieldExtractor<>();
        extractor.setNames(new String[] {"codCliente", "codContrato", "codProvincia"});
        return extractor;
    }
}
