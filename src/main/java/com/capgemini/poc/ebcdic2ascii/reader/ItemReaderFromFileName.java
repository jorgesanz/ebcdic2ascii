package com.capgemini.poc.ebcdic2ascii.reader;

import com.capgemini.poc.ebcdic2ascii.dto.LineContent;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.core.io.FileSystemResource;

public class ItemReaderFromFileName {

    public static  ItemReader getItemReaderFromFileName(String resource)  {
        return new FlatFileItemReaderBuilder<LineContent>()
                .name("ItemReaderFromFileName")
                .resource(new FileSystemResource(resource))
                .delimited()
                .names(new String[]{"content"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<LineContent>() {{
                    setTargetType(LineContent.class);
                }})
                .build();
    }
}
