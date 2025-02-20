package com.Gabriel.Noel.tarea3AD2024base.modelo;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Clase que representa una dirección para el servicio de Envío a Casa.
 */
@Embeddable
public class Direccion implements Serializable {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String direccion;
    
    private String localidad;

	public Direccion(String direccion, String localidad) {
		this.direccion = direccion;
		this.localidad = localidad;
	}

	public Direccion()
	{
		
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	@Override
	public String toString() {
		return "Direccion [direccion=" + direccion + ", localidad=" + localidad + "]";
	}

	
	
	
	
    
}
