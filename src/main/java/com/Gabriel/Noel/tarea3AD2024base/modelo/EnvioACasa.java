package com.Gabriel.Noel.tarea3AD2024base.modelo;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Clase que representa un envío a casa dentro del sistema de peregrinos.
 */
@Entity
public class EnvioACasa implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private Direccion direccion;

    private double peso;
    private double largo;
    private double ancho;
    private double alto;
    private boolean esUrgente;

    private Long idParada;  // Se guarda solo el ID de la parada desde donde se envió

    public EnvioACasa() {
    }

    public EnvioACasa(Direccion direccion, double peso, double largo, double ancho, double alto, boolean esUrgente, Long idParada) {
        this.direccion = direccion;
        this.peso = peso;
        this.largo = largo;
        this.ancho = ancho;
        this.alto = alto;
        this.esUrgente = esUrgente;
        this.idParada = idParada;
    }

    

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Direccion getDireccion() {
		return direccion;
	}

	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

	public double getLargo() {
		return largo;
	}

	public void setLargo(double largo) {
		this.largo = largo;
	}

	public double getAncho() {
		return ancho;
	}

	public void setAncho(double ancho) {
		this.ancho = ancho;
	}

	public double getAlto() {
		return alto;
	}

	public void setAlto(double alto) {
		this.alto = alto;
	}

	public boolean isEsUrgente() {
		return esUrgente;
	}

	public void setEsUrgente(boolean esUrgente) {
		this.esUrgente = esUrgente;
	}

	public Long getIdParada() {
		return idParada;
	}

	public void setIdParada(Long idParada) {
		this.idParada = idParada;
	}

	@Override
    public String toString() {
        return "EnvioACasa{" +
                "direccion=" + direccion +
                ", peso=" + peso +
                ", dimensiones=" + largo + "x" + ancho + "x" + alto +
                ", urgente=" + esUrgente +
                ", idParada=" + idParada +
                '}';
    }
}
