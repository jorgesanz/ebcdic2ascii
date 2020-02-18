package com.capgemini.poc.ebcdic2ascii.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Client implements Serializable {

    //    15 ENT-COD-CLIENTE        X(8).      8 posiciones
	@Id
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

	public String getCodCLiente() {
		return codCLiente;
	}

	public void setCodCLiente(String codCLiente) {
		this.codCLiente = codCLiente;
	}

	public String getNumPresnnCob() {
		return numPresnnCob;
	}

	public void setNumPresnnCob(String numPresnnCob) {
		this.numPresnnCob = numPresnnCob;
	}

	public String getTipSrSraEmpresa() {
		return tipSrSraEmpresa;
	}

	public void setTipSrSraEmpresa(String tipSrSraEmpresa) {
		this.tipSrSraEmpresa = tipSrSraEmpresa;
	}

	public String getNomTitularCuenta() {
		return nomTitularCuenta;
	}

	public void setNomTitularCuenta(String nomTitularCuenta) {
		this.nomTitularCuenta = nomTitularCuenta;
	}

	public String getNomAped1TitCta() {
		return nomAped1TitCta;
	}

	public void setNomAped1TitCta(String nomAped1TitCta) {
		this.nomAped1TitCta = nomAped1TitCta;
	}

	public String getNomAped2TitCta() {
		return nomAped2TitCta;
	}

	public void setNomAped2TitCta(String nomAped2TitCta) {
		this.nomAped2TitCta = nomAped2TitCta;
	}

	public String getNumDniNifCifCu() {
		return numDniNifCifCu;
	}

	public void setNumDniNifCifCu(String numDniNifCifCu) {
		this.numDniNifCifCu = numDniNifCifCu;
	}

	public String getTipDcoIdeTitCu() {
		return tipDcoIdeTitCu;
	}

	public void setTipDcoIdeTitCu(String tipDcoIdeTitCu) {
		this.tipDcoIdeTitCu = tipDcoIdeTitCu;
	}

}
