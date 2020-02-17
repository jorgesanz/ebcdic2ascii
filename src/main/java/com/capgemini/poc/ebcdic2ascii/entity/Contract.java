package com.capgemini.poc.ebcdic2ascii.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Contract{

	@Id
	@GeneratedValue
	private int id;

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

//           15 ENT-TIP-CONTRATO      X(2).       2 posiciones
    private String tipContrato;

//           15 ENT-FEC-ALTA-CONTRATO X(10).     10 posiciones
    private String fecAltaContrato;

//           15 ENT-FORMA-PAGO        X(1).       1 posiciones
    private String formaPago;

//           15 ENT-COD-PAIS          X(2).       2 posiciones
    private String codPais;

//           15 ENT-COD-POSTAL-CTO    S9(9).     10 posiciones
    private String codPostalCto;

//           15 ENT-TIP-MODO-PAGO     X(3).       3 posiciones
    private String tipModoPago;

//           15 ENT-DES-ADI-CONTRATO  X(45).     45 posiciones
    private String desAdiContrato;

	public String getCodContrato() {
		return codContrato;
	}

	public void setCodContrato(String codContrato) {
		this.codContrato = codContrato;
	}

	public String getCodCliente() {
		return codCliente;
	}

	public void setCodCliente(String codCliente) {
		this.codCliente = codCliente;
	}

	public String getCodProvincia() {
		return codProvincia;
	}

	public void setCodProvincia(String codProvincia) {
		this.codProvincia = codProvincia;
	}

	public String getCodPoblacion() {
		return codPoblacion;
	}

	public void setCodPoblacion(String codPoblacion) {
		this.codPoblacion = codPoblacion;
	}

	public String getCodCalle() {
		return codCalle;
	}

	public void setCodCalle(String codCalle) {
		this.codCalle = codCalle;
	}

	public String getCodFinca() {
		return codFinca;
	}

	public void setCodFinca(String codFinca) {
		this.codFinca = codFinca;
	}

	public String getCodPuntoSumin() {
		return codPuntoSumin;
	}

	public void setCodPuntoSumin(String codPuntoSumin) {
		this.codPuntoSumin = codPuntoSumin;
	}

	public String getNumPresnn() {
		return numPresnn;
	}

	public void setNumPresnn(String numPresnn) {
		this.numPresnn = numPresnn;
	}

	public String getCodMultis() {
		return codMultis;
	}

	public void setCodMultis(String codMultis) {
		this.codMultis = codMultis;
	}

	public String getCodEstruMserv() {
		return codEstruMserv;
	}

	public void setCodEstruMserv(String codEstruMserv) {
		this.codEstruMserv = codEstruMserv;
	}

	public String getIdeZonaFactn() {
		return ideZonaFactn;
	}

	public void setIdeZonaFactn(String ideZonaFactn) {
		this.ideZonaFactn = ideZonaFactn;
	}

	public String getCodSegmento() {
		return codSegmento;
	}

	public void setCodSegmento(String codSegmento) {
		this.codSegmento = codSegmento;
	}

	public String getTipOrgInterna() {
		return tipOrgInterna;
	}

	public void setTipOrgInterna(String tipOrgInterna) {
		this.tipOrgInterna = tipOrgInterna;
	}

	public String getCodOrganInter() {
		return codOrganInter;
	}

	public void setCodOrganInter(String codOrganInter) {
		this.codOrganInter = codOrganInter;
	}

	public String getCodCnae() {
		return codCnae;
	}

	public void setCodCnae(String codCnae) {
		this.codCnae = codCnae;
	}

	public String getTipContrato() {
		return tipContrato;
	}

	public void setTipContrato(String tipContrato) {
		this.tipContrato = tipContrato;
	}

	public String getFecAltaContrato() {
		return fecAltaContrato;
	}

	public void setFecAltaContrato(String fecAltaContrato) {
		this.fecAltaContrato = fecAltaContrato;
	}

	public String getFormaPago() {
		return formaPago;
	}

	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}

	public String getCodPais() {
		return codPais;
	}

	public void setCodPais(String codPais) {
		this.codPais = codPais;
	}

	public String getCodPostalCto() {
		return codPostalCto;
	}

	public void setCodPostalCto(String codPostalCto) {
		this.codPostalCto = codPostalCto;
	}

	public String getTipModoPago() {
		return tipModoPago;
	}

	public void setTipModoPago(String tipModoPago) {
		this.tipModoPago = tipModoPago;
	}

	public String getDesAdiContrato() {
		return desAdiContrato;
	}

	public void setDesAdiContrato(String desAdiContrato) {
		this.desAdiContrato = desAdiContrato;
	}

}
