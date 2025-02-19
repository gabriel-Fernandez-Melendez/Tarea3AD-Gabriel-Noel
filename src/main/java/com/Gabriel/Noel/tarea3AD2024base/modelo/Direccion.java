package com.Gabriel.Noel.tarea3AD2024base.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Clase que representa una dirección para el servicio de Envío a Casa.
 */
@Entity
public class Direccion implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String calle;
    private String portal;
    private String piso;
    private String letra;
    private String localidad;

    public Direccion() {
    }

    public Direccion(String calle, String portal, String piso, String letra, String localidad) {
        this.calle = calle;
        this.portal = portal;
        this.piso = piso;
        this.letra = letra;
        this.localidad = localidad;
    }

   

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public String getPortal() {
		return portal;
	}

	public void setPortal(String portal) {
		this.portal = portal;
	}

	public String getPiso() {
		return piso;
	}

	public void setPiso(String piso) {
		this.piso = piso;
	}

	public String getLetra() {
		return letra;
	}

	public void setLetra(String letra) {
		this.letra = letra;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	@Override
    public String toString() {
        return calle + " " + portal + ", " + piso + letra + " - " + localidad;
    }
}
