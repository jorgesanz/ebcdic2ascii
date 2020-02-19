package com.capgemini.poc.ebcdic2ascii.builder;

import com.capgemini.poc.ebcdic2ascii.dto.JobFilePaths;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class JobFilePathsBuilder {

    @Value("${csv.file.mysql.location}")
    private String csvExportLocation;

    @Value("${csv.file.db2.client.before}")
    private String db2CsvClientBefore;

    @Value("${csv.file.db2.contract.before}")
    private String db2CsvContractBefore;

    @Value("${csv.file.db2.client.after}")
    private String db2CsvClientAfter;

    @Value("${csv.file.db2.contract.after}")
    private String db2CsvContractAfter;

    @Value("${csv.file.db2.after}")
    private String db2CsvAfter;

    @Value("${csv.file.mysql.location}")
    private String mysqlCsv;

    @Value("${report.file.location}")
    private String reportLocation;

    @Value("${origin.file.location}")
    private String sourceLoadLocation;

    @Value("${transformed.file.location}")
    private String sourceTransformedLoadLocation;

    @Value("${comparation.reports.location}")
    private String comparationReportsLocation;

    public JobFilePaths get(String inputFileName){

        JobFilePaths jobFilePaths = new JobFilePaths();
        String fileNameWithoutExtension = FilenameUtils.removeExtension(inputFileName);
        jobFilePaths.setMysqlClientsBeforeLoad(csvExportLocation + File.separator + "clients-before-load.csv");
        jobFilePaths.setMysqlContractsBeforeLoad(csvExportLocation + File.separator + "contracts-before-load.csv");
        jobFilePaths.setMysqlClientsAfterLoad(csvExportLocation + File.separator + "clients-after-load.csv");
        jobFilePaths.setMysqlContractsAfterLoad(csvExportLocation + File.separator + "contracts-after-load.csv");

        jobFilePaths.setInputBinaryLocation(sourceLoadLocation + File.separator + inputFileName);
        jobFilePaths.setInputTransformedLocation(sourceTransformedLoadLocation + File.separator + fileNameWithoutExtension+".txt");

        jobFilePaths.setDb2ClientsBeforeLoad(sourceLoadLocation+File.separator+db2CsvClientBefore);
        jobFilePaths.setDb2ContractsBeforeLoad(sourceLoadLocation+File.separator+db2CsvContractBefore);
        jobFilePaths.setDb2ClientsAfterLoad(sourceLoadLocation+File.separator+db2CsvClientAfter);
        jobFilePaths.setDb2ContractsAfterLoad(sourceLoadLocation+File.separator+db2CsvContractAfter);

        jobFilePaths.setClientsReportBeforeLoad(sourceTransformedLoadLocation +File.separator + "clients-report-before-load.csv");
        jobFilePaths.setContractsReportBeforeLoad(sourceTransformedLoadLocation +File.separator + "contracts-report-before-load.csv");
        jobFilePaths.setClientsReportAfterLoad(sourceTransformedLoadLocation +File.separator + "clients-report-after-load.csv");
        jobFilePaths.setContractsReportAfterLoad(sourceTransformedLoadLocation +File.separator + "contracts-report-after-load.csv");

        return jobFilePaths;
    }
}
