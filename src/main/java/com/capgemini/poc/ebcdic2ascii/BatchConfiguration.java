package com.capgemini.poc.ebcdic2ascii;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.integration.launch.JobLaunchingGateway;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.dsl.Files;
import org.springframework.integration.file.filters.SimplePatternFileListFilter;
import org.springframework.integration.handler.LoggingHandler;

import java.io.File;
import java.io.FileReader;

// tag::setup[]
//@Configuration
//@EnableBatchProcessing
public class BatchConfiguration {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Value("${source.location}")
	private String sourceLocation;
	@Value("${target.location}")
	private String targetLocation;
	@Value("${target.format}")
	private String targetFormat;

	@Value("${source.format}")
	private String sourceFormat;


	// end::setup[]

	//	// tag::readerwriterprocessor[]
//	@Bean
//	public FlatFileItemReader<LineContent> reader() {
//		return new FlatFileItemReaderBuilder<LineContent>()
//				.name("ebcdicReader")
//				.resource(new FileSystemResource(sourceLocation))
//				.delimited()
//				.names(new String[]{"content"})
//				.fieldSetMapper(new BeanWrapperFieldSetMapper<LineContent>() {{
//					setTargetType(LineContent.class);
//				}})
//				.build();
//	}

	@Bean
	public FlatFileItemReader<LineContent> reader() {
		return new FlatFileItemReaderBuilder<LineContent>()
				.name("ebcdicReader")
				.resource(new FileSystemResource(sourceLocation+"\\sample-data.ebcdi"))
				.delimited()
				.names(new String[]{"content"})
				.fieldSetMapper(new BeanWrapperFieldSetMapper<LineContent>() {{
					setTargetType(LineContent.class);
				}})
				.build();
	}
	@Bean
	@StepScope
	public ItemReader sampleReader(@Value("#{jobParameters[input.file.name]}") String resource)  {
		FlatFileItemReader flatFileItemReader = new FlatFileItemReader();
		flatFileItemReader.setResource(new FileSystemResource(resource));
		return flatFileItemReader;
	}

	@Bean
	public Ebcdic2AsciiProcessor processor() {
		return new Ebcdic2AsciiProcessor(sourceFormat, targetFormat);
	}

	@Bean
	public Resource outputResource() {
		return new FileSystemResource(targetLocation);
	}

	@Bean
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
	@Bean
	public Job transformationJob() {
		return jobBuilderFactory.get("importUserJob")
				.incrementer(new RunIdIncrementer())
//				.listener(listener)
				.flow(step1())
				.end()
				.build();
	}

	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1")
				.<LineContent, LineContent>chunk(10)
				.reader(reader())
				.processor(processor())
				.writer(writer())
				.build();
	}
	// end::jobstep[]


	@Bean
	public FileMessageToJobRequest fileMessageToJobRequest() {
		FileMessageToJobRequest fileMessageToJobRequest = new FileMessageToJobRequest();
		fileMessageToJobRequest.setFileParameterName("input.file.name");
		fileMessageToJobRequest.setJob(transformationJob());
		return fileMessageToJobRequest;
	}

	@Bean
	public JobLaunchingGateway jobLaunchingGateway(JobRepository jobRepository) {
		SimpleJobLauncher simpleJobLauncher = new SimpleJobLauncher();
		simpleJobLauncher.setJobRepository(jobRepository);
		simpleJobLauncher.setTaskExecutor(new SyncTaskExecutor());
		JobLaunchingGateway jobLaunchingGateway = new JobLaunchingGateway(simpleJobLauncher);

		return jobLaunchingGateway;
	}


	@Bean
	public IntegrationFlow integrationFlow(JobLaunchingGateway jobLaunchingGateway) {

		return IntegrationFlows.from(sourceDirectory(), configurer -> configurer.poller(Pollers.fixedDelay(1000)))
				.filter(new SimplePatternFileListFilter("*.ebcdi")).
						handle(fileMessageToJobRequest()).
//						handle(jobLaunchingGateway).
				get();
	}

	@Bean
	public MessageSource<File> sourceDirectory() {
		FileReadingMessageSource messageSource = new FileReadingMessageSource();
		messageSource.setDirectory(new File(sourceLocation));
		return messageSource;
	}
}