package com.capgemini.poc.ebcdic2ascii.dto;

import lombok.Data;

@Data
public class CrudOperation {
    private Action action;
    private Client client;
    private Contract contract;
}
