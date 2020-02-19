package com.capgemini.poc.ebcdic2ascii.step;

import com.capgemini.poc.ebcdic2ascii.dto.LineContent;
import com.capgemini.poc.ebcdic2ascii.processor.GenericFormatTransformer;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

import static com.capgemini.poc.ebcdic2ascii.reader.ItemReaderFromFileName.getItemReaderFromFileName;
import static com.capgemini.poc.ebcdic2ascii.writer.WriterFromLineContentSupplier.getFlatFileItemWriter;

@Component
public class MoveTranslatedFileStep {

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private GenericFormatTransformer genericFormatTransformer;


    public Step get(String inputLocation, String outputLocation) {
        return stepBuilderFactory.get("moveTranslatedFile")
                .<LineContent, LineContent>chunk(10)
                .reader(getItemReaderFromFileName(inputLocation))
                .processor(genericFormatTransformer)
                .writer(getFlatFileItemWriter(outputLocation))
                .build();
    }

}
