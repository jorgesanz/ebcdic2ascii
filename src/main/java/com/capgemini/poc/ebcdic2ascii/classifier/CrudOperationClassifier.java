package com.capgemini.poc.ebcdic2ascii.classifier;

import com.capgemini.poc.ebcdic2ascii.dto.Action;
import com.capgemini.poc.ebcdic2ascii.dto.CrudOperation;
import com.capgemini.poc.ebcdic2ascii.writer.DeleteJdbcWriter;
import com.capgemini.poc.ebcdic2ascii.writer.UpsertJdbcWriter;
import org.springframework.batch.item.ItemWriter;
import org.springframework.classify.Classifier;
import org.springframework.stereotype.Component;

@Component
public class CrudOperationClassifier implements Classifier<CrudOperation,  ItemWriter<? super CrudOperation>> {

    private ItemWriter<CrudOperation> upsertJdbcWriter;
    private ItemWriter<CrudOperation> deleteJdbcWrite;

    public CrudOperationClassifier(DeleteJdbcWriter deleteJdbcWrite, UpsertJdbcWriter upsertJdbcWriter) {
        this.upsertJdbcWriter = upsertJdbcWriter;
        this.deleteJdbcWrite = deleteJdbcWrite;
    }

    @Override
    public ItemWriter<? super CrudOperation> classify(CrudOperation crudOperation) {

        if (crudOperation.getAction() == Action.CREATE || crudOperation.getAction() == Action.UPDATE){
            return upsertJdbcWriter;
        }else if (crudOperation.getAction() == Action.DELETE){
            return deleteJdbcWrite;
        }
        return null;

    }
}
