package com.capgemini.poc.ebcdic2ascii.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Data
public class Contract implements Serializable {

	@Id
//    15 ENT-COD-CONTRATO      S9(9).     10 posiciones
    private String codContrato;

//           15 ENT-COD-CLIENTE       X(8).       8 posiciones
    private String codCliente;

//           15 ENT-COD-PROVINCIA     S9(4).      5 posiciones
    private String codProvincia;

//           15 ENT-COD-POBLACION     S9(9).     10 posiciones
    private String codPoblacion;

//           15 ENT-COD-CALLE         S9(9).     10 posiciones
    private String codCalle;

//           15 ENT-COD-FINCA         S9(4).      5 posiciones
    private String codFinca;

//           15 ENT-COD-PUNTO-SUMIN   S9(4).      5 posiciones
    private String codPuntoSumin;

//           15 ENT-NUM-PRESNN-COB    S9(4).      5 posiciones
    private String numPresnn;

//           15 ENT-COD-MULTIS        S9(9).     10 posiciones
    private String codMultis;

//           15 ENT-COD-ESTRU-MSERV   S9(4).      5 posiciones
    private String codEstruMserv;

//           15 ENT-IDE-ZONA-FACTN    X(2).       2 posiciones
    private String ideZonaFactn;

//           15 ENT-COD-SEGMENTO      X(2).       2 posiciones
    private String codSegmento;

//           15 ENT-TIP-ORG-INTERNA   X(2).       2 posiciones
    private String tipOrgInterna;

//           15 ENT-COD-ORGAN-INTER   S9(4).      5 posiciones
    private String codOrganInter;

//           15 ENT-COD-CNAE          X(7).       7 posiciones
    private String codCnae;

////           15 ENT-TIP-CONTRATO      X(2).       2 posiciones
    private String tipContrato;
//
////           15 ENT-FEC-ALTA-CONTRATO X(10).     10 posiciones
    private String fecAltaContrato;
//
////           15 ENT-FORMA-PAGO        X(1).       1 posiciones
    private String formaPago;
//
////           15 ENT-COD-PAIS          X(2).       2 posiciones
    private String codPais;
//
////           15 ENT-COD-POSTAL-CTO    S9(9).     10 posiciones
    private String codPostalCto;
//
////           15 ENT-TIP-MODO-PAGO     X(3).       3 posiciones
    private String tipModoPago;
//
////           15 ENT-DES-ADI-CONTRATO  X(45).     45 posiciones
    private String desAdiContrato;




}
