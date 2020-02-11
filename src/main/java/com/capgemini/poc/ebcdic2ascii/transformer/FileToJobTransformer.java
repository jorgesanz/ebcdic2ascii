package com.capgemini.poc.ebcdic2ascii.transformer;


import com.capgemini.poc.ebcdic2ascii.Ebcdic2AsciiProcessor;
import com.capgemini.poc.ebcdic2ascii.LineContent;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.integration.launch.JobLaunchRequest;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.integration.annotation.Transformer;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Date;

@Component
public class FileToJobTransformer implements ApplicationContextAware {

    @Autowired
    private Job job;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Value("${target.location}")
    private String targetLocation;

    @Value("${target.format}")
    private String targetFormat;

    @Value("${source.format}")
    private String sourceFormat;

    private ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    @Transformer(inputChannel = "fileToJobProcessor", outputChannel = "jobChannel")
    public JobLaunchRequest transform(File aFile) {

        String fileName = aFile.getAbsolutePath();

        JobParameters jobParameters = new JobParametersBuilder()
                .addString("fileName", fileName)
                .addDate("dateTime", new Date())
                .toJobParameters();

        JobLaunchRequest request = new JobLaunchRequest(transformationJob(fileName), jobParameters);

        return request;
    }

    public Job transformationJob(String fileName) {
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
//				.listener(listener)
                .flow(step1(fileName))
                .end()
                .build();
    }

    public Step step1(String fileName) {
        return stepBuilderFactory.get("step1")
                .<LineContent, LineContent>chunk(10)
                .reader(sampleReader(fileName))
                .processor(processor())
                .writer(writer())
                .build();
    }



//    public FlatFileItemReader<LineContent> reader() {
//        return new FlatFileItemReaderBuilder<LineContent>()
//                .name("ebcdicReader")
//                .resource(new FileSystemResource(sourceLocation+"\\sample-data.ebcdi"))
//                .delimited()
//                .names(new String[]{"content"})
//                .fieldSetMapper(new BeanWrapperFieldSetMapper<LineContent>() {{
//                    setTargetType(LineContent.class);
//                }})
//                .build();
//    }

//    @StepScope
    public ItemReader sampleReader( String resource)  {
        return new FlatFileItemReaderBuilder<LineContent>()
                .name("ebcdicReader")
                .resource(new FileSystemResource(resource))
                .delimited()
                .names(new String[]{"content"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<LineContent>() {{
                    setTargetType(LineContent.class);
                }})
                .build();


//        FlatFileItemReader flatFileItemReader = new FlatFileItemReader();
//        flatFileItemReader.setResource(new FileSystemResource(resource));
//        return flatFileItemReader;
    }

    public Ebcdic2AsciiProcessor processor() {
        return new Ebcdic2AsciiProcessor(sourceFormat, targetFormat);
    }

    public Resource outputResource() {
        return new FileSystemResource(targetLocation);
    }

    public FlatFileItemWriter<LineContent> writer() {
        //Create writer instance
        FlatFileItemWriter<LineContent> writer = new FlatFileItemWriter<>();

        //Set output file location
        writer.setResource(outputResource());

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
    // end::readerwriterprocessor[]

    // tag::jobstep[]




}
