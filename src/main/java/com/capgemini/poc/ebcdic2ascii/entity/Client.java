package com.capgemini.poc.ebcdic2ascii.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Client {

    @Id
    @GeneratedValue
    private int id;

    //    15 ENT-COD-CLIENTE        X(8).      8 posiciones
    private String codCLiente;

    //           15 ENT-NUM-PRESNN-COB     S9(4).     5 posiciones
    private String numPresnnCob;

    //           15 ENT-TIP-SR-SRA-EMPRESA X(2).      2 posiciones
    private String tipSrSraEmpresa;

    //           15 ENT-NOM-TITULAR-CUENTA X(45).    45 posiciones
    private String nomTitularCuenta;

    //           15 ENT-NOM-APED-1-TIT-CTA X(45).    45 posiciones
    private String nomAped1TitCta;

    //           15 ENT-NOM-APED-2-TIT-CTA X(45).    45 posiciones
    private String nomAped2TitCta;

    //           15 ENT-NUM-DNI-NIF-CIF-CU X(9).      9 posiciones
    private String numDniNifCifCu;

    //           15 ENT-TIP-DCO-IDE-TIT-CU X(2).      2 posiciones
    private String tipDcoIdeTitCu;

}
