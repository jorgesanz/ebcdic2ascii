package com.capgemini.poc.ebcdic2ascii.step;

import com.capgemini.poc.ebcdic2ascii.dto.LineContent;
import com.capgemini.poc.ebcdic2ascii.entity.Client;
import com.capgemini.poc.ebcdic2ascii.processor.CrudOperationTransformer;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.database.HibernateItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

import static com.capgemini.poc.ebcdic2ascii.reader.ItemReaderFromFileName.getItemReaderFromFileName;
import static com.capgemini.poc.ebcdic2ascii.writer.WriterFromLineContentSupplier.getFlatFileItemWriter;

@Component
public class CrudOperationStep {

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private CrudOperationTransformer crudOperationTransformer;

    @Autowired
    private JpaItemWriter<Client> itemWriter;


    @Value("${source.location}")
    private String sourceLocation;

    @Value("${target.location}")
    private String targetLocation;

    public Step get(String fileName) {

        return stepBuilderFactory.get("CRUD operations")
                .<LineContent, LineContent>chunk(10)
                .reader(getItemReaderFromFileName(targetLocation + File.separator +fileName))
                .processor(crudOperationTransformer)
                .writer(itemWriter)
                .build();
    }


}