package com.Gabriel.Noel.tarea3AD2024base.modelo;

import jakarta.persistence.*; // Para las anotaciones JPA
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "estancia") // Nombre de la tabla en la base de datos
public class Estancia {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Generación automática del ID
	private Long id;

	@Column(nullable = false) // No permitir valores nulos
	private LocalDate fecha;

	@Column(nullable = false) // No permitir valores nulos
	private boolean vip = false;

	// Relación con Peregrino
	@ManyToOne
	@JoinColumn(name = "id_peregrino", nullable = false) // Clave foránea hacia Peregrino
	private Peregrino peregrino;

	// Relación con Parada
	@ManyToOne
	@JoinColumn(name = "id_parada", nullable = false) // Clave foránea hacia Parada
	private Parada parada;

	// Constructor por defecto
	public Estancia() {
	}

	// Constructor con parámetros
	public Estancia(Long id, LocalDate fecha, boolean vip, Peregrino peregrino, Parada parada) {
		this.id = id;
		this.fecha = fecha;
		this.vip = vip;
		this.peregrino = peregrino;
		this.parada = parada;
	}

	// Getters y Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public boolean isVip() {
		return vip;
	}

	public void setVip(boolean vip) {
		this.vip = vip;
	}

	public Peregrino getPeregrino() {
		return peregrino;
	}

	public void setPeregrino(Peregrino peregrino) {
		this.peregrino = peregrino;
	}

	public Parada getParada() {
		return parada;
	}

	public void setParada(Parada parada) {
		this.parada = parada;
	}

	// Métodos hashCode y equals
	@Override
	public int hashCode() {
		return Objects.hash(fecha, id, parada, peregrino, vip);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Estancia other = (Estancia) obj;
		return Objects.equals(fecha, other.fecha) && Objects.equals(id, other.id)
				&& Objects.equals(parada, other.parada) && Objects.equals(peregrino, other.peregrino)
				&& vip == other.vip;
	}

	// Método toString(modificar al formato correcto )
	@Override
	public String toString() {
		return "Estancia [id=" + id + ", fecha=" + fecha + ", vip=" + vip + ", peregrino="
				+ (peregrino != null ? peregrino.getNombre() : "N/A") + ", parada="
				+ (parada != null ? parada.getNombre() : "N/A") + "]";
	}
}
