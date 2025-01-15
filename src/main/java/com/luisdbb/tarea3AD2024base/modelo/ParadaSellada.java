package com.luisdbb.tarea3AD2024base.modelo;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "paradas_selladas") // Nombre de la tabla intermedia
public class ParadaSellada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_peregrino", nullable = false) // Clave foránea hacia Peregrino
    private Peregrino peregrino;

    @ManyToOne
    @JoinColumn(name = "id_parada", nullable = false) // Clave foránea hacia Parada
    private Parada parada;

    @Column(name = "fecha_parada", nullable = false) // Atributo adicional
    private LocalDate fechaParada;

    // Constructor por defecto
    public ParadaSellada() {}

    // Constructor con parámetros
    public ParadaSellada(Peregrino peregrino, Parada parada, LocalDate fechaParada) {
        this.peregrino = peregrino;
        this.parada = parada;
        this.fechaParada = fechaParada;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDate getFechaParada() {
        return fechaParada;
    }

    public void setFechaParada(LocalDate fechaParada) {
        this.fechaParada = fechaParada;
    }

    // hashCode y equals
    @Override
    public int hashCode() {
        return Objects.hash(id, peregrino, parada, fechaParada);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        ParadaSellada other = (ParadaSellada) obj;
        return Objects.equals(id, other.id) &&
               Objects.equals(peregrino, other.peregrino) &&
               Objects.equals(parada, other.parada) &&
               Objects.equals(fechaParada, other.fechaParada);
    }

    // toString
    @Override
    public String toString() {
        return "ParadaSellada [id=" + id +
               ", peregrino=" + (peregrino != null ? peregrino.getNombre() : "N/A") +
               ", parada=" + (parada != null ? parada.getNombre() : "N/A") +
               ", fechaParada=" + fechaParada +
               "]";
    }
}
