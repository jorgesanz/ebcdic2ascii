package com.capgemini.poc.ebcdic2ascii.processor;

import com.capgemini.poc.ebcdic2ascii.dto.CrudOperation;
import com.capgemini.poc.ebcdic2ascii.dto.LineContent;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class CrudOperationTransformer implements ItemProcessor<LineContent, CrudOperation> {



    @Override
    public CrudOperation process(LineContent lineContent) throws Exception {
        return new CrudOperation();
    }
}
