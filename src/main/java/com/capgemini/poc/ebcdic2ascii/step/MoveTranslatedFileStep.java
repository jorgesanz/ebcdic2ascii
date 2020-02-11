package com.capgemini.poc.ebcdic2ascii.step;

import com.capgemini.poc.ebcdic2ascii.dto.LineContent;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.capgemini.poc.ebcdic2ascii.reader.ItemReaderFromFileName.getItemReaderFromFileName;

@Component
public class MoveTranslatedFileStep {

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private ItemProcessor processor;

    @Autowired
    private ItemWriter itemWriter;

    public Step get(String fileName) {
        return stepBuilderFactory.get("moveTranslatedFile")
                .<LineContent, LineContent>chunk(10)
                .reader(getItemReaderFromFileName(fileName))
                .processor(processor)
                .writer(itemWriter)
                .build();
    }


}
