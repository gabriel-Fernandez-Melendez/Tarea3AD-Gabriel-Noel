package com.Gabriel.Noel.tarea3AD2024base.modelo;

import java.util.ArrayList;
import java.util.List;

public class Servicio {
	private Long id;
	private String nombre;
	private Double precio;
	private List<String> nombreParadas;

	//hay que crear la conexion con ambas, probablemente la coleccion puede y debe estar solo del lado de la relacion de conjuntos pero lo pongo aqui de forma provicional
	private ArrayList<ConjuntoContratado> conjuntos;
	
	
	public Servicio() {
		this.nombreParadas = new ArrayList<>();
	}

	public Servicio(Long id, String nombre, Double precio) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.precio = precio;
		this.nombreParadas = new ArrayList<>();
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
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}
	

	public ArrayList<ConjuntoContratado> getConjuntos() {
		return conjuntos;
	}

	public void setConjuntos(ArrayList<ConjuntoContratado> conjuntos) {
		this.conjuntos = conjuntos;
	}

	public List<String> getNombreParadas() {
		return nombreParadas;
	}

	public void setNombreParadas(List<String> nombreParadas) {
		this.nombreParadas = nombreParadas;
	}

	@Override
	public String toString() {
		return "Servicio [id=" + id + ", nombre=" + nombre + ", precio=" + precio + ", nombreParadas=" + nombreParadas
				+ ", conjuntos=" + conjuntos + "]";
	}

	
	
	
	
}
