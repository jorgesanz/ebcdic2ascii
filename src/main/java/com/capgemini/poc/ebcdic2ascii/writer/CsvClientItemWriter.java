package com.capgemini.poc.ebcdic2ascii.writer;

import com.capgemini.poc.ebcdic2ascii.entity.Client;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.FieldExtractor;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.core.io.FileSystemResource;

@Slf4j
public class CsvClientItemWriter {


    public static ItemWriter<Client> csvClientItemWriter(String exportFilePath) {

        log.info("export client file set to "+exportFilePath);
        FlatFileItemWriter<Client> csvFileWriter = new FlatFileItemWriter<>();

        String exportFileHeader = "ENT-COD-CLIENTE;ENT-NUM-PRESNN-COB;ENT-TIP-SR-SRA-EMPRESA;ENT-NOM-TITULAR-CUENTA;ENT-NOM-APED-1-TIT-CTA X(45);ENT-NOM-APED-2-TIT-CTA;ENT-NUM-DNI-NIF-CIF-CU;ENT-TIP-DCO-IDE-TIT-CU";
        StringHeaderWriter headerWriter = new StringHeaderWriter(exportFileHeader);
        csvFileWriter.setHeaderCallback(headerWriter);

        csvFileWriter.setResource(new FileSystemResource(exportFilePath));

        LineAggregator<Client> lineAggregator = createStudentLineAggregator();
        csvFileWriter.setLineAggregator(lineAggregator);

        return csvFileWriter;
    }

    private static LineAggregator<Client> createStudentLineAggregator() {
        DelimitedLineAggregator<Client> lineAggregator = new DelimitedLineAggregator<>();
        lineAggregator.setDelimiter(";");

        FieldExtractor<Client> fieldExtractor = createStudentFieldExtractor();
        lineAggregator.setFieldExtractor(fieldExtractor);

        return lineAggregator;
    }

    private static FieldExtractor<Client> createStudentFieldExtractor() {
        BeanWrapperFieldExtractor<Client> extractor = new BeanWrapperFieldExtractor<>();
        extractor.setNames(new String[] {"codCLiente", "numPresnnCob", "tipSrSraEmpresa","nomTitularCuenta","nomAped1TitCta","nomAped2TitCta","numDniNifCifCu","tipDcoIdeTitCu"});
        return extractor;
    }
}
