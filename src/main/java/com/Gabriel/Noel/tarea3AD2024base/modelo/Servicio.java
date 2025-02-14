package com.Gabriel.Noel.tarea3AD2024base.modelo;

import java.util.ArrayList;

public class Servicio {
	private Long id;
	private String nombre;
	private Double Precio;

	//hay que crear la conexion con ambas, probablemente la coleccion puede y debe estar solo del lado de la relacion de conjuntos pero lo pongo aqui de forma provicional
	private ArrayList<ConjuntoContratado> conjuntos;
	public Servicio() {
		
	}

	public Servicio(Long id, String nombre, Double precio) {
		super();
		this.id = id;
		this.nombre = nombre;
		Precio = precio;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Double getPrecio() {
		return Precio;
	}

	public void setPrecio(Double precio) {
		Precio = precio;
	}
	

	public ArrayList<ConjuntoContratado> getConjuntos() {
		return conjuntos;
	}

	public void setConjuntos(ArrayList<ConjuntoContratado> conjuntos) {
		this.conjuntos = conjuntos;
	}

	@Override
	public String toString() {
		return "Servicio [id=" + id + ", nombre=" + nombre + ", Precio=" + Precio + "]";
	}
	
	
}
