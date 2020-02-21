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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
        List<String> mySqlLines = extractLines(mysqlFile);
        List<String> myDb2Lines = extractLines(db2File);
        List<String> reportLines = compareLines(mySqlLines, myDb2Lines);
        writeLines(reportLines);
        return RepeatStatus.FINISHED;
    }

    private List<String> compareLines(List<String> fileA, List<String> fileB) {
        List<String> report = new ArrayList<>();

        boolean sizeDifference = false;

        if (fileA.size() != fileB.size()) {
            sizeDifference = true;
            if (fileA.size() > fileB.size())
                report.add((fileA.size()-fileB.size())+" more lines in input file A than in input file B");
            else
                report.add((fileB.size()-fileA.size())+" more lines in input file A than in input file B");
        }

        if (!sizeDifference) {
            for (int i=0; i<fileA.size();i++) {

                if (!fileA.get(i).trim().equals(fileB.get(i).trim())) {
                    report.add("File A - "+fileA.get(i));
                    report.add("File B - "+fileB.get(i));
                    report.add(" ");
                }

            }

            if (report.size() == 0)
                report.add("File A = File B");


        }


        return report;
    }

    private long okLine(Set<String> mySqlLines, Set<String> db2Lines) {
        return mySqlLines.stream().filter(db2Lines::contains).count();
    }


    private void writeLines(List<String> myDb2Lines) throws IOException {

        BufferedWriter writer = new BufferedWriter(new FileWriter(reportFile));

        for(String line: myDb2Lines){
            writer.write(line+System.lineSeparator());
        }

        writer.close();
    }

    private List<String> extractLines(String file) {
        List<String> lines = new ArrayList<String>();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(file))) {
            lines = br.lines().collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
}
