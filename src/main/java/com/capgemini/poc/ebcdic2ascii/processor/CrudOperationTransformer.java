package com.capgemini.poc.ebcdic2ascii.processor;

import com.capgemini.poc.ebcdic2ascii.dto.Action;
import com.capgemini.poc.ebcdic2ascii.dto.CrudOperation;
import com.capgemini.poc.ebcdic2ascii.dto.LineContent;
import com.capgemini.poc.ebcdic2ascii.entity.Client;
import com.capgemini.poc.ebcdic2ascii.entity.Contract;
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
        contract.setCodProvincia(content.substring(ENT_COD_PROVINCI_FIRST_POS,ENT_COD_PROVINCI_FIRST_POS+ENT_COD_PROVINCI_LENGTH));
        contract.setCodPoblacion(content.substring(ENT_COD_POBLACIO_FIRST_POS,ENT_COD_POBLACIO_FIRST_POS+ENT_COD_POBLACIO_LENGTH));
        contract.setCodCalle(content.substring(ENT_COD_CALLE_FIRST_POS,ENT_COD_CALLE_FIRST_POS+ENT_COD_CALLE_LENGTH));
        contract.setCodFinca(content.substring(ENT_COD_FINCA_FIRST_POS, ENT_COD_FINCA_FIRST_POS+ENT_COD_FINCA_LENGTH));
        contract.setCodPuntoSumin(content.substring(ENT_COD_PUNTO_SU_FIRST_POS, ENT_COD_PUNTO_SU_FIRST_POS+ENT_COD_PUNTO_SU_LENGTH));
        contract.setNumPresnn(content.substring(ENT_NUM_PRESNN_C_CON_FIRST_POS, ENT_NUM_PRESNN_C_CON_FIRST_POS+ENT_NUM_PRESNN_C_CON_LENGTH));
        contract.setCodMultis(content.substring(ENT_COD_MULTIS_FIRST_POS, ENT_COD_MULTIS_FIRST_POS+ENT_COD_MULTIS_LENGTH));
        contract.setCodEstruMserv(content.substring(ENT_COD_ESTRU_MS_FIRST_POS, ENT_COD_ESTRU_MS_FIRST_POS+ENT_COD_ESTRU_MS_LENGTH));
        contract.setIdeZonaFactn(content.substring(ENT_IDE_ZONA_FAC_FIRST_POS, ENT_IDE_ZONA_FAC_FIRST_POS+ENT_IDE_ZONA_FAC_LENGTH));
        contract.setCodSegmento(content.substring(ENT_COD_SEGMENTO_FIRST_POS,ENT_COD_SEGMENTO_FIRST_POS+ENT_COD_SEGMENTO_LENGTH));
        contract.setTipOrgInterna(content.substring(ENT_TIP_ORG_INTE_FIRST_POS, ENT_TIP_ORG_INTE_FIRST_POS+ENT_TIP_ORG_INTE_LENGTH));
//        contract.setCodOrganInter(content.substring(ENT_COD_ORGAN_IN_FIRST_POS, ENT_COD_ORGAN_IN_FIRST_POS+ENT_COD_ORGAN_IN_LENGTH));
//        contract.setCodCnae(content.substring(ENT_COD_CNAE_FIRST_POS, ENT_COD_CNAE_FIRST_POS+ENT_COD_CNAE_LENGTH));


//        contract.setTipContrato(content.substring(ENT_TIP_CONTRATO_FIRST_POS, ENT_TIP_CONTRATO_FIRST_POS+ENT_TIP_CONTRATO_LENGTH));
//        contract.setFecAltaContrato(content.substring(ENT_FEC_ALTA_CON_FIRST_POS, ENT_FEC_ALTA_CON_FIRST_POS+ENT_FEC_ALTA_CON_LENGTH));
//        contract.setFormaPago(content.substring(ENT_FORMA_PAGO_FIRST_POS, ENT_FORMA_PAGO_FIRST_POS+ENT_FORMA_PAGO_LENGTH));
//        contract.setCodPais(content.substring(ENT_COD_PAIS_FIRST_POS, ENT_COD_PAIS_FIRST_POS+ENT_COD_PAIS_LENGTH));
//        contract.setCodPostalCto(content.substring(ENT_COD_POSTAL_C_FIRST_POS, ENT_COD_POSTAL_C_FIRST_POS+ENT_COD_POSTAL_C_LENGTH));
//        contract.setTipModoPago(content.substring(ENT_TIP_MODO_PAG_FIRST_POS, ENT_TIP_MODO_PAG_FIRST_POS+ENT_TIP_MODO_PAG_LENGTH));
//        contract.setDesAdiContrato(content.substring(ENT_DES_ADI_CONTRATO_FIRST_POS, ENT_DES_ADI_CONTRATO_FIRST_POS+ENT_DES_ADI_CONTRATO_LENGTH));
        return contract;
    }

    private Client createClient(String content) {
        Client client = new Client();
        client.setCodCLiente(content.substring(COD_CLIENTE_FIRST_POS, COD_CLIENTE_FIRST_POS+COD_CLIENTE_LENGTH));
        client.setNumPresnnCob(content.substring(NUM_PRESNN_COB_FIRST_POS, NUM_PRESNN_COB_FIRST_POS + NUM_PRESNN_COB_LENGTH));
        client.setTipSrSraEmpresa(content.substring(ENT_TIP_SR_SRA_E_FIRST_POS, ENT_TIP_SR_SRA_E_FIRST_POS+ENT_TIP_SR_SRA_E_LENGTH));
        client.setNomTitularCuenta(content.substring(ENT_NOM_TITULAR__FIRST_POS, ENT_NOM_TITULAR__FIRST_POS+ENT_NOM_TITULAR__LENGTH));
        client.setNomAped1TitCta(content.substring(ENT_NOM_APED_1_T_FIRST_POS,ENT_NOM_APED_1_T_FIRST_POS+ENT_NOM_APED_1_T_LENGTH));
        client.setNomAped2TitCta(content.substring(ENT_NOM_APED_2_T_FIRST_POS, ENT_NOM_APED_2_T_FIRST_POS+ENT_NOM_APED_2_T_LENGTH));
        client.setNumDniNifCifCu(content.substring(ENT_NUM_DNI_NIF__FIRST_POS, ENT_NUM_DNI_NIF__FIRST_POS+ENT_NUM_DNI_NIF__LENGTH));
        client.setTipDcoIdeTitCu(content.substring(ENT_TIP_DCO_IDE__FIRST_POS, ENT_TIP_DCO_IDE__FIRST_POS+ENT_TIP_DCO_IDE__LENGTH));
        
        return client;
    }

    private Action createAction(String content) {
        switch (content.substring(COD_ACCION_FIRST_POS,COD_ACCION_FIRST_POS+COD_ACCION_LENGTH)){
            case "A": return Action.CREATE;
            case "M": return Action.UPDATE;
            case "B": return Action.DELETE;
            default: throw new IllegalArgumentException("No action found");
        }
    }
}
