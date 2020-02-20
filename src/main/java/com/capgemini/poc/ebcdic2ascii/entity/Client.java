package com.capgemini.poc.ebcdic2ascii.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Data
@Table(name="CLIENTE")
public class Client implements Serializable {

    //    15 ENT_COD_CLIENTE        X(8).      8 posiciones
	@Id
	@Column(name ="COD_CLIENTE")
	private String codCLiente;

    //           15 ENT_NUM_PRESNN_COB     S9(4).     5 posiciones
	@Column(name ="NUM_PRESNN_COB")
    private String numPresnnCob;

    //           15 ENT_TIP_SR_SRA_EMPRESA X(2).      2 posiciones
	@Column(name ="TIP_SR_SRA_EMPRESA")
    private String tipSrSraEmpresa;

    //           15 ENT_NOM_TITULAR_CUENTA X(45).    45 posiciones
	@Column(name ="NOM_TITULAR_CUENTA")
    private String nomTitularCuenta;

    //           15 ENT_NOM_APED_1_TIT_CTA X(45).    45 posiciones
	@Column(name ="NOM_APED_1_TIT_CTA")
    private String nomAped1TitCta;

    //           15 ENT_NOM_APED_2_TIT_CTA X(45).    45 posiciones
	@Column(name ="NOM_APED_2_TIT_CTA")
    private String nomAped2TitCta;

    //           15 ENT_NUM_DNI_NIF_CIF_CU X(9).      9 posiciones
	@Column(name ="NUM_DNI_NIF_CIF_CU")
    private String numDniNifCifCu;

    //           15 ENT_TIP_DCO_IDE_TIT_CU X(2).      2 posiciones
	@Column(name ="TIP_DCO_IDE_TIT_CU")
    private String tipDcoIdeTitCu;

}
