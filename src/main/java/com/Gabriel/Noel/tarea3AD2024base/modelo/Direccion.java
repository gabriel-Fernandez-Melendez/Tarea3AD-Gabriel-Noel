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

    private String direccion;
    
    private String localidad;

	public Direccion(Long id, String direccion, String localidad) {
		super();
		this.id = id;
		this.direccion = direccion;
		this.localidad = localidad;
	}

	public Direccion()
	{
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
		return "Direccion [id=" + id + ", direccion=" + direccion + ", localidad=" + localidad + "]";
	}

	
	
	
	
    
}
