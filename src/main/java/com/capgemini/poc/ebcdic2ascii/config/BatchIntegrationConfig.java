package com.capgemini.poc.ebcdic2ascii.config;

import com.capgemini.poc.ebcdic2ascii.entity.Client;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.integration.launch.JobLaunchingMessageHandler;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.file.FileReadingMessageSource;

import javax.persistence.EntityManagerFactory;
import java.io.File;

@EnableIntegration
@EnableBatchProcessing
@Configuration
public class BatchIntegrationConfig {

    @Autowired
    EntityManagerFactory emf;

    @Value("${source.location}")
    private String ftpUploadDir;

    @Bean
    @InboundChannelAdapter(value = "fileInputChannel", poller = @Poller(fixedDelay = "1000"))
    public MessageSource<File> fileReadingMessageSource() {
        FileReadingMessageSource source = new FileReadingMessageSource();
        source.setDirectory(new File(ftpUploadDir));
//        source.setFilter(new SimplePatternFileListFilter("*.csv"));
        source.setScanEachPoll(true);
        source.setUseWatchService(true);
        return source;
    }

    @Bean
    @ServiceActivator(inputChannel = "jobChannel", outputChannel = "nullChannel")
    protected JobLaunchingMessageHandler launcher(JobLauncher jobLauncher) {
        return new JobLaunchingMessageHandler(jobLauncher);
    }

    @Bean
    public JpaItemWriter<Client> writer() {
        JpaItemWriter<Client> writer = new JpaItemWriter();
        writer.setEntityManagerFactory(emf);
        return writer;
    }

}
