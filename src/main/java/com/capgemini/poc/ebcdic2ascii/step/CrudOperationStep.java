package com.capgemini.poc.ebcdic2ascii.step;

import com.capgemini.poc.ebcdic2ascii.dto.LineContent;
import com.capgemini.poc.ebcdic2ascii.processor.CrudOperationTransformer;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

import static com.capgemini.poc.ebcdic2ascii.reader.ItemReaderFromFileName.getItemReaderFromFileName;

@Component
public class CrudOperationStep {

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private CrudOperationTransformer crudOperationTransformer;


    @Autowired
    private ClassifierCompositeItemWriter classifierCompositeItemWriter;


    @Value("${origin.file.location}")
    private String sourceLocation;

    @Value("${transformed.file.location}")
    private String targetLocation;

    public Step get(String fileName) {

        return stepBuilderFactory.get("CRUD operations")
                .<LineContent, LineContent>chunk(10)
                .reader(getItemReaderFromFileName(targetLocation + File.separator +fileName))
                .processor(crudOperationTransformer)
                .writer(classifierCompositeItemWriter)
                .build();
    }


}
