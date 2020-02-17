package com.capgemini.poc.ebcdic2ascii.writer;


import com.capgemini.poc.ebcdic2ascii.dto.CrudOperation;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UpsertJdbcWriter implements ItemWriter<CrudOperation> {

    @Override
    public void write(List<? extends CrudOperation> items) throws Exception {
        System.out.println("update");
    }
}
