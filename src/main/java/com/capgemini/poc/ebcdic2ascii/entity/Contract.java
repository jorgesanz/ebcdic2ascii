package com.capgemini.poc.ebcdic2ascii.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "CONTRATO")
@Data
public class Contract implements Serializable {


    //    15 ENT_COD_CONTRATO      S9(9).     10 posiciones
    @Id
    @Column(name = "COD_CONTRATO")
    private String codContrato;

    //           15 ENT_COD_CLIENTE       X(8).       8 posiciones
    @Column(name = "COD_CLIENTE")
    private String codCliente;

    //           15 ENT_COD_PROVINCIA     S9(4).      5 posiciones
    @Column(name = "COD_PROVINCIA")
    private String codProvincia;

    //           15 ENT_COD_POBLACION     S9(9).     10 posiciones
    @Column(name = "COD_POBLACION")
    private String codPoblacion;

    //           15 ENT_COD_CALLE         S9(9).     10 posiciones
    @Column(name = "COD_CALLE")
    private String codCalle;

    //           15 ENT_COD_FINCA         S9(4).      5 posiciones
    @Column(name = "COD_FINCA")
    private String codFinca;

    //           15 ENT_COD_PUNTO_SUMIN   S9(4).      5 posiciones
    @Column(name = "COD_PUNTO_SUMIN")
    private String codPuntoSumin;

    //           15 ENT_NUM_PRESNN_COB    S9(4).      5 posiciones
    @Column(name = "NUM_PRESNN_COB")
    private String numPresnn;

    //           15 ENT_COD_MULTIS        S9(9).     10 posiciones
    @Column(name = "COD_MULTIS")
    private String codMultis;

    //           15 ENT_COD_ESTRU_MSERV   S9(4).      5 posiciones
    @Column(name = "COD_ESTRU_MSERV")
    private String codEstruMserv;

    //           15 ENT_IDE_ZONA_FACTN    X(2).       2 posiciones
    @Column(name = "IDE_ZONA_FACTN")
    private String ideZonaFactn;

    //           15 ENT_COD_SEGMENTO      X(2).       2 posiciones
    @Column(name = "COD_SEGMENTO")
    private String codSegmento;

    //           15 ENT_TIP_ORG_INTERNA   X(2).       2 posiciones
    @Column(name = "TIP_ORG_INTERNA")
    private String tipOrgInterna;

    //           15 ENT_COD_ORGAN_INTER   S9(4).      5 posiciones
    @Column(name = "COD_ORGAN_INTER")
    private String codOrganInter;

    //           15 ENT_COD_CNAE          X(7).       7 posiciones
    @Column(name = "COD_CNAE")
    private String codCnae;
    
}
