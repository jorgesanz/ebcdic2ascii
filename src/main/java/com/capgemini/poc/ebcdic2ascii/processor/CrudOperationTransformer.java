package com.capgemini.poc.ebcdic2ascii.processor;

import com.capgemini.poc.ebcdic2ascii.dto.*;
import com.capgemini.poc.ebcdic2ascii.entity.Client;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import static com.capgemini.poc.ebcdic2ascii.processor.CrudOperationTransformerConstants.*;

@Component
public class CrudOperationTransformer implements ItemProcessor<LineContent, CrudOperation> {

    @Override
    public CrudOperation process(LineContent lineContent) throws Exception {
        return createCrudOperation(lineContent.getContent());
    }

    private CrudOperation createCrudOperation(String content) {
        CrudOperation crudOperation = new CrudOperation();
        crudOperation.setAction(createAction(content));
        crudOperation.setClient(createClient(content));
        crudOperation.setContract(createContract(content));
        return crudOperation;
    }

    private Contract createContract(String content) {
        Contract contract = new Contract();
        contract.setCodContrato(content.substring(ENT_COD_CONTRATO_FIRST_POS, ENT_COD_CONTRATO_FIRST_POS + ENT_COD_CONTRATO_LENGTH));
        contract.setCodCliente(content.substring(ENT_COD_CLIENTE_FIRST_POS, ENT_COD_CLIENTE_FIRST_POS + ENT_COD_CLIENTE_LENGTH));
        //TODO FILL ALL POSITIONS
        return contract;
    }

    private Client createClient(String content) {
        Client client = new Client();
        client.setCodCLiente(content.substring(COD_CLIENTE_FIRST_POS, COD_CLIENTE_FIRST_POS+COD_CLIENTE_LENGTH));
        client.setNumPresnnCob(content.substring(ENT_NUM_PRESNN_C_CLI_FIRST_POS, ENT_NUM_PRESNN_C_CLI_LENGTH + ENT_NUM_PRESNN_C_CLI_LENGTH));
        //TODO FILL ALL POSITIONS
        return client;
    }

    private Action createAction(String content) {
        switch (content.substring(COD_ACCION_FIRST_POS,COD_ACCION_FIRST_POS+COD_ACCION_LENGTH)){
            case "I": return Action.CREATE;
            case "M": return Action.UPDATE;
            case "B": return Action.DELETE;
            default: throw new IllegalArgumentException("No action found");
        }
    }
}
