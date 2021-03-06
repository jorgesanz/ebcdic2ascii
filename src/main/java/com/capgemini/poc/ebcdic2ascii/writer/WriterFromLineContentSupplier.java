package com.capgemini.poc.ebcdic2ascii.writer;

import com.capgemini.poc.ebcdic2ascii.dto.LineContent;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

public class WriterFromLineContentSupplier{



    public static FlatFileItemWriter getFlatFileItemWriter(String fileLocation) {
        FlatFileItemWriter<LineContent> writer = new FlatFileItemWriter<>();

        //Set output file location
        writer.setResource(outputResource(fileLocation));

        //All job repetitions should "append" to same output file
        writer.setAppendAllowed(true);

        //Name field values sequence based on object properties
        writer.setLineAggregator(new DelimitedLineAggregator<LineContent>() {
            {
                setDelimiter(",");
                setFieldExtractor(new BeanWrapperFieldExtractor<LineContent>() {
                    {
                        setNames(new String[]{/* "id",*/ "content"});
                    }
                });
            }
        });
        return writer;
    }

    public static Resource outputResource(String fileLocation) {
        return new FileSystemResource(fileLocation);
    }
}
