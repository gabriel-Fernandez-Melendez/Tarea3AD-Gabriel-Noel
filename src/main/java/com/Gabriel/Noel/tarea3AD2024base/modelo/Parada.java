package com.Gabriel.Noel.tarea3AD2024base.modelo;

import jakarta.persistence.*;
import java.util.Objects;
import java.util.Set;
import java.util.HashSet;

@Entity
@Table(name = "paradas") // Nombre de la tabla en la base de datos
public class Parada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100) // Nombre de la parada
    private String nombre;

    @Column(nullable = false) // Región asociada
    private char region;

    @Column(nullable = false, length = 100) // Responsable de la parada
    private String responsable;

    @Column(name = "id_usuario", nullable = true) // ID de usuario (opcional, si se usa para algo adicional)
    private Long idUsuario;

    // Relación con ParadaSellada
    @OneToMany(mappedBy = "parada", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ParadaSellada> paradasSelladas = new HashSet<>();
    
    @OneToOne(cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn
    private Credenciales credenciales;
    

    // Constructor por defecto
    public Parada() {}

    // Constructor con parámetros
    public Parada(Long id, String nombre, char region, String responsable, Long idUsuario,
			Set<ParadaSellada> paradasSelladas, Credenciales credenciales) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.region = region;
		this.responsable = responsable;
		this.idUsuario = idUsuario;
		this.paradasSelladas = paradasSelladas;
		this.credenciales = credenciales;
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

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
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
        return Objects.hash(id, nombre, region, responsable, idUsuario);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Parada other = (Parada) obj;
        return Objects.equals(id, other.id) &&
               Objects.equals(nombre, other.nombre) &&
               region == other.region &&
               Objects.equals(responsable, other.responsable) &&
               Objects.equals(idUsuario, other.idUsuario);
    }

    @Override
    public String toString() {
        return "Parada [id=" + id +
               ", nombre=" + nombre +
               ", region=" + region +
               ", responsable=" + responsable + "]";
    }
}
