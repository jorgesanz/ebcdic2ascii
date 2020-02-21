package com.capgemini.poc.ebcdic2ascii.tasklet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class CsvComparatorTasklet implements Tasklet {

    private String mysqlFile;
    private String db2File;
    private String reportFile;

    public CsvComparatorTasklet(String mysqlFile, String db2File, String reportFile) {
        this.mysqlFile = mysqlFile;
        this.db2File = db2File;
        this.reportFile = reportFile;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws IOException {
        log.info("comparation started");
        log.info(String.format("extracting db2 file %s", db2File));
        Set<String> myDb2Lines = extractLines(db2File);
        log.info(String.format("db2 file %s extracted", db2File));
        log.info(String.format("extracting mySql file %s", mysqlFile));
        Set<String> mySqlLines = extractLines(mysqlFile);
        log.info(String.format("mySql file %s extracted", mysqlFile));
        Set<String> reportLines = compareLines(mySqlLines, myDb2Lines);
        log.info("files compared");
        writeLines(reportLines);
        log.info("report generated in "+reportFile);
        return RepeatStatus.FINISHED;
    }

    private Set<String> compareLines(Set<String> mySqlLines, Set<String> db2Lines) {
        Set<String> report = new HashSet<>();
        long matchingLines = okLine(mySqlLines,db2Lines);
        report.add(String.format("%s matching lines",matchingLines));
        long linesInMysqlNotFound = mySqlLines.size() - matchingLines;
        if(linesInMysqlNotFound > 0){
            report.add(String.format("%s lines not found in mysql", linesInMysqlNotFound));
        }
        long linesIndb2NotFound = db2Lines.size() - matchingLines;
        if(linesIndb2NotFound > 0){
            report.add(String.format("%s lines not found in db2", linesIndb2NotFound));
        }
        return report;
    }

    private long okLine(Set<String> mySqlLines, Set<String> db2Lines) {
        return mySqlLines.stream().filter(db2Lines::contains).count();
    }


    private void writeLines(Set<String> myDb2Lines) throws IOException {

        BufferedWriter writer = new BufferedWriter(new FileWriter(reportFile));

        for(String line: myDb2Lines){
            writer.write(line+System.lineSeparator());
        }

        writer.close();
    }

    private Set<String> extractLines(String file) {
        Set<String> lines = new HashSet<>();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(file))) {
            lines = br.lines().collect(Collectors.toSet());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
}
