package com.luisdbb.tarea3AD2024base.modelo;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
//prueba git
@Entity
@Table(name = "peregrinos") // Nombre de la tabla en la base de datos
public class Peregrino {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100) // Nombre del peregrino
    private String nombre;

    @Column(nullable = false, length = 50) // Nacionalidad del peregrino
    private String nacionalidad;

    // Relación Uno a Uno con Carnet
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "carnet_id", referencedColumnName = "id", nullable = false) // Clave foránea hacia Carnet
    private Carnet carnet;

    // Relación Uno a Muchos con Estancia
    @OneToMany(mappedBy = "peregrino", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Estancia> estancias = new ArrayList<>();

    // Relación Uno a Muchos con ParadaSellada
    @OneToMany(mappedBy = "peregrino", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ParadaSellada> paradasSelladas = new ArrayList<>();

    // Constructor por defecto
    public Peregrino() {}

    // Constructor con parámetros
    public Peregrino(Long id, String nombre, String nacionalidad, Carnet carnet, List<Estancia> estancias, List<ParadaSellada> paradasSelladas) {
        this.id = id;
        this.nombre = nombre;
        this.nacionalidad = nacionalidad;
        this.carnet = carnet;
        this.estancias = estancias;
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

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public Carnet getCarnet() {
        return carnet;
    }

    public void setCarnet(Carnet carnet) {
        this.carnet = carnet;
    }

    public List<Estancia> getEstancias() {
        return estancias;
    }

    public void setEstancias(List<Estancia> estancias) {
        this.estancias = estancias;
    }

    public List<ParadaSellada> getParadasSelladas() {
        return paradasSelladas;
    }

    public void setParadasSelladas(List<ParadaSellada> paradasSelladas) {
        this.paradasSelladas = paradasSelladas;
    }

    // Métodos hashCode y equals
    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, nacionalidad, carnet, estancias, paradasSelladas);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Peregrino other = (Peregrino) obj;
        return Objects.equals(id, other.id) &&
               Objects.equals(nombre, other.nombre) &&
               Objects.equals(nacionalidad, other.nacionalidad) &&
               Objects.equals(carnet, other.carnet) &&
               Objects.equals(estancias, other.estancias) &&
               Objects.equals(paradasSelladas, other.paradasSelladas);
    }

    // Método toString
    @Override
    public String toString() {
        return "Peregrino [id=" + id +
               ", nombre=" + nombre +
               ", nacionalidad=" + nacionalidad +
               ", carnet=" + carnet +
               ", estancias=" + estancias +
               ", paradasSelladas=" + paradasSelladas +
               "]";
    }
}
