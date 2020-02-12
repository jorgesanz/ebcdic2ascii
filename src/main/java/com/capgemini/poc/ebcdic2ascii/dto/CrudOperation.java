package com.capgemini.poc.ebcdic2ascii.dto;

import com.capgemini.poc.ebcdic2ascii.entity.Client;
import lombok.Data;

@Data
public class CrudOperation {
    private Action action;
    private Client client;
    private Contract contract;
}
