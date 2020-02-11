package com.capgemini.poc.ebcdic2ascii.tasklet;

import com.capgemini.poc.ebcdic2ascii.LineContent;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.UnexpectedJobExecutionException;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

public class TransformFileTasklet implements Tasklet, InitializingBean {

    public TransformFileTasklet( String sourceFormat, String targetFormat) {
        Charset charset_in = Charset.forName(targetFormat);
        Charset charset_out = Charset.forName(sourceFormat);
        this.decoder = charset_out.newDecoder();
        this.encoder = charset_in.newEncoder();
    }

    private Resource directory;

    private CharsetDecoder decoder;
    private CharsetEncoder encoder;

    public RepeatStatus execute(StepContribution contribution,
                                ChunkContext chunkContext) throws Exception {


        String fileLocation = (String) chunkContext.getStepContext().getJobParameters().get("fileName");

        return RepeatStatus.FINISHED;
    }

    public void setDirectoryResource(Resource directory) {
        this.directory = directory;
    }

    public void afterPropertiesSet() throws Exception {
        Assert.notNull(directory, "directory must be set");
    }


    public LineContent process(LineContent lineContent) throws Exception {

        CharBuffer uCharBuffer = CharBuffer.wrap(lineContent.getContent());
        ByteBuffer bbuf = encoder.encode(uCharBuffer);
        CharBuffer cbuf = decoder.decode(bbuf);
        lineContent.setContent(cbuf.toString());
        return lineContent;

    }

    public LineContent read(String location){

//        BeanWrapperFieldSetMapper<LineContent> setMapper = new BeanWrapperFieldSetMapper<>(){{
//            setTargetType(LineContent.class);
//        }};

        LineContent lineContent = new LineContent();

                return lineContent;
//        public FlatFileItemReader<LineContent> reader() {
//            return new FlatFileItemReaderBuilder<LineContent>()
//                    .name("ebcdicReader")
//                    .resource(new FileSystemResource(sourceLocation+"\\sample-data.ebcdi"))
//                    .delimited()
//                    .names(new String[]{"content"})
//                    .fieldSetMapper(new BeanWrapperFieldSetMapper<LineContent>() {{
//                        setTargetType(LineContent.class);
//                    }})
//                    .build();
//        }
    }

}