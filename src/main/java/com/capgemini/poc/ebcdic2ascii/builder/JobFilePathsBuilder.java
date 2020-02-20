package com.capgemini.poc.ebcdic2ascii.builder;

import com.capgemini.poc.ebcdic2ascii.dto.JobFilePaths;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class JobFilePathsBuilder {

    @Value("${csv.file.mysql.location}")
    private String csvExportLocation;

    @Value("${csv.file.db2.client.after}")
    private String db2CsvClientAfter;

    @Value("${csv.file.db2.contract.after}")
    private String db2CsvContractAfter;

    @Value("${csv.file.mysql.location}")
    private String mysqlCsv;

    @Value("${origin.file.location}")
    private String sourceLoadLocation;


    @Value("${comparation.reports.location}")
    private String comparationReportsLocation;

    public JobFilePaths get(){

        JobFilePaths jobFilePaths = new JobFilePaths();

        jobFilePaths.setMysqlClients(csvExportLocation + File.separator + "clients.csv");
        jobFilePaths.setMysqlContracts(csvExportLocation + File.separator + "contracts.csv");

        jobFilePaths.setDb2Clients(sourceLoadLocation+File.separator+db2CsvClientAfter);
        jobFilePaths.setDb2Contracts(sourceLoadLocation+File.separator+db2CsvContractAfter);

        jobFilePaths.setClientsReport(comparationReportsLocation +File.separator + "clients-report.csv");
        jobFilePaths.setContractsReport(comparationReportsLocation +File.separator + "contracts-report.csv");

        return jobFilePaths;
    }
}
