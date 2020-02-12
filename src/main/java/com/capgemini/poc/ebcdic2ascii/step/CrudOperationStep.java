package com.capgemini.poc.ebcdic2ascii.step;

import com.capgemini.poc.ebcdic2ascii.dto.LineContent;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
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
    private ItemProcessor processor;

    @Value("${source.location}")
    private String sourceLocation;

    @Value("${target.location}")
    private String targetLocation;

    public Step get(String fileName) {

        return stepBuilderFactory.get("CRUD operations")
                .<LineContent, LineContent>chunk(10)
                .reader(getItemReaderFromFileName(targetLocation + File.separator +fileName))
                .processor(processor)
                .writer(getFlatFileItemWriter(targetLocation + File.separator + "2"+fileName ))
                .build();
    }


}
