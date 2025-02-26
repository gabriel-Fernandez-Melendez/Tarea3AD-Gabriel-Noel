package com.Gabriel.Noel.tarea3AD2024base.controller;

import java.time.LocalDate;

/**
 * Tabla Creada para manejar los datos mejor e inyectarlos
 * a la TableView de la EstanciasFiltradasController
 * solo el contructor y los getter y setters
 */
public class PeregrinoTablaController {
    private Long id;
    private String nombre;
    private LocalDate fechaSellado; // Como String para mostrar directamente en la tabla
    private String seEstancio;  // "Sí" o "No"
    private String esVIP;       // "Sí" o "No"

    // Constructor
    public PeregrinoTablaController(Long id, String nombre, LocalDate fechaSellado, String seEstancio, String esVIP) {
        this.id = id;
        this.nombre = nombre;
        this.fechaSellado = fechaSellado;
        this.seEstancio = seEstancio;
        this.esVIP = esVIP;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public LocalDate getFechaSellado() {
        return fechaSellado;
    }

    public String getSeEstancio() {
        return seEstancio;
    }

    public String getEsVIP() {
        return esVIP;
    }
}
