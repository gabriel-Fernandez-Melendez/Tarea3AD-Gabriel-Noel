package com.luisdbb.tarea3AD2024base.modelo;

import jakarta.persistence.*; // Usa jakarta.persistence con Spring Boot 3.x
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "carnet") // Nombre de la tabla en la base de datos
public class Carnet {

    // Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Genera automáticamente el ID
    private Long id;

    @Column(name = "fecha_expedicion", nullable = false)
    private LocalDate fechaexp = LocalDate.now(); // Valor por defecto

    @Column(nullable = false)
    private double distancia = 0.0; // Valor por defecto

    @Column(nullable = false)
    private int nvips = 0; // Valor por defecto

    // Relación con Parada
    @ManyToOne // Muchas instancias de Carnet pueden tener la misma parada inicial
    @JoinColumn(name = "parada_inicial_id", nullable = false) // Clave foránea
    private Parada paradaInicial;

    // Constructor vacío
    public Carnet() {}

    // Constructor con parámetros
    public Carnet(Long id, LocalDate fechaexp, double distancia, int nvips, Parada paradaInicial) {
        this.id = id;
        this.fechaexp = fechaexp;
        this.distancia = distancia;
        this.nvips = nvips;
        this.paradaInicial = paradaInicial;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaexp() {
        return fechaexp;
    }

    public void setFechaexp(LocalDate fechaexp) {
        this.fechaexp = fechaexp;
    }

    public double getDistancia() {
        return distancia;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }

    public int getNvips() {
        return nvips;
    }

    public void setNvips(int nvips) {
        this.nvips = nvips;
    }

    public Parada getParadaInicial() {
        return paradaInicial;
    }

    public void setParadaInicial(Parada paradaInicial) {
        this.paradaInicial = paradaInicial;
    }

    // Métodos hashCode y equals
    @Override
    public int hashCode() {
        return Objects.hash(distancia, fechaexp, id, nvips, paradaInicial);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Carnet other = (Carnet) obj;
        return Double.compare(other.distancia, distancia) == 0 &&
               nvips == other.nvips &&
               Objects.equals(fechaexp, other.fechaexp) &&
               Objects.equals(id, other.id) &&
               Objects.equals(paradaInicial, other.paradaInicial);
    }

    // Método toString
    @Override
    public String toString() {
        return "\n ID: " + id +
               "\n Fecha de Expedición: " + fechaexp +
               "\n Distancia: " + distancia + "Km" +
               "\n Número de Estancias VIP: " + nvips +
               "\n Parada Inicial: " + (paradaInicial != null ? paradaInicial.getNombre() : "N/A");
    }
}
