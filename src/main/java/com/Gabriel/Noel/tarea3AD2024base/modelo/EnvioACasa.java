package com.Gabriel.Noel.tarea3AD2024base.modelo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;

/**
 * Clase que representa un envío a casa dentro del sistema de peregrinos.
 */
@Entity
public class EnvioACasa implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue
    private Long id;

    @Embedded
    private Direccion direccion;

    private double peso;
    
    private int[] volumen = new int[3];
    
    private boolean esUrgente;

    private Long idParada;  // Se guarda solo el ID de la parada desde donde se envió

    public EnvioACasa() {
    }

    public EnvioACasa(Direccion direccion, double peso, int[] volumen, boolean esUrgente, Long idParada) {
        this.direccion = direccion;
        this.peso = peso;
        this.volumen = new int[]{0, 0, 0};
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

	
	public int[] getVolumen() {
		return volumen;
	}

	public void setVolumen(int[] volumen) {
		this.volumen = volumen;
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
		return "EnvioACasa [id=" + id + ", direccion=" + direccion + ", peso=" + peso + ", volumen="
				+ Arrays.toString(volumen) + ", esUrgente=" + esUrgente + ", idParada=" + idParada + "]";
	}

	
}
