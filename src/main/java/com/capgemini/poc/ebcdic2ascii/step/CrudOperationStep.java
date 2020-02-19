package com.capgemini.poc.ebcdic2ascii.step;

import com.capgemini.poc.ebcdic2ascii.dto.LineContent;
import com.capgemini.poc.ebcdic2ascii.processor.CrudOperationTransformer;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.capgemini.poc.ebcdic2ascii.reader.ItemReaderFromFileName.getItemReaderFromFileName;

@Component
public class CrudOperationStep {

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private CrudOperationTransformer crudOperationTransformer;


    @Autowired
    private ClassifierCompositeItemWriter classifierCompositeItemWriter;

    public Step get(String fileName) {

        return stepBuilderFactory.get("CRUD operations")
                .<LineContent, LineContent>chunk(10)
                .reader(getItemReaderFromFileName(fileName))
                .processor(crudOperationTransformer)
                .writer(classifierCompositeItemWriter)
                .build();
    }


}
