package com.Gabriel.Noel.tarea3AD2024base.modelo;

/**
 * Tabla Creada para manejar los datos mejor e inyectarlos
 * a la TableView de la EstanciasFiltradasController
 * solo el contructor y los getter y setters
 */
public class PeregrinoTabla {
    private Long id;
    private String nombre;
    private String fechaSellado; // Como String para mostrar directamente en la tabla
    private String seEstancio;  // "Sí" o "No"
    private String esVIP;       // "Sí" o "No"

    // Constructor
    public PeregrinoTabla(Long id, String nombre, String fechaSellado, String seEstancio, String esVIP) {
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

    public String getFechaSellado() {
        return fechaSellado;
    }

    public String getSeEstancio() {
        return seEstancio;
    }

    public String getEsVIP() {
        return esVIP;
    }
}
