package com.capgemini.poc.ebcdic2ascii.writer;


import com.capgemini.poc.ebcdic2ascii.dto.CrudOperation;
import com.capgemini.poc.ebcdic2ascii.entity.Client;
import com.capgemini.poc.ebcdic2ascii.entity.Contract;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

public class DeleteJdbcWriter implements ItemWriter<CrudOperation> {

    JpaItemWriter<Client> clientDeleter;

    JpaItemWriter<Contract> contractDeleter;

    public DeleteJdbcWriter(JpaItemWriter<Client> clientDeleter, JpaItemWriter<Contract> contractDeleter) {
        this.clientDeleter = clientDeleter;
        this.contractDeleter = contractDeleter;
    }

    @Override
    public void write(List<? extends CrudOperation> items) throws Exception {
        List<Client> clients = items.stream().map(CrudOperation::getClient).collect(Collectors.toList());
        List<Contract> contracts = items.stream().map(CrudOperation::getContract).collect(Collectors.toList());
        clientDeleter.write(clients);
        contractDeleter.write(contracts);
    }
}
