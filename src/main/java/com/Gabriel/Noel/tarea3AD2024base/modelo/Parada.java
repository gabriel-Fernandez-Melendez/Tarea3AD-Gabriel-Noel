package com.Gabriel.Noel.tarea3AD2024base.modelo;

import jakarta.persistence.*;
import java.util.Objects;
import java.util.Set;
import java.io.Serializable;
import java.util.HashSet;

@Entity
@Table(name = "paradas") // Nombre de la tabla en la base de datos
public class Parada implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 100) // Nombre de la parada
	private String nombre;

	@Column(nullable = false) // Región asociada
	private char region;

	@Column(nullable = false, length = 100) // Responsable de la parada
	private String responsable;

	// Relación con ParadaSellada
	@OneToMany(mappedBy = "parada", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<ParadaSellada> paradasSelladas = new HashSet<>();

	@OneToOne(cascade = CascadeType.MERGE)//cambiado de all a merge para poder persisitir en la base de datos
	@JoinColumn(name = "Credenciales_id", referencedColumnName = "id", nullable = false) // preguntar a luis por que de																						// forma bidirecional no
	private Credenciales credenciales;

	// Constructor por defecto
	public Parada() {
	}

	// Constructor con parámetros
	public Parada(Long id, String nombre, char region, String responsable, Set<ParadaSellada> paradasSelladas,
			Credenciales credenciales) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.region = region;
		this.responsable = responsable;
		this.paradasSelladas = paradasSelladas;
	}

	// Getters y Setters
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

	public char getRegion() {
		return region;
	}

	public void setRegion(char region) {
		this.region = region;
	}

	public String getResponsable() {
		return responsable;
	}

	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}

	public Set<ParadaSellada> getParadasSelladas() {
		return paradasSelladas;
	}

	public void setParadasSelladas(Set<ParadaSellada> paradasSelladas) {
		this.paradasSelladas = paradasSelladas;
	}

	// hashCode, equals y toString
	@Override
	public int hashCode() {
		return Objects.hash(id, nombre, region, responsable);
	}

	public Credenciales getCredenciales() {
		return credenciales;
	}

	public void setCredenciales(Credenciales credenciales) {
		this.credenciales = credenciales;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Parada other = (Parada) obj;
		return Objects.equals(id, other.id) && Objects.equals(nombre, other.nombre) && region == other.region
				&& Objects.equals(responsable, other.responsable);
	}

	@Override
	public String toString() {
		return nombre;
	}
}
