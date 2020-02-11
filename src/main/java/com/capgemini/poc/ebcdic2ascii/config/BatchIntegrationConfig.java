package com.capgemini.poc.ebcdic2ascii.config;

import com.capgemini.poc.ebcdic2ascii.Ebcdic2AsciiProcessor;
import com.capgemini.poc.ebcdic2ascii.LineContent;
import com.capgemini.poc.ebcdic2ascii.TransformFileTasklet;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.MethodInvokingTaskletAdapter;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.integration.launch.JobLaunchingMessageHandler;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.filters.SimplePatternFileListFilter;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@EnableIntegration
@EnableBatchProcessing
public class BatchIntegrationConfig {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Value("${source.location}")
    private String ftpUploadDir;

    @Value("${target.location}")
    private String targetLocation;
    @Value("${target.format}")
    private String targetFormat;

    @Value("${source.format}")
    private String sourceFormat;

    @Bean
    public MessageChannel fileInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public DirectChannel pgpFileProcessor() {
        return new DirectChannel();
    }

    @Bean
    @InboundChannelAdapter(value = "fileInputChannel", poller = @Poller(fixedDelay = "1000"))
    public MessageSource<File> fileReadingMessageSource() {
        FileReadingMessageSource source = new FileReadingMessageSource();
        source.setDirectory(new File(ftpUploadDir));
        source.setFilter(new SimplePatternFileListFilter("*.csv"));
        source.setScanEachPoll(true);
        source.setUseWatchService(true);
        return source;
    }

    @Bean
    @ServiceActivator(inputChannel = "jobChannel", outputChannel = "nullChannel")
    protected JobLaunchingMessageHandler launcher(JobLauncher jobLauncher) {
        return new JobLaunchingMessageHandler(jobLauncher);
    }

//    @Bean
    public Step step1() {
        return  this.stepBuilderFactory.get("step1")
                .tasklet(myTasklet()).build();
    }

    private MethodInvokingTaskletAdapter  myTasklet() {
        MethodInvokingTaskletAdapter adapter = new MethodInvokingTaskletAdapter();

        adapter.setTargetObject(processor());
        adapter.setTargetMethod("one");

        return adapter;
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


//    @Bean
//    public Job transformationJob() {
//        return jobBuilderFactory.get("importUserJob")
//                .incrementer(new RunIdIncrementer())
////				.listener(listener)
//                .flow(step1())
//                .end()
//                .build();
//    }

    @Bean
    public Job taskletJob() {
        return this.jobBuilderFactory.get("taskletJob")
                .start(deleteFilesInDir())
                .build();
    }

    @Bean
    public Step deleteFilesInDir() {
        return this.stepBuilderFactory.get("deleteFilesInDir")
                .tasklet(fileDeletingTasklet())
                .build();
    }

    @Bean
    public TransformFileTasklet fileDeletingTasklet() {
        TransformFileTasklet tasklet = new TransformFileTasklet();

        tasklet.setDirectoryResource(new FileSystemResource("target/test-outputs/test-dir"));

        return tasklet;
    }


}
