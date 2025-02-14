package com.Gabriel.Noel.tarea3AD2024base.modelo;

public class Servicio {
	private Long id;
	private String nombre;
	private Double Precio;

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

	@Override
	public String toString() {
		return "Servicio [id=" + id + ", nombre=" + nombre + ", Precio=" + Precio + "]";
	}
	
	
}
