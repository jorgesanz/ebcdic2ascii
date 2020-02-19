package com.capgemini.poc.ebcdic2ascii.dto;

import lombok.Data;

@Data
public class JobFilePaths {

    private String inputBinaryLocation;
    private String inputTransformedLocation;

    private String mysqlClientsBeforeLoad;
    private String mysqlContractsBeforeLoad;
    private String mysqlClientsAfterLoad;
    private String mysqlContractsAfterLoad;

    private String db2ClientsBeforeLoad;
    private String db2ContractsBeforeLoad;
    private String db2ClientsAfterLoad;
    private String db2ContractsAfterLoad;

    private String clientsReportBeforeLoad;
    private String contractsReportBeforeLoad;

    private String clientsReportAfterLoad;
    private String contractsReportAfterLoad;

}
