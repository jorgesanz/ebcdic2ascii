package com.capgemini.poc.ebcdic2ascii.dto;

import lombok.Data;

@Data
public class JobFilePaths {

    private String mysqlClients;
    private String mysqlContracts;


    private String db2Clients;
    private String db2Contracts;


    private String clientsReport;
    private String contractsReport;

    private String report1;
    private String report2;
    private String reportComparationReport;

}
