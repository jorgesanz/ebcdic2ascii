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

        String exportFileHeader = "ENT-COD-CONTRATO;ENT-COD-CLIENTE;ENT-COD-PROVINCIA;ENT-COD-POBLACION;ENT-COD-CALLE;ENT-COD-FINCA;ENT-COD-PUNTO-SUMIN;ENT-NUM-PRESNN-COB;ENT-COD-MULTIS;ENT-COD-ESTRU-MSERV;ENT-IDE-ZONA-FACTN;ENT-COD-SEGMENTO;ENT-TIP-ORG-INTERNA;ENT-COD-ORGAN-INTER;ENT-COD-CNAE;ENT-TIP-CONTRATO;ENT-FEC-ALTA-CONTRATO;ENT-FORMA-PAGO;ENT-COD-PAIS;ENT-COD-POSTAL-CTO;ENT-TIP-MODO-PAGO;ENT-DES-ADI-CONTRATO";
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
        extractor.setNames(new String[] {"codCliente", "codContrato", "codProvincia","codPoblacion","codCalle","codFinca","codPuntoSumin","numPresnn","codMultis","codEstruMserv","ideZonaFactn","codSegmento","tipOrgInterna","codOrganInter","codCnae"});
        return extractor;
    }
}
