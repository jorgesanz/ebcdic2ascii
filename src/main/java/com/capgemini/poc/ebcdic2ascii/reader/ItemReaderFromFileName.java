package com.capgemini.poc.ebcdic2ascii.reader;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.transform.FixedLengthTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.core.io.FileSystemResource;

public class ItemReaderFromFileName {

    public static  ItemReader getItemReaderFromFileName(String resource)  {
        FlatFileItemReader flatFileItemReader = new FlatFileItemReaderBuilder<String>()
                .name("ItemReaderFromFileName")
                .resource(new FileSystemResource(resource))
                .lineTokenizer(getLineTokenizer())
//                .fixedLength()
//                .fixedLength()
////                .columns()
////                .delimited()
////                .names(new String[]{"content"})
//                .fieldSetMapper(new BeanWrapperFieldSetMapper<String>() {{
//                    setTargetType(String.class);
//                }})
                .build();

        return flatFileItemReader;
    }

    public static LineTokenizer getLineTokenizer(){
        FixedLengthTokenizer tokenizer = new FixedLengthTokenizer();

        tokenizer.setNames("Content");
        tokenizer.setColumns(new Range(1-325));
        return tokenizer;
    }
}
